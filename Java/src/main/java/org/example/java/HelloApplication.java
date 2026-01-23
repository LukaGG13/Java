package org.example.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.java.controllers.TabsController;
import org.example.java.entity.repository.DatabaseRepository;
import org.example.java.entity.repository.Repository;
import org.example.java.ui.RepositoryUiAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HelloApplication extends Application {
    private static final Logger log = LoggerFactory.getLogger(HelloApplication.class);
    @Override
    public void start(Stage stage) throws IOException {
        log.trace("Program started");
        log.info("Program started");


        /*
        Optional<User> activeUser = Optional.empty();

        List<Room> rooms = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Booking> bookings = new ArrayList<>();
        Map<Room, List<Review>> reviews = new HashMap<>();

        Mocker.mockRooms(rooms, 20);
        Mocker.mockUsers(users, 20);

        //Repository repository = new DataRepository(activeUser, rooms, users, bookings, reviews);
         */
        Repository repository = new DatabaseRepository();
        RepositoryUiAdapter repostiryUiAdapter = new RepositoryUiAdapter(repository);
        TabsController tabsController = new TabsController(repostiryUiAdapter);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("header-tab.fxml"));
        fxmlLoader.setController(tabsController);

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
