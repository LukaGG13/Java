package org.example.java.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.example.java.entity.interfaces.Searchable;
import org.example.java.entity.repository.Repository;
import org.example.java.entity.room.Room;
import org.example.java.entity.user.User;
import org.example.java.ui.interfaces.Displayable;
import org.example.java.ui.interfaces.UiComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepositoryUiAdapter {
    //TODO: poparvio ovo smece
    final Repository repository;
    final ObservableList<UiComponent> userObservableArray;
    //final ObservableList<UiComponent> roomObservableArray;
    final ObservableList<UiComponent> observableArray;
    final FilteredList<UiComponent> filteredList;

    static final Logger log = LoggerFactory.getLogger(RepositoryUiAdapter.class);



    public RepositoryUiAdapter(Repository repository){
        this.repository = repository;

        userObservableArray = FXCollections.observableArrayList();
        //roomObservableArray = FXCollections.observableArrayList();
        observableArray = FXCollections.observableArrayList();

        for(var user : repository.getUsers()) {
            observableArray.add(new UserUiAdapter(user, this::updateUser, this::deleteUser));
            userObservableArray.add(new UserUiAdapter(user, this::updateUser, this::deleteUser));
        }

        for(var room : repository.getRooms()) {
            //roomObservableArray.add(new RoomView(room));
            observableArray.add(new RoomView(room));
        }

        //filteredList = new FilteredList<>(userObservableArray); //TODO ??
        filteredList = new FilteredList<>(observableArray); //TODO ??
        //filteredList.addAll(roomObservableArray);
    }

    public FilteredList<UiComponent> getUiFilterList() {
        return filteredList;
    }

    public void addUser(User user) { //TODO sinyc misliom da je ovaj prisut s view bolji
        log.debug("Adding user to ui adapter {}", user);
        repository.addUser(user);
        //userObservableArray.add(new UserUiAdapter(user, this::updateUser, this::deleteUser));
        observableArray.add(new UserUiAdapter(user, this::updateUser, this::deleteUser));
        filteredList.setPredicate(_ -> true);
    }

    public void deleteUser(UserUiAdapter userUiAdapter) {
        repository.deleteUser(userUiAdapter.toUser());
        observableArray.remove(userUiAdapter);
        //userObservableArray.remove(userUiAdapter);
    }

    public void updateUser(UserUiAdapter userUiAdapter) {
       repository.updateUser(userUiAdapter.toUser());
    }

    public void addRoom(RoomView room) { //TODO: sincy
        log.debug("Adding room to ui adapter {}", room);
        repository.addRoom(room.toRoom());
        observableArray.add(room);
        //roomObservableArray.add(room);
        filteredList.setPredicate(_ -> true);
        //TODO: change to room adapter kada napravis
        //searchableObservableList.add(room);
    }

    public ObservableList<UiComponent> getUserObservableArray() {
        return userObservableArray;
    }

    /*
    public ObservableList<UiComponent> getRooms() {
        return observableArray;
        //return roomObservableArray;
    }

     */

}
