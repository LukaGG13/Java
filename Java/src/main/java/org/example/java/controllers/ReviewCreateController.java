package org.example.java.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.example.java.ui.RepositoryUiAdapter;
import org.example.java.ui.UserUiAdapter;
import org.example.java.ui.interfaces.UiComponent;

import java.io.IOException;

public class ReviewCreateController {

    //TODO: switch to user if dosnt work.
    @FXML
    private ComboBox<UiComponent> chooseUserComboBox;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextArea reviewTextArea;

    @FXML
    private Button saveReviewButton;

    private final RepositoryUiAdapter repository;

    public ReviewCreateController(RepositoryUiAdapter repository) {
        this.repository = repository;
    }

    @FXML
    void initialize() {
        //chooseUserComboBox.getItems().addAll(repository.getUserObservableArray().getFirst());
        chooseUserComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(UiComponent component, boolean empty) {
                super.updateItem(component, empty);

                if (empty || component == null) {
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/org/example/java/user-small-view.fxml")
                        );

                        var userSmallViewController = new UserSmallViewController((UserUiAdapter) component);
                        loader.setController(userSmallViewController);

                        Node view = loader.load();
                        setGraphic(view);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        chooseUserComboBox.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(UiComponent component, boolean empty) {
                super.updateItem(component, empty);

                if (empty || component == null) {
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/org/example/java/user-small-view.fxml")
                        );

                        var userSmallViewController = new UserSmallViewController((UserUiAdapter) component);
                        loader.setController(userSmallViewController);

                        Node view = loader.load();
                        setGraphic(view);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


        ObservableList<UiComponent> users = repository.getUserObservableArray();

        chooseUserComboBox.setItems(users);
    }
}
