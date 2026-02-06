package org.example.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.java.ui.RepositoryUiAdapter;

public class LoginController {

        @FXML
        private TextField passwordTextField;

        @FXML
        private TextField userNameTextField;

        private final RepositoryUiAdapter repository;

        public LoginController(RepositoryUiAdapter repository) {
            this.repository = repository;
        }

        @FXML
        private void initialize() {
            //TODO passwordTextField = validating text field
            //repository.login();
        }
}
