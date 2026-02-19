package org.example.java.controllers;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.IntegerStringConverter;
import org.example.java.components.ValidatingTextField;
import org.example.java.entity.room.Room;
import org.example.java.ui.RoomUiAdapter;

import java.math.BigDecimal;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class RoomCreateController {
    @FXML
    private CheckBox breakfastCheckBox;
    @FXML
    private Button createRoomButton;
    @FXML
    private ValidatingTextField distanceFromBeachValidatingTextField;
    @FXML
    private ValidatingTextField distanceFromCityValidatingTextField;
    @FXML
    private CheckBox gymCheckBox;
    @FXML
    private Spinner<Integer> numberOfBedsSpinner;
    @FXML
    private CheckBox parkingCheckBox;
    @FXML
    private CheckBox poolCheckBox;
    @FXML
    private ValidatingTextField pricePerNIghtValidatingTextField;
    @FXML
    private ValidatingTextField roomNumberValidatingTextField;
    @FXML
    private ValidatingTextField sizeOfRoomValidatingTextField;
    @FXML
    private CheckBox spaCheckBox;
    @FXML
    private CheckBox wifiCheckBox;


    private static final Pattern PRICE_PATTERN =
            Pattern.compile("^\\d+(\\.\\d{1,2})?$");


    private static final Pattern INTEGER_PATTERN =
            Pattern.compile("^\\d+$");

    private static final Pattern EMPTY_PATTERN =
            Pattern.compile("^$");

    public static final Predicate<String> isValidPrice =
            s -> s != null && PRICE_PATTERN.matcher(s).matches();

    Predicate<String> isInteger =
            s -> s != null && INTEGER_PATTERN.matcher(s).matches();

    Predicate<String> isEmpty =
            s -> s != null && EMPTY_PATTERN.matcher(s).matches();

    private final Consumer<RoomUiAdapter> buttonAction;
    private final Function<Room, RoomUiAdapter> roomUiAdapterSupplier;

    private void validateTextFields() {
        if (!pricePerNIghtValidatingTextField.isValid()) throw new IllegalStateException("Price is incorrect");
        if (!sizeOfRoomValidatingTextField.isValid()) throw new IllegalStateException("Size of room is incorrect");
        if (!distanceFromCityValidatingTextField.isValid()) throw new IllegalStateException("Distance from city is incorrect");
        if (!distanceFromBeachValidatingTextField.isValid()) throw new IllegalStateException("Distance from beach is incorrect");
        if (!roomNumberValidatingTextField.isValid()) throw new IllegalStateException("Room number is incorrect");
    }

    private Room createRoomFromUi() {
        validateTextFields();

        var numberOfBeds = numberOfBedsSpinner.getValue();
        var pricePerNight = new BigDecimal(pricePerNIghtValidatingTextField.getText());
        var roomBuilder = new Room.RoomBuilder(numberOfBeds, pricePerNight);

        var sizeOfRoom = sizeOfRoomValidatingTextField.getText();
        var distanceFromCity = distanceFromCityValidatingTextField.getText();
        var distanceFromBeach = distanceFromBeachValidatingTextField.getText();
        var roomNumber = roomNumberValidatingTextField.getText();

        if(!sizeOfRoom.isBlank())  roomBuilder.sizeInSqrM(Integer.parseInt(sizeOfRoom));
        if(!distanceFromCity.isBlank()) roomBuilder.distanceFromCityCenter(new BigDecimal(distanceFromCity));
        if(!distanceFromBeach.isBlank()) roomBuilder.distanceFromBeach(new BigDecimal(distanceFromBeach));
        if(!roomNumber.isBlank()) roomBuilder.roomNumber(Integer.parseInt(roomNumber));

        if(gymCheckBox.isSelected())    roomBuilder.addAmenity(Room.Amenity.GYM);
        if(wifiCheckBox.isSelected())   roomBuilder.addAmenity(Room.Amenity.WIFI);
        if(poolCheckBox.isSelected())   roomBuilder.addAmenity(Room.Amenity.POOL);
        if(parkingCheckBox.isSelected()) roomBuilder.addAmenity(Room.Amenity.PARKING);
        if(spaCheckBox.isSelected())    roomBuilder.addAmenity(Room.Amenity.SPA);
        if(breakfastCheckBox.isSelected()) roomBuilder.addAmenity(Room.Amenity.BREAKFAST);

       return roomBuilder.build();
    }

    private void resetUi() {
        numberOfBedsSpinner.getValueFactory().setValue(2);
        pricePerNIghtValidatingTextField.setText("");
        sizeOfRoomValidatingTextField.setText("");
        distanceFromCityValidatingTextField.setText("");
        distanceFromBeachValidatingTextField.setText("");
        roomNumberValidatingTextField.setText("");
        gymCheckBox.setSelected(false);
        wifiCheckBox.setSelected(false);
        poolCheckBox.setSelected(false);
        parkingCheckBox.setSelected(false);
        spaCheckBox.setSelected(false);
        breakfastCheckBox.setSelected(false);
    }

    private void buttonClicked() {
        try {
            buttonAction.accept(roomUiAdapterSupplier.apply(createRoomFromUi()));
            resetUi();
        } catch (IllegalArgumentException | IllegalStateException e) {
            (new Alert(Alert.AlertType.ERROR, e.getMessage())).showAndWait();
        }
    }

    public RoomCreateController(Function<Room, RoomUiAdapter> roomUiAdapterSupplier, Consumer<RoomUiAdapter> buttonAction) {

        this.roomUiAdapterSupplier = roomUiAdapterSupplier;

        this.buttonAction = buttonAction;
    }


    public void bindToRoomUiAdapter(RoomUiAdapter roomUiAdapter) {
        Bindings.bindBidirectional(roomUiAdapter.getNumberOfBeds(), getNumberOfBedsSpinner().getValueFactory().valueProperty(), new IntegerStringConverter());
        getPricePerNIghtValidatingTextField().textProperty().bindBidirectional(roomUiAdapter.getPricePerNight());
        getSizeOfRoomValidatingTextField().textProperty().bindBidirectional(roomUiAdapter.getSizeOfRoom());
        getDistanceFromBeachValidatingTextField().textProperty().bindBidirectional(roomUiAdapter.getDistanceFromBeach());
        getDistanceFromCityValidatingTextField().textProperty().bindBidirectional(roomUiAdapter.getDistanceFromCity());
        getRoomNumberValidatingTextField().textProperty().bindBidirectional(roomUiAdapter.getNameOfRoom());
    }

    @FXML
    private void initialize() {
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100);
        spinnerValueFactory.setValue(2);
        numberOfBedsSpinner.setValueFactory(spinnerValueFactory);
        createRoomButton.setOnAction(_ -> buttonClicked());
        pricePerNIghtValidatingTextField.setValidation(isValidPrice);
        sizeOfRoomValidatingTextField.setValidation(s -> isInteger.test(s) || isEmpty.test(s));
        roomNumberValidatingTextField.setValidation(s -> isInteger.test(s) || isEmpty.test(s));
        distanceFromBeachValidatingTextField.setValidation(s -> isValidPrice.test(s) || isEmpty.test(s));
        distanceFromCityValidatingTextField.setValidation(s -> isValidPrice.test(s) || isEmpty.test(s));
    }

    public CheckBox getBreakfastCheckBox() {
        return breakfastCheckBox;
    }

    public Button getCreateRoomButton() {
        return createRoomButton;
    }

    public ValidatingTextField getDistanceFromBeachValidatingTextField() {
        return distanceFromBeachValidatingTextField;
    }

    public ValidatingTextField getDistanceFromCityValidatingTextField() {
        return distanceFromCityValidatingTextField;
    }

    public CheckBox getGymCheckBox() {
        return gymCheckBox;
    }

    public Spinner<Integer> getNumberOfBedsSpinner() {
        return numberOfBedsSpinner;
    }

    public CheckBox getParkingCheckBox() {
        return parkingCheckBox;
    }

    public CheckBox getPoolCheckBox() {
        return poolCheckBox;
    }

    public ValidatingTextField getPricePerNIghtValidatingTextField() {
        return pricePerNIghtValidatingTextField;
    }

    public ValidatingTextField getRoomNumberValidatingTextField() {
        return roomNumberValidatingTextField;
    }

    public ValidatingTextField getSizeOfRoomValidatingTextField() {
        return sizeOfRoomValidatingTextField;
    }

    public CheckBox getSpaCheckBox() {
        return spaCheckBox;
    }

    public CheckBox getWifiCheckBox() {
        return wifiCheckBox;
    }
}