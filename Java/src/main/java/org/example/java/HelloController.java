package org.example.java;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.example.java.controllers.UserCreateController;
import org.example.java.database.DatabaseUtils;
import org.example.java.entity.interfaces.Searchable;
import org.example.java.ui.RepositoryUiAdapter;
import org.example.java.ui.UserUiAdapter;
import org.example.java.ui.interfaces.Displayable;
import org.example.java.ui.interfaces.UiComponent;
import org.example.java.utils.ControllerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


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

    @FXML
    private VBox lastCreatedUser;


    private final RepositoryUiAdapter repository;
    private static final String CLASS_DEFAULT = "Class";
    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    public HelloController(RepositoryUiAdapter repository) {
       this.repository = repository;
    }

    void displayToVBox(FilteredList<? extends Displayable> results) {
        vBoxForReuslt.getChildren().clear();
        for (var result : results) {
            vBoxForReuslt.getChildren().add(result.display());
            //vBoxForReuslt.getChildren().add((new UserUiAdapter((User)result)).display());
            //vBoxForReuslt.getChildren().add(new Label(result.toString()));
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

    private void filter(FilteredList<UiComponent> filteredResults, Set<Pair<String,String>> searchQueries) {
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
        Thread.ofVirtual().name("get last user").start(() -> {
            try {
                while (true) {
                    Thread.sleep(10_000);

                    var lastUser = DatabaseUtils.getLastCreateUser();

                    Platform.runLater(() -> {
                         lastCreatedUser.getChildren().clear();
                         VBox userBox = new UserUiAdapter(
                                lastUser,
                                repository::updateUser,
                                repository::deleteUser
                        ).display();

                        lastCreatedUser.getChildren().add(userBox);
                    });

                }
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        });

       errorTextField.setText("");
       classComboBox.setItems(FXCollections.observableArrayList(CLASS_DEFAULT, "User", "Room","Bookings","Reviews"));
       fieldComboBox.setItems(FXCollections.observableArrayList(ControllerUtils.classToFields("")));

        var filteredResults = repository.getUiFilterList();
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

        filteredResults.addListener((ListChangeListener<UiComponent>) _ -> displayToVBox(filteredResults));
    }
}