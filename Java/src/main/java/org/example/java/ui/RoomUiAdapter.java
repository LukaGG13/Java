package org.example.java.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.util.Pair;
import org.example.java.controllers.RoomViewController;
import org.example.java.entity.room.Room;
import org.example.java.ui.interfaces.Displayable;
import org.example.java.ui.interfaces.Searchable;
import org.example.java.ui.interfaces.UiComponent;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;

public class RoomUiAdapter implements UiComponent{

    private final Room room;
    private final SimpleStringProperty nameOfRoom;
    private final SimpleStringProperty sizeOfRoom;
    private final SimpleStringProperty numberOfBeds;
    private final SimpleStringProperty pricePerNight;
    private final SimpleStringProperty distanceFromBeach;
    private final SimpleStringProperty distanceFromCity;
    private final ObservableList<String> amenities = FXCollections.observableArrayList();

    public final Consumer<RoomUiAdapter> updateRoom;
    public final Consumer<RoomUiAdapter> deleteRoom;

    private static final Map<Room, RoomUiAdapter> roomAdapters = new HashMap<>();

    private RoomUiAdapter(Room room, Consumer<RoomUiAdapter> updateRoom, Consumer<RoomUiAdapter> deleteRoom) {
        this.room = room;
        this.updateRoom = updateRoom;
        this.deleteRoom = deleteRoom;

        this.nameOfRoom = new SimpleStringProperty(room.getRoomNumber().toString());
        this.sizeOfRoom = new SimpleStringProperty(room.getSizeInSqrM().toString());
        this.numberOfBeds = new SimpleStringProperty(room.getNumOfBeds().toString());
        this.pricePerNight = new SimpleStringProperty(room.getPricePerNight().toString());
        this.distanceFromBeach = new SimpleStringProperty(room.getDistanceFromBeach().toString());
        this.distanceFromCity = new SimpleStringProperty(room.getDistanceFromCityCenter().toString());
        //TODO: ne znam dali je dobro ?
        for(var amenity : room.getAmenities()) {
            amenities.add(amenity.toString());
        }
    }

    public static RoomUiAdapter of(Room room, Consumer<RoomUiAdapter> updateRoom, Consumer<RoomUiAdapter> deleteRoom) {
        return roomAdapters.computeIfAbsent(room, r -> new RoomUiAdapter(r, updateRoom, deleteRoom));
    }

    //public RoomUiAdapter of(Room room, )

    public static RoomUiAdapter of(Room room) {
        if (!roomAdapters.containsKey(room)) {
            throw new NoSuchElementException("No room adapter for room create one first");
        }
        return roomAdapters.get(room);
    }

    /*
    public static RoomUiAdapter of(UUID id) {
        if (!roomAdapters.containsKey(id)) throw new NoSuchElementException("No room adapter for room create one first");
        return roomAdapters.get(id);
    }
     */

    public Room toRoom() {
        var roomBuilder = new Room.RoomBuilder(this.room.getId(), Integer.parseInt(numberOfBeds.getValue()), new BigDecimal(pricePerNight.getValue()));
        roomBuilder.sizeInSqrM(Integer.parseInt(sizeOfRoom.getValue()));
        roomBuilder.distanceFromBeach(new BigDecimal(distanceFromBeach.getValue()));
        roomBuilder.distanceFromCityCenter(new BigDecimal(distanceFromCity.getValue()));
        roomBuilder.roomNumber(Integer.parseInt(nameOfRoom.getValue()));
        //TODO: amenties
        return roomBuilder.build();
    }

    public UUID getUUID() {
        return room.getId();
    }

    /*
    public void updateRoom(Room room) throws RuntimeException {
        //TODO: trabalo bi neki error
        updateRoom(room);
    }

    public void deleteRoom(Room room) throws RuntimeException {
        //TODO: trabalo bi neki error
        deleteRoom(room);
    }
     */
    public SimpleStringProperty getSizeOfRoom() {
        return sizeOfRoom;
    }

    public SimpleStringProperty getNumberOfBeds() {
        return numberOfBeds;
    }

    public SimpleStringProperty getPricePerNight() {
        return pricePerNight;
    }

    public SimpleStringProperty getDistanceFromBeach() {
        return distanceFromBeach;
    }

    public SimpleStringProperty getDistanceFromCity() {
        return distanceFromCity;
    }

    public ObservableList<String> getAmenities() {
        return amenities;
    }

    public Consumer<RoomUiAdapter> getUpdateRoom() {
        return updateRoom;
    }

    public Consumer<RoomUiAdapter> getDeleteRoom() {
        return deleteRoom;
    }

    public SimpleStringProperty getNameOfRoom() {
        return nameOfRoom;
    }

    @Override
    public Node display() {

        var fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/java/room-view.fxml"));
        var roomViewController = new RoomViewController(this);
        fxmlLoader.setController(roomViewController);
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e); //TODO: mrzim checked iznimke
        }
    }

    @Override
    public Set<Pair<String, String>> getKeyWord() {
        //TODO: implement the rest
        return Set.of(new Pair<>("Class","Room"));
    }
}
