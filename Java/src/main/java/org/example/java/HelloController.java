package org.example.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.example.java.entity.interfaces.Searchable;
import org.example.java.ui.RepostiryUiAdapter;
import org.example.java.utils.ControllerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final RepostiryUiAdapter repository;
    private static final String CLASS_DEFAULT = "Class";
    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    public HelloController(RepostiryUiAdapter repository) {
       this.repository = repository;
    }

    void displayToVBox(FilteredList<?> results) {
        vBoxForReuslt.getChildren().clear();
        for (var result : results) {
            vBoxForReuslt.getChildren().add(new Label(result.toString()));
        }
    }

    boolean matches(Searchable o, Set<Pair<String, String>> searchQueries) {
        if (searchQueries.isEmpty()) {
            return true;
        }

        for (var query : searchQueries) {
            var match = false;
            for (var keyWord : o.getKeyWord()) {
                if ((keyWord.getKey().equals(query.getKey()) || "Any".equals(query.getKey())) && keyWord.getValue().contains(query.getValue())) {
                    match = true;
                    log.debug("Match for {} with {}", query, keyWord);
                    break;
                } else {
                    log.debug("No match for {} with {}", query, keyWord);
                }
            }
            if(!match) {
                return false;
            }
        }
        return true;
    }

    private void filter(FilteredList<Searchable> filteredResults, Set<Pair<String,String>> searchQueries) {
        filteredResults.setPredicate(o -> matches(o, searchQueries));

        errorTextField.setText(searchQueries.toString());
        if(filteredResults.isEmpty()) {
            errorTextField.setText("Error no result");
        }
        displayToVBox(filteredResults);
    }

    void updateSearchQuery(Set<Pair<String, String>> query) {
        query.clear();

        String searchText = searchTextField.getText().trim();
        String selectedClass = classComboBox.getValue();
        String selectedField = fieldComboBox.getValue();


        if (selectedClass != null && !CLASS_DEFAULT.equals(selectedClass)) {

            query.add(new Pair<>(CLASS_DEFAULT, selectedClass));
            if (!searchText.isBlank()) {
                if (selectedField != null && !"Field".equals(selectedField)) {
                    query.add(new Pair<>(selectedField, searchText));
                } else {
                    query.add(new Pair<>("Any", searchText));
                }
            }
        } else {
            query.add(new Pair<>("Any", searchText));
        }
    }

    @FXML
    void initialize() {
       errorTextField.setText("");
       classComboBox.setItems(FXCollections.observableArrayList(CLASS_DEFAULT, "User", "Room","Bookings","Reviews"));
       fieldComboBox.setItems(FXCollections.observableArrayList(ControllerUtils.classToFields("")));
        ObservableList<Searchable> results = repository.getSearchible();
        results.addAll(repository.getRooms());
        FilteredList<Searchable> filteredResults = new FilteredList<>(results);

        displayToVBox(filteredResults);

       final var setQueries = new HashSet<Pair<String, String>>();

       searchTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
           updateSearchQuery(setQueries);
           filter(filteredResults, setQueries);
       }));

       classComboBox.getSelectionModel().selectedItemProperty().addListener(((options, oldValue, newValue) -> {
           fieldComboBox.setItems(FXCollections.observableArrayList(ControllerUtils.classToFields(newValue)));
           updateSearchQuery(setQueries);
           filter(filteredResults, setQueries);
       }));

        fieldComboBox.getSelectionModel().selectedItemProperty().addListener(((options, oldValue, newValue) -> {
            updateSearchQuery(setQueries);
            filter(filteredResults, setQueries);
        }));

        results.addListener((javafx.collections.ListChangeListener<Searchable>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    filter(filteredResults, setQueries);
                }
            }
        });
    }
}