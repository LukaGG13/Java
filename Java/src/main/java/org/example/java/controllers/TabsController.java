package org.example.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import org.example.java.HelloController;
import org.example.java.ui.RepostiryUiAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TabsController {

    @FXML
    private Tab createUserTab;

    @FXML
    private Tab searchTab;

    @FXML
    private Tab createRoomTab;

    private static final String HELLO_VIEW_TAB = "/org/example/java/hello-view.fxml";
    private static final String CREATE_USER_TAB = "/org/example/java/user-create.fxml";
    private static final String CREATE_ROOM_TAB = "/org/example/java/room-create.fxml";
    private static final Logger log = LoggerFactory.getLogger(TabsController.class);
    private final RepostiryUiAdapter repository;

    public TabsController(RepostiryUiAdapter repository){
       this.repository = repository;
    }

    @FXML
    private void initialize() {
        loadTab(searchTab, HELLO_VIEW_TAB);
        loadTab(createUserTab, CREATE_USER_TAB);
        loadTab(createRoomTab, CREATE_ROOM_TAB);
    }

    private void loadTab(Tab tab, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            switch (fxmlPath) {
                case HELLO_VIEW_TAB -> {
                    HelloController helloController = new HelloController(repository);
                    loader.setController(helloController);
                }
                case CREATE_USER_TAB -> {
                    UserCreateController userCreateController = new UserCreateController(repository);
                    loader.setController(userCreateController);
                }
                case CREATE_ROOM_TAB -> {
                    RoomCreateController roomCreateController = new RoomCreateController(repository);
                    loader.setController(roomCreateController);
                }
                case null, default -> throw new IllegalArgumentException("Tab path is invalid");

            }
            Parent content = loader.load();
            tab.setContent(content);
        } catch (IOException e) {
            log.error("Error while loading tab", e);
        }
    }
}
