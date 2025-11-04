package org.example.app;

import org.example.entity.*;
import org.example.utils.SearchMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    /**
     * Writes an array of {@link User} to STDOUT.
     *
     * @param users The array of type {@link User} to be displayed.
     */
    static void displayUsers(User[] users) {
        log.trace("Displaying users");
        if (users.length == 0) {
            System.out.println("Cant display users no users found");
            return;
        }

        for (Integer i = 0; i < users.length; i++) {
            System.out.println((i + 1) + ": " + users[i]);
        }
    }

    /**
     * Writes an array of {@link Room} to STDOUT.
     *
     * @param rooms The array of type {@link Room} to be displayed.
     */
    static void displayRooms(Room[] rooms) {
        log.trace("Displaying rooms");
        if (rooms.length == 0) {
            log.warn("Trying to display rooms while no rooms exist");
            System.out.println("Cant display rooms no rooms found");
            return;
        }

        for (Integer i = 0; i < rooms.length; i++) {
            System.out.println((i + 1) + ": " + rooms[i]);
        }
    }

    /**
     * Prints the interface to STDOUT for creating rooms.
     *
     * @param admin {@link Admin} that is creating the room.
     * @param sc    {@link Scanner} used for getting data for input.
     * @return the newly created {@link Room}.
     */
    static Room enterRoom(Admin admin, Scanner sc) {
        log.trace("Entering date for rooms from STDIN");
        System.out.println("Create room");
        System.out.println("Enter number of beds and price");
        return admin.createRoom(sc.nextInt(), sc.nextBigDecimal());
    }

    /**
     * Prints the interface to STDOUT for creating bookings.
     *
     * @param guest {@link Guest} that will book the room.
     * @param rooms Array of rooms {@link Room} that can be booked can't be of length 0.
     * @param sc    {@link Scanner} Used to get inout from user.
     * @return A new {@link Booking}.
     */
    static Booking enterGuest(Guest guest, Room[] rooms, Scanner sc) {
        System.out.println("user rooom");
        if (rooms.length == 0) {
            System.out.println("No rooms to book try another time or maybe create a room with admin");
            throw new IllegalArgumentException();
        }
        displayRooms(rooms);
        Integer roomIdx = sc.nextInt() - 1;
        sc.nextLine();

        System.out.println("Enter check in date and checkout day (dd/MM/yyyy)");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyyHH:mm");
        LocalDateTime checkIn = LocalDateTime.parse(sc.nextLine() + "08:00", fmt);
        LocalDateTime checkOut = LocalDateTime.parse(sc.nextLine() + "08:00", fmt);

        return guest.bookRoom(rooms[roomIdx], checkIn, checkOut);
    }

    /**
     * Prints the interface to STDOUT for creating a user.

     * @param sc {@link Scanner} user for getting the input for the user
     * @return A new {@link User}.
     * @throws IncorrectInputException Throws if Guest is Creat before admin because their will be no rooms see {@link #enterGuest(Guest, Room[], Scanner)}
     */
    static User enterUser(Scanner sc) throws IncorrectInputException {
        System.out.println("Enter 1) Admin 2) Guest");
        Integer choice = sc.nextInt();
        sc.nextLine();

        String userName;
        System.out.print("Enter name of user: ");
        userName = sc.nextLine();

        Integer userAge;
        System.out.print("Age of user: ");
        userAge = sc.nextInt();
        sc.nextLine();

        return switch (choice) {
            case 1 -> new Admin(userName, userAge);
            case 2 -> new Guest(userName, userAge);
            default -> throw new IncorrectInputException("User entered the input wrong error");
        };
    }

    static void main(String[] args) {
        log.trace("Program started");
        log.info("Program started");
        final Integer NUMBER_OF_CLASSES_TO_ENTER = 5;
        Room[] rooms = new Room[0];
        User[] users = new User[0];
        Booking[] bookings = new Booking[0];
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter " + NUMBER_OF_CLASSES_TO_ENTER + " users");
        for (Integer i = 0; i < NUMBER_OF_CLASSES_TO_ENTER; i++) {
            try {
                User newUser = enterUser(sc);
                switch (newUser) {
                    case Admin admin -> {
                        rooms = Arrays.copyOf(rooms, rooms.length + 1);
                        rooms[rooms.length - 1] = enterRoom(admin, sc);
                    }
                    case Guest guest -> {
                        bookings = Arrays.copyOf(bookings, bookings.length + 1);
                        bookings[bookings.length - 1] = enterGuest(guest, rooms, sc);
                    }
                    default -> throw new InputMismatchException("Didnt create user");
                }
                users = Arrays.copyOf(users, users.length + 1);
                users[users.length - 1] = newUser;
            } catch (IncorrectInputException | DateTimeParseException | IllegalArgumentException e) {
                log.error("Incorrect Input exception caught", e);
                System.out.println("Count create user something went wrong try again");
            }
        }

        SearchMenu menu = new SearchMenu(users, rooms, bookings);
        menu.display();
    }
}