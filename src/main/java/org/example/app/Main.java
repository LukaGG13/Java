package org.example.app;

import org.example.entity.*;
import org.example.utils.Mocker;
import org.example.utils.menus.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.lang.IO.println;

@SuppressWarnings("java:S1118")
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

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

            menu.printMenu();
            menu.askChoice();
            switch (menu.doChoice()){
                case MenuResult.LoggedOut _ -> {
                    println("loggin out");
                    activeUser = Optional.empty();
                    repository = new DataRepository(activeUser, rooms, users, bookings, reviews);
                }
                case MenuResult.RoomCreated(Room room) -> rooms.add(room);
                case MenuResult.BookingCreated(Booking booking) -> bookings.add(booking);
                case MenuResult.AdminCreated(Admin admin) -> users.add(admin);
                case MenuResult.GuestCreated(Guest guest) -> users.add(guest);
                case MenuResult.ReviewCreated roomReviewSimpleEntry -> {}//TODO
                case MenuResult.LogIn(User user) -> {
                    println("loggin in");
                   activeUser = Optional.of(user);
                   repository = new DataRepository(activeUser, rooms, users, bookings, reviews);
                }
                case MenuResult.ExitProgram _ -> exitProgram = true;
                case MenuResult.Error(String msg) -> println(msg + " try something else");
                default -> throw new IllegalStateException("Unexpected value: " + menu.doChoice());
            }
        }

        /**
        println("Enter " + NUMBER_OF_CLASSES_TO_ENTER + " users");
        for (int i = 0; i < NUMBER_OF_CLASSES_TO_ENTER; i++) {
            try {
                User newUser = enterUser(sc);
                switch (newUser) {
                    case Admin admin -> rooms.add(enterRoom(admin,sc));
                    case Guest guest -> bookings.add(enterGuest(guest, reviews, rooms, sc));
                    default -> throw new InputMismatchException("Didn't create user");
                }
                users.add(newUser);
            } catch (IncorrectInputException | DateTimeParseException | IllegalArgumentException | InputMismatchException e) { //TODO mislim da je kamen u glavu
                log.error("Incorrect Input exception caught", e);
                println("Count create user something went wrong try again");
            }
        }

        Mocker.mockUsers(users);
        Mocker.mockRooms(rooms);
        Mocker.mockBookings(bookings);

        println("Random users:");
        printList(users);

        println("Random rooms:");
        printList(rooms);

        println("Random bookings:");
        printList(bookings);

        UserSorter.sortByName(users);
        users.reversed().forEach(IO::println);
        println();

        println();
        println();

        Map<LocalDateTime, List<Booking>> result = bookings.stream().collect(Collectors.groupingBy(Booking::checkIn));
        result.forEach(((_, v) -> v.forEach(IO::println)));

        var minUser = UserSorter.min(users, User::getAge);
        if(minUser.isPresent()){
            println("min user is -> " + minUser.orElseThrow());
        } else {
            println("No min user found");
        }
         **/
    }
}