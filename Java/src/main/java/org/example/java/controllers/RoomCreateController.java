package org.example.java.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.java.entity.room.Room;
import org.example.java.ui.RepositoryUiAdapter;

import java.math.BigDecimal;


public class RoomCreateController {
    @FXML
    private Button createRoomButton;

    @FXML
    private Spinner<Integer> numberOfBedsSpinner;

    @FXML
    private TextField pricePerNightTextField;

    private final RepositoryUiAdapter repository;

    private Room createRoomFromUi() {
        Integer numberOfBeds = numberOfBedsSpinner.getValue();
        BigDecimal pricePerNight;
        try {
            pricePerNight = new BigDecimal(pricePerNightTextField.getText());
            if(BigDecimal.valueOf(0).compareTo(pricePerNight) >= 0) {
                throw new IllegalStateException("Cant be 0 or a negative number");
            }
        } catch (NumberFormatException _) {
            throw new IllegalArgumentException("Not a number");
        }
        return new Room.RoomBuilder(numberOfBeds, pricePerNight).build();
    }

    private void addRoom() {
        try {
            repository.addRoom(createRoomFromUi());
        } catch (IllegalArgumentException | IllegalStateException e) {
            (new Alert(Alert.AlertType.ERROR, e.getMessage())).showAndWait();
        }
    }
    public RoomCreateController(RepositoryUiAdapter repository) {
        this.repository = repository;
    }
    @FXML
    private void initialize() {
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100);
        spinnerValueFactory.setValue(2);
        numberOfBedsSpinner.setValueFactory(spinnerValueFactory);
        createRoomButton.setOnAction(_ -> addRoom());
    }
}
