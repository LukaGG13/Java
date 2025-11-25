package org.example.app;

import org.example.entity.*;
import org.example.utils.Mocker;
import org.example.utils.UserSorter;
import org.example.utils.menus.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.List;

import static java.lang.IO.println;

@SuppressWarnings("java:S1118")
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    //TODO java doc i logs pls
    static void main() {
        log.trace("Program started");
        log.info("Program started");

        Optional<User> activeUser = Optional.empty();

        List<Room> rooms = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Booking> bookings = new ArrayList<>();
        Map<Room, List<Review>> reviews = new HashMap<>();

        Mocker.mockRooms(rooms, 3);
        Mocker.mockUsers(users, 3);


        Repository repository = new DataRepository(activeUser, rooms, users, bookings, reviews);

        boolean exitProgram = false;
        while(!exitProgram){
            MenuInterface menu = switch (activeUser.orElse(null)) {
               case Admin _ -> new AdminMenu(repository);
               case Guest _ -> new GuestMenu(repository);
               case null, default -> new DefaultMenu(repository);
            };

            println();
            println();
            menu.printMenu();
            menu.askChoice();
            switch (menu.doChoice()){
                case MenuResult.LoggedOut _ -> {
                    activeUser = Optional.empty();
                    repository = new DataRepository(activeUser, rooms, users, bookings, reviews);
                }
                case MenuResult.RoomCreated(Room room) -> rooms.add(room);
                case MenuResult.BookingCreated(Booking booking) -> bookings.add(booking);
                case MenuResult.AdminCreated(Admin admin) -> users.add(admin);
                case MenuResult.GuestCreated(Guest guest) -> users.add(guest);
                case MenuResult.ReviewCreated roomReviewSimpleEntry -> {}//TODO
                case MenuResult.LogIn(User user) -> {
                   activeUser = Optional.of(user);
                   repository = new DataRepository(activeUser, rooms, users, bookings, reviews);
                }
                case MenuResult.ExitProgram _ -> exitProgram = true;
                case MenuResult.Error(String msg) -> println(msg + " try something else");
                default -> throw new IllegalStateException("Unexpected value: " + menu.doChoice());
            }
        }
        println(UserSorter.min(users, User::getAge));
        println(UserSorter.max(users, User::getAge));
    }
}