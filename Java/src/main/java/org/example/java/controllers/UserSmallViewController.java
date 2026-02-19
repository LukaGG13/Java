package org.example.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.java.ui.UserUiAdapter;


public class UserSmallViewController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label ageLabel;

    private UserUiAdapter userUiAdapter;

    public UserSmallViewController(UserUiAdapter userUiAdapter) {
        this.userUiAdapter = userUiAdapter;
    }

    @FXML
    private void initialize() {
        nameLabel.textProperty()
                .bind(userUiAdapter.getNameBinding());

        ageLabel.textProperty()
                .bind(userUiAdapter.getAgeBinding());

        nameLabel.setStyle("-fx-text-fill: black;");
        ageLabel.setStyle("-fx-text-fill: black;");
    }

}
