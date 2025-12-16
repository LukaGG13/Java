package org.example.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.java.entity.interfaces.Searchable;
import org.example.java.entity.repository.Repository;
import org.example.java.utils.ControllerUtils;

import java.util.HashSet;
import java.util.Set;


public class HelloController {

    @FXML
    private ComboBox<String> classComboBox;

    @FXML
    private Label errorTextField;

    @FXML
    private ComboBox<String> fieldComboBox;

    @FXML
    private TextField searchTextField;

    @FXML
    private VBox vBoxForReuslt;

    private final Repository repository;

    public HelloController(Repository repository) {
       this.repository = repository;
    }

    void displayToVBox(FilteredList<?> results) {
        vBoxForReuslt.getChildren().clear();
        for (var result : results) {
            vBoxForReuslt.getChildren().add(new Label(result.toString()));
        }
    }

    private void filter(FilteredList<Searchable> filteredResults, Set<String> searchQueries) {
        filteredResults.setPredicate(o -> {
            if (searchQueries.isEmpty()) {
                return true;
            }

            //todo lock in
            for (var query : searchQueries) {
                var match = false;
                for (var keyWord : o.getKeyWord()) {
                    if (query.toLowerCase().equals(keyWord.toLowerCase())) {
                        System.out.println(query + " matched " + keyWord);
                        match = true;
                        break;
                    }
                }
                if(!match)
                    return false;
            }
            return true;
        });

        errorTextField.setText(searchQueries.toString());
        //errorTextField.setText("");
        if(filteredResults.isEmpty()) {
            //errorTextField.setText("Error no result");
        }
        displayToVBox(filteredResults);
    }

    void updateSearchQuery(Set<String> query) {
        query.clear();

        String selectedClass = classComboBox.getValue();
        if (selectedClass != null && !"Class".equals(selectedClass)) {
            query.add(selectedClass);
        }

        String searchText = searchTextField.getText().trim();
        if (!searchText.isBlank()) {
            query.add(searchText);
        }

        String selectedField = fieldComboBox.getValue();
        if (selectedField != null && !"Field".equals(selectedField) && !searchText.isBlank() && classComboBox.getValue() != null && !"Class".equals(classComboBox.getValue())) {
            query.add(selectedField + "=" + searchText);  // Add the field and search text pair to the query
        }
    }

    @FXML
    void initialize() {
       errorTextField.setText("");
       classComboBox.setItems(FXCollections.observableArrayList("Class", "User", "Room","Bookings","Reviews"));
       fieldComboBox.setItems(FXCollections.observableArrayList(ControllerUtils.classToFields("")));
       ObservableList<Searchable> results = FXCollections.observableArrayList();
       FilteredList<Searchable> filteredResults = new FilteredList<>(results);
       final var setQueries = new HashSet<String>();
       results.setAll(repository.getUsers());
       //results.addAll(repository.getBookings());
       //results.addAll(repository.getReviews());
       //results.addAll(repository.getRooms());

       searchTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
           /*
           setQueries.add(newValue);
           setQueries.remove(oldValue);
           if(!"Field".equals(fieldComboBox.getValue()) && fieldComboBox.getValue() != null){
               setQueries.add(fieldComboBox.getValue() + "=" + newValue);
               setQueries.remove(fieldComboBox.getValue() + "=" + oldValue);
           }
            */
           updateSearchQuery(setQueries);
           filter(filteredResults, setQueries);

       }));

       classComboBox.getSelectionModel().selectedItemProperty().addListener(((options, oldValue, newValue) -> {
           /*
           if(!newValue.equals("Class")) {
               setQueries.add(newValue);
           }
           setQueries.remove(oldValue);
           setQueries.remove(fieldComboBox.getValue());
           fieldComboBox.setItems(FXCollections.observableArrayList(ContorllerUtils.classToFields(newValue)));
           fieldComboBox.setValue("Field");
           filter(filteredResults, setQueries);

            */
           fieldComboBox.setItems(FXCollections.observableArrayList(ControllerUtils.classToFields(newValue)));
           updateSearchQuery(setQueries);
           filter(filteredResults, setQueries);
       }));

        fieldComboBox.getSelectionModel().selectedItemProperty().addListener(((options, oldValue, newValue) -> {
            /*
            if(!newValue.equals("Field") && !"".equals(searchTextField.getText())) {
                setQueries.add(newValue + "=" + searchTextField.getText());
            }
            setQueries.remove(oldValue + "=" + searchTextField.getText());
            filter(filteredResults, setQueries);

             */
            updateSearchQuery(setQueries);
            filter(filteredResults, setQueries);
        }));

       displayToVBox(filteredResults);
    }
}