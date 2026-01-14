package org.example.java.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import org.example.java.entity.interfaces.Searchable;
import org.example.java.entity.repository.Repository;
import org.example.java.entity.room.Room;
import org.example.java.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepostiryUiAdapter {
    //TODO poparvio ovo smece
    final Repository repository;
    final ObservableList<User> userObservableArray;
    final ObservableList<Room> roomObservableList;
    final ObservableList<Searchable> searchableObservableList;

    static final Logger log = LoggerFactory.getLogger(RepostiryUiAdapter.class);

    public RepostiryUiAdapter(Repository repository){
        this.repository = repository;
        userObservableArray = FXCollections.observableArrayList(repository.getUsers());
        roomObservableList = FXCollections.observableArrayList(repository.getRooms());
        searchableObservableList = FXCollections.observableArrayList(repository.getUsers());
        searchableObservableList.addAll(repository.getRooms());
    }

    public ObservableList<Searchable> getSearchible() {
       searchableObservableList.setAll(repository.getUsers());
       searchableObservableList.addAll(repository.getRooms());
       return searchableObservableList;
    }
    public ObservableList<User> getUsers() {
       return userObservableArray;
    }

    public void addUser(User user) {
        log.debug("Ading user to ui adapter {}", user);
        repository.addUser(user);
        userObservableArray.add(user);
        searchableObservableList.add(user);
    }

    public ObservableList<Room> getRooms() {
        return roomObservableList;
    }
}
