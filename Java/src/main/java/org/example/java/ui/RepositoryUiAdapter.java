package org.example.java.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.example.java.entity.booking.Booking;
import org.example.java.entity.repository.Repository;
import org.example.java.entity.user.User;
import org.example.java.ui.interfaces.UiComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class RepositoryUiAdapter {
    //TODO: poparvi ovo smece
    final Repository repository;
    private final ObservableList<UiComponent> userObservableArray;
    private final ObservableList<BookingUiAdapter> bookingObservableArray;
    //final ObservableList<UiComponent> roomObservableArray;
    private final ObservableList<UiComponent> observableArray;
    private final FilteredList<UiComponent> filteredList;

    static final Logger log = LoggerFactory.getLogger(RepositoryUiAdapter.class);


    public RepositoryUiAdapter(Repository repository){
        this.repository = repository;

        userObservableArray = FXCollections.observableArrayList();
        bookingObservableArray = FXCollections.observableArrayList();
        //roomObservableArray = FXCollections.observableArrayList();
        observableArray = FXCollections.observableArrayList();

        for(var user : repository.getUsers()) {
            observableArray.add(new UserUiAdapter(user, this::updateUser, this::deleteUser));
            userObservableArray.add(new UserUiAdapter(user, this::updateUser, this::deleteUser));
        }

        for(var room : repository.getRooms()) {
            //observableArray.add(new RoomView(room));
            observableArray.add(RoomUiAdapter.of(room, this::updateRoom, this::deleteRoom));
        }

        for(var booking : repository.getBookings()) {
            var bookingAdapter = BookingUiAdapter.of(booking, this::updateBooking, this::deleteBooking);
            bookingObservableArray.add(bookingAdapter);
            observableArray.add(bookingAdapter);
        }

        //filteredList = new FilteredList<>(userObservableArray); //TODO ??
        filteredList = new FilteredList<>(observableArray); //TODO ??
        //filteredList.addAll(roomObservableArray);
    }

    public FilteredList<UiComponent> getUiFilterList() {
        return filteredList;
    }

    public void addUser(User user) { //TODO sinyc misliom da je ovaj prisut s view bolji //TODO2: what the helly to znaci luka
        log.debug("Adding user to ui adapter {}", user);
        repository.addUser(user);
        var userAdapter = new UserUiAdapter(user, this::updateUser, this::deleteUser);

        userObservableArray.add(userAdapter);
        observableArray.add(userAdapter);
        filteredList.setPredicate(_ -> true);
    }

    public void deleteUser(UserUiAdapter userUiAdapter) {
        repository.deleteUser(userUiAdapter.toUser());
        observableArray.remove(userUiAdapter);
        userObservableArray.remove(userUiAdapter);
        //TODO treba pobirsat password login file bolje u ppravom reposoitroiju nego tu
    }

    public void updateUser(UserUiAdapter userUiAdapter) {
        //TODO: trebat ce na vise mjest promjenit userUiAdapter tocnije bit ce ok ako bi si dijelili isti binding aa my chud life
        userObservableArray.remove(userUiAdapter); //TODO; why no work
        userObservableArray.add(userUiAdapter); //TODO temp and hacky fix ovo se treba fixat da je bolje
        repository.updateUser(userUiAdapter.toUser());
    }

    public void addRoom(RoomUiAdapter room) { //TODO: sincy
        log.debug("Adding room to ui adapter {}", room);
        repository.addRoom(room.toRoom());
        observableArray.add(room);
        //roomObservableArray.add(room);
        filteredList.setPredicate(_ -> true);
        //TODO: change to room adapter kada napravis
        //searchableObservableList.add(room);
    }

    public void deleteRoom(RoomUiAdapter roomUiAdapter) {
        throw new UnsupportedOperationException("naprvi ovo what the helly");
        //TODO: ne znam dali je kod dolje dobar uzet je iz usera treba rewritaat sve mozda sa generics
        /*
        repository.deleteRoom(roomUiAdapter.toRoom());
        observableArray.remove(roomUiAdapter);
        roomObservableArray.remove(roomUiAdapter);
         */
    }

    public void updateRoom(RoomUiAdapter roomUiAdapter) {
        throw new UnsupportedOperationException("naprvi ovo what the helly");
        //TODO: ne znam dali je kod dolje dobar uzet je iz usera treba rewritaat sve mozda sa generics
        /*
        //TODO: trebat ce na vise mjest promjenit roomUiAdapter tocnije bit ce ok ako bi si dijelili isti binding aa my chud life
        roomObservableArray.remove(roomUiAdapter); //TODO; why no work
        roomObservableArray.add(roomUiAdapter); //TODO temp and hacky fix ovo se treba fixat da je bolje
        repository.updateRoom(roomUiAdapter.toRoom());
         */
    }

    public void addBooking(BookingUiAdapter booking) {
        log.debug("Adding booking to ui adapter {}", booking);
        repository.addBooking(booking.getBooking());
        bookingObservableArray.add(booking);
        observableArray.add(booking);
        filteredList.setPredicate(_ -> true);
    }

    public void deleteBooking(BookingUiAdapter bookingUiAdapter) {
        log.debug("Deleting booking from ui adapter {}", bookingUiAdapter);
        repository.deleteBooking(bookingUiAdapter.getBooking());
        observableArray.remove(bookingUiAdapter);
        bookingObservableArray.remove(bookingUiAdapter);
    }

    public void updateBooking(BookingUiAdapter bookingUiAdapter) {
        log.debug("Updating booking in ui adapter {}", bookingUiAdapter);
        bookingObservableArray.remove(bookingUiAdapter);
        bookingObservableArray.add(bookingUiAdapter);
        repository.updateBooking(bookingUiAdapter.getBooking());
    }

    public ObservableList<UiComponent> getUserObservableArray() {
        return userObservableArray;
    }

    public ObservableList<BookingUiAdapter> getBookingObservableArray() {
        return bookingObservableArray;
    }

    /*
    public ObservableList<UiComponent> getRooms() {
        return observableArray;
        //return roomObservableArray;
    }

     */

    //TODO switch to userUiAdapter
    public Optional<User> getActiveUser() {
        return repository.getActiveUser();
    }


    public Repository toRepository() {
        return repository;
    }
}
