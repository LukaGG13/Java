package org.example.java.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.util.Pair;
import org.example.java.controllers.RoomViewController;
import org.example.java.entity.booking.Booking;
import org.example.java.entity.room.Room;
import org.example.java.entity.user.User;
import org.example.java.ui.interfaces.UiComponent;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

public class BookingUiAdapter implements UiComponent {

    private final Booking booking;
    private final SimpleStringProperty userProperty;
    private final SimpleStringProperty roomProperty;
    private final SimpleStringProperty checkInProperty;
    private final SimpleStringProperty checkOutProperty;

    public final Consumer<BookingUiAdapter> updateBooking;
    public final Consumer<BookingUiAdapter> deleteBooking;

    private static final Map<Booking, BookingUiAdapter> bookingAdapters = new HashMap<>();

    private BookingUiAdapter(
            Booking booking,
            Consumer<BookingUiAdapter> updateBooking,
            Consumer<BookingUiAdapter> deleteBooking
    ) {
        this.booking = booking;
        this.updateBooking = updateBooking;
        this.deleteBooking = deleteBooking;

        this.userProperty = new SimpleStringProperty(booking.user().getName());
        this.roomProperty = new SimpleStringProperty("Room " + booking.room().getRoomNumber());
        this.checkInProperty = new SimpleStringProperty(booking.checkIn().toString());
        this.checkOutProperty = new SimpleStringProperty(booking.checkOut().toString());
    }

    public static BookingUiAdapter of(
            Booking booking,
            Consumer<BookingUiAdapter> updateBooking,
            Consumer<BookingUiAdapter> deleteBooking
    ) {
        return bookingAdapters.computeIfAbsent(booking, b -> new BookingUiAdapter(b, updateBooking, deleteBooking));
    }

    public static BookingUiAdapter of(Booking booking) {
        if (!bookingAdapters.containsKey(booking)) {
            throw new NoSuchElementException("No booking adapter for booking. Create one first");
        }
        return bookingAdapters.get(booking);
    }

    public Booking toBooking() {
        return booking;
    }

    public SimpleStringProperty getUserProperty() {
        return userProperty;
    }

    public SimpleStringProperty getRoomProperty() {
        return roomProperty;
    }

    public SimpleStringProperty getCheckInProperty() {
        return checkInProperty;
    }

    public SimpleStringProperty getCheckOutProperty() {
        return checkOutProperty;
    }

    public Booking getBooking() {
        return booking;
    }

    public Consumer<BookingUiAdapter> getUpdateBooking() {
        return updateBooking;
    }

    public Consumer<BookingUiAdapter> getDeleteBooking() {
        return deleteBooking;
    }

    public User getUser() {
        return booking.user();
    }

    public Room getRoom() {
        return booking.room();
    }

    @Override
    public Node display() {
        try {
            var fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/java/booking-view.fxml"));
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Pair<String, String>> getKeyWord() {
        return Set.of(
                new Pair<>("Class", "Booking"),
                new Pair<>("User", booking.user().getName()),
                new Pair<>("Room", String.valueOf(booking.room().getRoomNumber())),
                new Pair<>("CheckIn", booking.checkIn().toString()),
                new Pair<>("CheckOut", booking.checkOut().toString())
        );
    }
}
