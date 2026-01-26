package org.example.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.example.java.ui.RepositoryUiAdapter;
import org.example.java.ui.interfaces.UiComponent;

public class ReviewCreateController {

    //TODO: switch to user if dosnt work.
    @FXML
    private ComboBox<UiComponent> chooseUserComboBox;

    @FXML
    private ChoiceBox<UiComponent> choiceBox;

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
                    setGraphic(component.display()); // VBox is fine
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
                    setGraphic(component.display());
                }
            }
        });

        chooseUserComboBox.getItems().addAll(repository.getUserObservableArray());
    }
}
