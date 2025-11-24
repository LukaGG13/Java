package org.example.utils;

import org.example.entity.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.lang.IO.print;
import static java.lang.IO.println;

public class InputOutputUtils {
    private InputOutputUtils() {}
    private static final Logger log = LoggerFactory.getLogger(InputOutputUtils.class);

    /**
     * Writes an {@link List} of {@link User} to STDOUT.
     *
     * @param list to be displayed.
     */
    public static void printList(List<?> list) {
        log.trace("Displaying list");
        if (list.isEmpty()) {
            println("Cant display list empty");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            println((i + 1) + ": " + list.get(i));
        }
    }

    /**
     * Writes a {@link List} of {@link Room} to STDOUT>
     * @param rooms {@link List} of type {@link Room} to be displayed.
     * @param reviews {@link Map} where key is {@link Room} object, and value is a {@link List} of
     *                           {@link Review} objects left for that room.
     */
    public static void displayRooms(List<Room> rooms, Map<Room, List<Review>> reviews) {
        log.trace("Displaying rooms and reviews");
        if (rooms.isEmpty()) {
            log.warn("Trying to display rooms while no rooms exist");
            println("Cant display rooms no rooms found");
            return;
        }


        for (int i = 0; i < rooms.size(); i++) {
            println((i + 1) + ": " + rooms.get(i));

            if(!reviews.containsKey(rooms.get(i))){
                continue;
            }

            for(Review review : reviews.get(rooms.get(i))) {
                println("\t" + review.guest().getName() + ": " + review.rating() + "/10" + "\n\t\t" + review.message());
            }
        }
    }

    /**
     * Prints the interface to STDOUT for creating rooms.
     *
     * @param admin {@link Admin} that is creating the room.
     * @param sc    {@link Scanner} used for getting data for input.
     * @return the newly created {@link Room}.
     */
    /*
    public static Room enterRoom(Admin admin, Scanner sc) {
        log.trace("Entering date for rooms from STDIN");
        println("Create room");
        println("Enter number of beds and price");
        return admin.createRoom(sc.nextInt(), sc.nextBigDecimal());
    }
    */
    /**
     * Function for user to leave a review.
     * @param guest {@link Guest} that will leave a review.
     * @param reviews {@link Map} of {@link User} and {@link String} review to which the user review will be added.
     * @param sc {@link Scanner} that will be used to get the input from the user.
     */
    /*
    public static void leaveReview(Guest guest, Room room, Map<Room, List<Review>> reviews, Scanner sc) {
        println("Do you want to leave a review: (YES/no)");
        String answer = sc.nextLine();
        if("no".equals(answer)) {
            return;
        }

        println("Your review: ");
        String message = sc.nextLine();

        println("Your rating (1-10): ");
        Integer ratting = sc.nextInt();
        sc.nextLine();

        reviews.computeIfAbsent(room, _ -> new ArrayList<>());
        reviews.get(room).add(new Review(guest, message, LocalDate.now(), ratting));
    }
    */
    /**
     * Prints the interface to STDOUT for creating bookings.
     *
     * @param guest {@link Guest} that will book the room.
     * @param rooms {@link List} of rooms {@link Room} that can be booked can't be of length 0.
     * @param sc    {@link Scanner} Used to get inout from user.
     * @return A new {@link Booking}.
     */
    /*
    public static Booking enterGuest(Guest guest, Map<Room, List<Review>> reviews, List<Room> rooms, Scanner sc) {
        println("user room");
        if (rooms.isEmpty()) {
            println("No rooms to book try another time or maybe create a room with admin");
            throw new IllegalArgumentException();
        }
        displayRooms(rooms, reviews);
        int roomIdx = sc.nextInt() - 1;
        sc.nextLine();

        println("Enter check in date and checkout day (dd/MM/yyyy)");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyyHH:mm");
        LocalDateTime checkIn = LocalDateTime.parse(sc.nextLine() + "08:00", fmt);
        LocalDateTime checkOut = LocalDateTime.parse(sc.nextLine() + "08:00", fmt);

        Booking booking = guest.bookRoom(rooms.get(roomIdx), checkIn, checkOut);

        leaveReview(guest,rooms.get(roomIdx), reviews, sc);

        return booking;
    }
    */
    /**
     * Prints the interface to STDOUT for creating a user.

     * @param sc {@link Scanner} user for getting the input for the user
     * @return A new {@link User}.
     * @throws IncorrectInputException Throws if Guest is Created before admin because their will be no rooms see {@link #enterGuest(Guest, Map, List, Scanner)}
     */
    /*
    public static User enterUser(Scanner sc) throws IncorrectInputException {
        println("Enter 1) Admin 2) Guest");
        int choice = sc.nextInt();
        sc.nextLine();

        String userName;
        print("Enter name of user: ");
        userName = sc.nextLine();

        Integer userAge;
        print("Age of user: ");
        userAge = sc.nextInt();
        sc.nextLine();

        return switch (choice) {
            case 1 -> new Admin(userName, userAge);
            case 2 -> new Guest(userName, userAge);
            default -> throw new IncorrectInputException("User entered the input wrong error");
        };
    }
    */
}
