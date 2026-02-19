package org.example.java.controllers;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.util.converter.IntegerStringConverter;
import org.example.java.entity.repository.DataRepository;
import org.example.java.entity.repository.DatabaseRepository;
import org.example.java.ui.RepositoryUiAdapter;
import org.example.java.ui.RoomUiAdapter;

import java.io.IOException;

public class RoomViewController {

    @FXML
    private ListView<String> listView;

    @FXML
    private Label beachDistLabel;

    @FXML
    private Label bedsLabel;

    @FXML
    private Button bookButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label cityDistLabel;

    @FXML
    private Button editButton;

    @FXML
    private Label priceLabel;

    @FXML
    private Label roomNameLabel;

    @FXML
    private Label sizeLabel;

    private final RoomUiAdapter roomUiAdapter;
    public RoomViewController(RoomUiAdapter roomUiAdapter) {
        this.roomUiAdapter = roomUiAdapter;
    }

    @FXML
    void initialize() {
        roomNameLabel.textProperty().bindBidirectional(roomUiAdapter.getNameOfRoom());
        bedsLabel.textProperty().bindBidirectional(roomUiAdapter.getNumberOfBeds());
        priceLabel.textProperty().bindBidirectional(roomUiAdapter.getPricePerNight());
        sizeLabel.textProperty().bindBidirectional(roomUiAdapter.getSizeOfRoom());
        cityDistLabel.textProperty().bindBidirectional(roomUiAdapter.getDistanceFromCity());
        beachDistLabel.textProperty().bindBidirectional(roomUiAdapter.getDistanceFromBeach());
        listView.setItems(roomUiAdapter.getAmenities());
        //amenitiesPane.getChildren().set(roomUiAdapter.getAmenities());



        deleteButton.setOnAction(_ -> roomUiAdapter.deleteRoom.accept(roomUiAdapter));

        editButton.setOnAction(_ -> {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Edit room");

            var fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/java/room-create.fxml"));

            try {
                //var createRoomController = new RoomCreateController(() -> roomUiAdapter, roomUiAdapter.updateRoom);
                //TODO: fix
                var createRoomController = new RoomCreateController(room -> roomUiAdapter, roomUiAdapter.updateRoom);

                fxmlLoader.setController(createRoomController);
                var node = fxmlLoader.load();
                createRoomController.bindToRoomUiAdapter(roomUiAdapter);
                //TODO: bindaj u controller passaj Consumer a ne repository promjeni text gumba
                //TODO: i napravi da radi openito za editiranje

                dialog.getDialogPane().setContent((Node)node);
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

                dialog.showAndWait();
            } catch (IOException exception) {
                throw  new RuntimeException(exception.getMessage());
            }
        });
        //TODO: dialog
        //TODO: bookButton.setOnAction(_ -> roomUiAdapter.updateRoom(get ));

    }
}