package org.example.java.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.java.entity.booking.Booking;
import org.example.java.entity.room.Room;
import org.example.java.entity.user.User;
import org.example.java.ui.BookingUiAdapter;
import org.example.java.ui.RepositoryUiAdapter;
import org.example.java.ui.interfaces.UiComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Consumer;
import java.util.function.Function;

public class BookingCreateController {

    @FXML
    private ComboBox<UiComponent> selectUserComboBox;

    @FXML
    private ComboBox<Room> selectRoomComboBox;

    @FXML
    private DatePicker checkInDatePicker;

    @FXML
    private DatePicker checkOutDatePicker;

    @FXML
    private Spinner<Integer> checkInHourSpinner;

    @FXML
    private Spinner<Integer> checkInMinuteSpinner;

    @FXML
    private Spinner<Integer> checkOutHourSpinner;

    @FXML
    private Spinner<Integer> checkOutMinuteSpinner;

    @FXML
    private Button createBookingButton;

    private final RepositoryUiAdapter repository;
    private final Consumer<BookingUiAdapter> buttonAction;
    private final Function<Booking, BookingUiAdapter> bookingUiAdapterSupplier;

    private static final Logger log = LoggerFactory.getLogger(BookingCreateController.class);

    public BookingCreateController(
            RepositoryUiAdapter repository,
            Function<Booking, BookingUiAdapter> bookingUiAdapterSupplier,
            Consumer<BookingUiAdapter> buttonAction
    ) {
        this.repository = repository;
        this.bookingUiAdapterSupplier = bookingUiAdapterSupplier;
        this.buttonAction = buttonAction;
    }

    private void validateInputs() {
        if (selectUserComboBox.getValue() == null) {
            throw new IllegalArgumentException("Please select a user");
        }
        if (selectRoomComboBox.getValue() == null) {
            throw new IllegalArgumentException("Please select a room");
        }
        if (checkInDatePicker.getValue() == null) {
            throw new IllegalArgumentException("Please select check-in date");
        }
        if (checkOutDatePicker.getValue() == null) {
            throw new IllegalArgumentException("Please select check-out date");
        }
    }

    private Booking createBookingFromUi() {
        validateInputs();

        User user = ((org.example.java.ui.UserUiAdapter) selectUserComboBox.getValue()).toUser();
        Room room = selectRoomComboBox.getValue();

        LocalDate checkInDate = checkInDatePicker.getValue();
        LocalDate checkOutDate = checkOutDatePicker.getValue();

        int checkInHour = checkInHourSpinner.getValue();
        int checkInMinute = checkInMinuteSpinner.getValue();
        int checkOutHour = checkOutHourSpinner.getValue();
        int checkOutMinute = checkOutMinuteSpinner.getValue();

        LocalDateTime checkIn = LocalDateTime.of(checkInDate, LocalTime.of(checkInHour, checkInMinute));
        LocalDateTime checkOut = LocalDateTime.of(checkOutDate, LocalTime.of(checkOutHour, checkOutMinute));

        return new Booking(room, user, checkIn, checkOut);
    }

    private void resetUi() {
        selectUserComboBox.setValue(null);
        selectRoomComboBox.setValue(null);
        checkInDatePicker.setValue(null);
        checkOutDatePicker.setValue(null);
        checkInHourSpinner.getValueFactory().setValue(14);
        checkInMinuteSpinner.getValueFactory().setValue(0);
        checkOutHourSpinner.getValueFactory().setValue(10);
        checkOutMinuteSpinner.getValueFactory().setValue(0);
    }

    private void buttonClicked() {
        try {
            Booking booking = createBookingFromUi();
            buttonAction.accept(bookingUiAdapterSupplier.apply(booking));
            resetUi();
        } catch (IllegalArgumentException | IllegalStateException e) {
            log.error("Error creating booking", e);
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void initialize() {
        // Set up user combo box
        selectUserComboBox.setItems(repository.getUserObservableArray());

        // Set up room combo box
        Thread.ofVirtual().name("load-rooms").start(() -> {
            try {
                var rooms = repository.toRepository().getRooms();
                Platform.runLater(() -> {
                    selectRoomComboBox.setItems(FXCollections.observableArrayList(rooms));
                    selectRoomComboBox.setCellFactory(cb -> new ListCell<>() {
                        @Override
                        protected void updateItem(Room room, boolean empty) {
                            super.updateItem(room, empty);
                            setText(empty || room == null ? "" : "Room " + room.getRoomNumber());
                        }
                    });
                    selectRoomComboBox.setButtonCell(new ListCell<>() {
                        @Override
                        protected void updateItem(Room room, boolean empty) {
                            super.updateItem(room, empty);
                            setText(empty || room == null ? "" : "Room " + room.getRoomNumber());
                        }
                    });
                });
            } catch (Exception e) {
                log.error("Error loading rooms", e);
            }
        });

        // Set up date pickers
        checkInDatePicker.setValue(LocalDate.now().plusDays(1));
        checkOutDatePicker.setValue(LocalDate.now().plusDays(2));

        // Set up spinners for time
        SpinnerValueFactory<Integer> checkInHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 14);
        SpinnerValueFactory<Integer> checkInMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        SpinnerValueFactory<Integer> checkOutHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 10);
        SpinnerValueFactory<Integer> checkOutMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);

        checkInHourSpinner.setValueFactory(checkInHourFactory);
        checkInMinuteSpinner.setValueFactory(checkInMinuteFactory);
        checkOutHourSpinner.setValueFactory(checkOutHourFactory);
        checkOutMinuteSpinner.setValueFactory(checkOutMinuteFactory);

        // Set up button
        createBookingButton.setOnAction(_ -> buttonClicked());
    }

    public ComboBox<UiComponent> getSelectUserComboBox() {
        return selectUserComboBox;
    }

    public ComboBox<Room> getSelectRoomComboBox() {
        return selectRoomComboBox;
    }

    public DatePicker getCheckInDatePicker() {
        return checkInDatePicker;
    }

    public DatePicker getCheckOutDatePicker() {
        return checkOutDatePicker;
    }

    public Spinner<Integer> getCheckInHourSpinner() {
        return checkInHourSpinner;
    }

    public Spinner<Integer> getCheckInMinuteSpinner() {
        return checkInMinuteSpinner;
    }

    public Spinner<Integer> getCheckOutHourSpinner() {
        return checkOutHourSpinner;
    }

    public Spinner<Integer> getCheckOutMinuteSpinner() {
        return checkOutMinuteSpinner;
    }

    public Button getCreateBookingButton() {
        return createBookingButton;
    }
}
