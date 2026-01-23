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
    final ObservableList<Room> roomObservableList;
    final FilteredList<UiComponent> filteredList;

    static final Logger log = LoggerFactory.getLogger(RepositoryUiAdapter.class);



    public RepositoryUiAdapter(Repository repository){
        this.repository = repository;

        userObservableArray = FXCollections.observableArrayList();
        for(var user : repository.getUsers()) {
            userObservableArray.add(new UserUiAdapter(user, this::updateUser, this::deleteUser));
        }

        roomObservableList = FXCollections.observableArrayList();
        filteredList = new FilteredList<>(userObservableArray);
        /*
        userObservableArray = FXCollections.observableArrayList(repository.getUsers());
        roomObservableList = FXCollections.observableArrayList(repository.getRooms());
        searchableObservableList = FXCollections.observableArrayList(repository.getUsers());
        searchableObservableList.addAll(repository.getRooms());
         */
    }

    /*
    public ObservableList<Searchable> getSearchible() {
       searchableObservableList.setAll(repository.getUsers());
       searchableObservableList.addAll(repository.getRooms());
       return searchableObservableList;
    }
    public ObservableList<User> getUsers() {
       return userObservableArray;
    }
    */

    public FilteredList<UiComponent> getUiFilterList() {
        return filteredList;
    }

    public void addUser(User user) {
        log.debug("Adding user to ui adapter {}", user);
        repository.addUser(user);
        userObservableArray.add(new UserUiAdapter(user, this::updateUser, this::deleteUser));
        filteredList.setPredicate(_ -> true);
    }

    public void deleteUser(User user) {
        repository.deleteUser(user);
        //TODO: ostatak cb
    }

    public void updateUser(User user) {
       repository.updateUser(user);
       //TODO: ostaloc
    }

    public void addRoom(Room room) {
       log.debug("Adding room to ui adapter {}", room);
        repository.addRoom(room);
        roomObservableList.add(room);
        //TODO: change to room adapter kada napravis
        //searchableObservableList.add(room);
    }

    public ObservableList<Room> getRooms() {
        return roomObservableList;
    }

}
