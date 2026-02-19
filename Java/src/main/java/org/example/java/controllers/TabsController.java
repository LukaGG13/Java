package org.example.java.controllers;

import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.example.java.HelloController;
import org.example.java.components.AuthorizedTab;
import org.example.java.entity.booking.Booking;
import org.example.java.entity.room.Room;
import org.example.java.services.LoginService;
import org.example.java.ui.BookingUiAdapter;
import org.example.java.ui.RepositoryUiAdapter;
import org.example.java.ui.RoomUiAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

public class TabsController {


    @FXML
    private Tab createReviewTab;

    @FXML
    private Tab createRoomTab;

    @FXML
    private Tab createUserTab;

    @FXML
    private Tab searchTab;

    //TODO: pogledat kako da se utomatcki dodaju tabovi
    @FXML
    private TabPane tabPane;

    @FXML
    private Tab loginTab;

    @FXML
    private Tab createBookingTab;

    private static final String HELLO_VIEW_TAB = "/org/example/java/hello-view.fxml";
    private static final String CREATE_USER_TAB = "/org/example/java/user-create.fxml";
    private static final String CREATE_ROOM_TAB = "/org/example/java/room-create.fxml";
    private static final String CREATE_REVIEW_TAB = "/org/example/java/review-create.fxml";
    private static final String CREATE_BOOKING_TAB = "/org/example/java/booking-create.fxml" ;
    private static final String LOGIN_TAB = "/org/example/java/login-view.fxml";
    private static final Logger log = LoggerFactory.getLogger(TabsController.class);
    private final RepositoryUiAdapter repository; //TODO: temp fix tech dept

    public TabsController(RepositoryUiAdapter repository){
       this.repository = repository;
    }


    /*
    @FXML
    private void initialize() {

        var authorizedLoginTab = new AuthorizedTab(loginTab, LoginService.AuthorizationLevelOfRole.UNSIGNED.getAuthorizationLevel());
        var authorizedSearchTab = new AuthorizedTab(searchTab, LoginService.AuthorizationLevelOfRole.GUEST.getAuthorizationLevel());
        loadTab(authorizedLoginTab, LOGIN_TAB);
        //searchTab = new AuthorizedTab(5); //TODO: remove magick numbers
        //createUserTab = new AuthorizedTab(10_000);
        //loginTab = new AuthorizedTab(10_000);
       // var authorizedSearchTab = new AuthorizedTab(searchTab, 5);
       // var tab1 = new AuthorizedTab(loginTab, 10_000);
       // var tab2 = new AuthorizedTab(createUserTab, 10_000);
       // loadTab(authorizedSearchTab, HELLO_VIEW_TAB);
       // loadTab(tab1, LOGIN_TAB);
       // loadTab(tab2, CREATE_USER_TAB);
        //loadTab(searchTab, HELLO_VIEW_TAB);
        //loadTab(createUserTab, CREATE_USER_TAB);
        // loadTab(createRoomTab, CREATE_ROOM_TAB);
        // loadTab(createReviewTab, CREATE_REVIEW_TAB);
        //loadTab(loginTab, LOGIN_TAB);
    }

   */
    //TODO: refactor
    /*
    @FXML
    private void initialize() {


        tabPane.getTabs().removeAll();
        //TODO: remove sve iz gui i rucno dodaj bilo bi nice da se moze loopat
        tabPane.getTabs().add(createAuthorizedTabAndLoad(loginTab, LOGIN_TAB, LoginService.AuthorizationLevelOfRole.UNSIGNED));

        //createAuthorizedTabAndLoad(createUserTab, CREATE_USER_TAB, LoginService.AuthorizationLevelOfRole.UNSIGNED);
        //createAuthorizedTabAndLoad(searchTab, HELLO_VIEW_TAB, LoginService.AuthorizationLevelOfRole.GUEST);
        //createAuthorizedTabAndLoad(createReviewTab, CREATE_REVIEW_TAB, LoginService.AuthorizationLevelOfRole.GUEST);
        //createAuthorizedTabAndLoad(createRoomTab, CREATE_ROOM_TAB, LoginService.AuthorizationLevelOfRole.ADMIN);


        var loginService = new LoginService(repository.toRepository());
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldTab, newTab) -> {
                        if (!loginService.authorization((AuthorizedTab) newTab)) {

                            // ❌ Not authorized → revert selection
                            tabPane.getSelectionModel().select(oldTab);

                            // Optional: feedback
                            log.error("Access denied");
                        }
                }
        );

    }


     */

    @FXML
    private void initialize() {

        tabPane.getTabs().clear();

        var loginService = new LoginService(repository.toRepository());

        AuthorizedTab login = new AuthorizedTab(
                loginTab.getText(),
                loginTab,
                LoginService.AuthorizationLevelOfRole.UNSIGNED.getAuthorizationLevel()
        );
        loadAuthorizedTab(login, LOGIN_TAB);

        AuthorizedTab search = new AuthorizedTab(
                searchTab.getText(),
                searchTab,
                LoginService.AuthorizationLevelOfRole.GUEST.getAuthorizationLevel()
        );
        loadAuthorizedTab(search, HELLO_VIEW_TAB);

        AuthorizedTab review = new AuthorizedTab(
                createReviewTab.getText(),
                createReviewTab,
                LoginService.AuthorizationLevelOfRole.GUEST.getAuthorizationLevel()
        );
        loadAuthorizedTab(review, CREATE_REVIEW_TAB);

        AuthorizedTab booking = new AuthorizedTab(
                createBookingTab.getText(),
                createBookingTab,
                LoginService.AuthorizationLevelOfRole.GUEST.getAuthorizationLevel()
        );
        loadAuthorizedTab(booking, CREATE_BOOKING_TAB);

        AuthorizedTab room = new AuthorizedTab(
                createRoomTab.getText(),
                createRoomTab,
                LoginService.AuthorizationLevelOfRole.ADMIN.getAuthorizationLevel()
        );
        loadAuthorizedTab(room, CREATE_ROOM_TAB);

        AuthorizedTab user = new AuthorizedTab(
                createUserTab.getText(),
                createUserTab,
                LoginService.AuthorizationLevelOfRole.UNSIGNED.getAuthorizationLevel()
        );
        loadAuthorizedTab(user, CREATE_USER_TAB);

        tabPane.getTabs().addAll(
                login,
                search,
                review,
                booking,
                room,
                user
        );

        tabPane.getSelectionModel().selectedItemProperty().addListener(
                (_, oldTab, newTab) -> {

                    if (!(newTab instanceof AuthorizedTab authorizedTab)) {
                        return;
                    }

                    if (!loginService.authorization(authorizedTab)) {
                        tabPane.getSelectionModel().select(oldTab);
                        new Alert(Alert.AlertType.ERROR, "You can't access this tab").showAndWait();
                        log.error("Access denied");
                    }
                }
        );
    }


    private AuthorizedTab createAuthorizedTabAndLoad(Tab tab, String fxmlPath, LoginService.AuthorizationLevelOfRole role) {
        var authorizedTab = new AuthorizedTab("my super secure tab", tab, role.getAuthorizationLevel());
        loadAuthorizedTab(authorizedTab, fxmlPath);
        return authorizedTab;
    }




    private void loadAuthorizedTab(AuthorizedTab tab, String fxmlPath) {
        try {
            // TODO: teastfull susatav
            var loginService = new LoginService(repository.toRepository());
//            if (!loginService.authorization(tab)) return;

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            switch (fxmlPath) {
                case LOGIN_TAB -> {
                    var loginController = new LoginController(loginService, repository);
                    loader.setController(loginController);
                }
                case HELLO_VIEW_TAB -> {
                    var helloController = new HelloController(repository);
                    loader.setController(helloController);
                }
                case CREATE_USER_TAB -> {
                    var userCreateController = new UserCreateController(repository);
                    loader.setController(userCreateController);
                }
                case CREATE_ROOM_TAB -> {
                    /*
                    var roomCreateController = new RoomCreateController(
                            () -> RoomUiAdapter.of(new Room.RoomBuilder(2, new BigDecimal(100)).build(), repository::updateRoom, repository::deleteRoom),
                            repository::addRoom
                    );
                    */
                     //roomCreateController = new RoomCreateController(room -> RoomUiAdapter.of(room, repository::updateRoom, repository::deleteRoom), repository::addRoom);
                    RoomCreateController roomCreateController = new RoomCreateController(room -> RoomUiAdapter.of(room, repository::updateRoom, repository::deleteRoom), repository::addRoom);
                    loader.setController(roomCreateController);
                }
                case CREATE_REVIEW_TAB -> {
                    ReviewCreateController reviewCreateController = new ReviewCreateController(repository);
                    loader.setController(reviewCreateController);
                }
                case CREATE_BOOKING_TAB -> {
                    BookingCreateController bookingCreateController = new BookingCreateController(
                            repository,
                            booking -> BookingUiAdapter.of(booking, repository::updateBooking, repository::deleteBooking),
                            repository::addBooking
                    );
                    loader.setController(bookingCreateController);
                }
                case null, default -> throw new IllegalArgumentException("Tab path is invalid");

            }
            Parent content = loader.load();
            //tab.fuckYouApiSetContent(content);
            tab.setContent(content);
        } catch (IOException e) {
            log.error("Error while loading tab", e);
        }
    }
}
