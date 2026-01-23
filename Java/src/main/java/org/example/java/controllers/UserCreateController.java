package org.example.java.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.java.entity.admin.Admin;
import org.example.java.entity.guest.Guest;
import org.example.java.entity.user.User;
import org.example.java.ui.RepositoryUiAdapter;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class UserCreateController {

    @FXML
    private Spinner<Integer> ageSpinner;

    @FXML
    private Button enterUserButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private ComboBox<String> roleComboBox;

    private final RepositoryUiAdapter repository;
    private static final Logger log = LoggerFactory.getLogger(UserCreateController.class);

    public UserCreateController(RepositoryUiAdapter repository) {
       this.repository = repository;
    }

    private User constructUserFromUi() {

        var name = nameTextField.getText();
        var age = ageSpinner.getValue();
        if (name.isBlank()) throw new IllegalArgumentException("Name or age are invalid");

       if("Admin".equals(roleComboBox.getValue())) return new Admin(name, age);
       else return new Guest(name, age);
    }

    private void enterButtonPressed() {
        try {
            User user = constructUserFromUi();
            repository.addUser(user);
        } catch (IllegalArgumentException e) {
            log.error("Incorrect arguments in form", e);
            var alert = new Alert(Alert.AlertType.ERROR, "Incorrect arguments in form");
            alert.showAndWait();
        }
    }

    @FXML
    private void initialize() {
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100);
        spinnerValueFactory.setValue(20);
        ageSpinner.setValueFactory(spinnerValueFactory);

        roleComboBox.setItems(FXCollections.observableArrayList("Guest", "Admin"));
        enterUserButton.setOnAction(_ -> enterButtonPressed());
    }
}
