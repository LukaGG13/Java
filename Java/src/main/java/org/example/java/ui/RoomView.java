package org.example.java.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.example.java.entity.room.Room;
import org.example.java.ui.interfaces.Displayable;
import org.example.java.ui.interfaces.UiComponent;

import java.util.HashSet;
import java.util.Set;

public class RoomView implements UiComponent {
    private final Room room;

    public RoomView(Room room) {
        this.room = room;
    }

    public Room toRoom() {
        return room;
    }

    @Override
    public VBox display() {
        var label = new Label(room.toString());
        return new VBox(label);
    }

    @Override
    public Set<Pair<String, String>> getKeyWord() {
            Set<Pair<String, String>> keywords = new HashSet<>();

            // Class identifier
            keywords.add(new Pair<>("Class", "Room"));

            // Exact field names (matching the class)
            keywords.add(new Pair<>("numOfBeds", room.getNumOfBeds().toString()));
            keywords.add(new Pair<>("sizeInSqrM", room.getSizeInSqrM().toString()));
            keywords.add(new Pair<>("pricePerNight", room.getPricePerNight().toString()));
            keywords.add(new Pair<>("distanceFromCityCenter", room.getDistanceFromCityCenter().toString()));
            keywords.add(new Pair<>("distanceFromBeach", room.getDistanceFromBeach().toString()));
            keywords.add(new Pair<>("amenities", room.getAmenities().toString()));

            return keywords;
        }
}
