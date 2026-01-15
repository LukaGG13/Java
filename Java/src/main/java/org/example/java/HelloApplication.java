package org.example.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.java.controllers.TabsController;
import org.example.java.entity.booking.Booking;
import org.example.java.entity.repository.DataRepository;
import org.example.java.entity.repository.Repository;
import org.example.java.entity.review.Review;
import org.example.java.entity.room.Room;
import org.example.java.entity.user.User;
import org.example.java.ui.RepostiryUiAdapter;
import org.example.java.utils.Mocker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class HelloApplication extends Application {
    private static final Logger log = LoggerFactory.getLogger(HelloApplication.class);
    @Override
    public void start(Stage stage) throws IOException {
        log.trace("Program started");
        log.info("Program started");

        Optional<User> activeUser = Optional.empty();

        List<Room> rooms = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Booking> bookings = new ArrayList<>();
        Map<Room, List<Review>> reviews = new HashMap<>();

        Mocker.mockRooms(rooms, 20);
        Mocker.mockUsers(users, 20);

        Repository repository = new DataRepository(activeUser, rooms, users, bookings, reviews);
        RepostiryUiAdapter repostiryUiAdapter = new RepostiryUiAdapter(repository);
        TabsController tabsController = new TabsController(repostiryUiAdapter);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("header-tab.fxml"));
        fxmlLoader.setController(tabsController);

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
