package org.example.app;

import org.example.entity.*;
import org.example.utils.SearchMenu;
import org.example.utils.UserSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    /**
     * Writes an {@link List} of {@link User} to STDOUT.
     *
     * @param users {@link List} of type {@link User} to be displayed.
     */
    static void displayUsers(List<User> users) {
        log.trace("Displaying users");
        if (users.isEmpty()) {
            System.out.println("Cant display users no users found");
            return;
        }

        for (Integer i = 0; i < users.size(); i++) {
            System.out.println((i + 1) + ": " + users.get(i));
        }
    }

    /**
     * Writes a {@link List} of {@link Room} to STDOUT>
     * @param rooms {@link List} of type {@link Room} to be displayed.
     * @param reviews {@link Map} where key is {@link Room} object, and value is a {@link List} of
     *                           {@link Review} objects left for that room.
     */
    static void displayRooms(List<Room> rooms, Map<Room, List<Review>> reviews) {
        log.trace("Displaying rooms and reviews");
        if (rooms.isEmpty()) {
            log.warn("Trying to display rooms while no rooms exist");
            System.out.println("Cant display rooms no rooms found");
            return;
        }


        for (Integer i = 0; i < rooms.size(); i++) {
            System.out.println((i + 1) + ": " + rooms.get(i));

            if(!reviews.containsKey(rooms.get(i))){
                continue;
            }

            for(Review review : reviews.get(rooms.get(i))) {
                System.out.println("\t" + review.guest().getName() + ": " + review.rating() + "/10" + "\n\t\t" + review.message());
            }
        }
    };
    /**
     * Writes an {@link List} of {@link Room} to STDOUT.
     *
     * @param rooms {@link List} of type {@link Room} to be displayed.
     */
    static void displayRooms(List<Room> rooms) {
        log.trace("Displaying rooms");
        if (rooms.isEmpty()) {
            log.warn("Trying to display rooms while no rooms exist");
            System.out.println("Cant display rooms no rooms found");
            return;
        }

        for (Integer i = 0; i < rooms.size(); i++) {
            System.out.println((i + 1) + ": " + rooms.get(i));
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
     * Function for user to leave a review.
     * @param guest {@link Guest} that will leave a review.
     * @param reviews {@link Map} of {@link User} and {@link String} review to which the user review will be added.
     * @param sc {@link Scanner} that will be used to get the input from the user.
     */
    static void leaveReview(Guest guest, Room room, Map<Room, List<Review>> reviews, Scanner sc) {
        System.out.println("Do you want to leave a review: (YES/no)");
        String answer = sc.nextLine();
        if("min".equals(answer)) {
            return;
        }

        System.out.println("Your review: ");
        String message = sc.nextLine();

        System.out.println("Your rating (1-10): ");
        Integer ratting = sc.nextInt();
        sc.nextLine();

        reviews.computeIfAbsent(room, k -> new ArrayList<>());
        reviews.get(room).add(new Review(guest, message, LocalDate.now(), ratting));
    }

    /**
     * Prints the interface to STDOUT for creating bookings.
     *
     * @param guest {@link Guest} that will book the room.
     * @param rooms {@link List} of rooms {@link Room} that can be booked can't be of length 0.
     * @param sc    {@link Scanner} Used to get inout from user.
     * @return A new {@link Booking}.
     */
    static Booking enterGuest(Guest guest, Map<Room, List<Review>> reviews, List<Room> rooms, Scanner sc) {
        System.out.println("user rooom");
        if (rooms.isEmpty()) {
            System.out.println("No rooms to book try another time or maybe create a room with admin");
            throw new IllegalArgumentException();
        }
        displayRooms(rooms, reviews);
        Integer roomIdx = sc.nextInt() - 1;
        sc.nextLine();

        System.out.println("Enter check in date and checkout day (dd/MM/yyyy)");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyyHH:mm");
        LocalDateTime checkIn = LocalDateTime.parse(sc.nextLine() + "08:00", fmt);
        LocalDateTime checkOut = LocalDateTime.parse(sc.nextLine() + "08:00", fmt);

        Booking booking = guest.bookRoom(rooms.get(roomIdx), checkIn, checkOut);

        leaveReview(guest,rooms.get(roomIdx), reviews, sc);

        return booking;
    }

    /**
     * Prints the interface to STDOUT for creating a user.

     * @param sc {@link Scanner} user for getting the input for the user
     * @return A new {@link User}.
     * @throws IncorrectInputException Throws if Guest is Creat before admin because their will be no rooms see {@link #enterGuest(Guest, List, Scanner)}
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

    public static void main(String[] args) {
        log.trace("Program started");
        log.info("Program started");
        final Integer NUMBER_OF_CLASSES_TO_ENTER = 0;
        List<Room> rooms = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Booking> bookings = new ArrayList<>();
        Map<Room, List<Review>> reviews = new HashMap<>();
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter " + NUMBER_OF_CLASSES_TO_ENTER + " users");
        for (Integer i = 0; i < NUMBER_OF_CLASSES_TO_ENTER; i++) {
            try {
                User newUser = enterUser(sc);
                switch (newUser) {
                    case Admin admin -> rooms.add(enterRoom(admin,sc));
                    case Guest guest -> bookings.add(enterGuest(guest, reviews, rooms, sc));
                    default -> throw new InputMismatchException("Didnt create user");
                }
                users.add(newUser);
            } catch (IncorrectInputException | DateTimeParseException | IllegalArgumentException | InputMismatchException e) {
                log.error("Incorrect Input exception caught", e);
                System.out.println("Count create user something went wrong try again");
            }
        }

        users.add(new Admin("Luka", 19));
        users.add(new Admin("Gabrijel", 20));
        users.add(new Admin("Ivan", 20));
        users.add(new Admin("Erik", 19));
        users.add(new Admin("Rubi", 20));
        users.add(new Admin("Lovro", 20));
        users.add(new Admin("abcd", 21));
        users.add(new Admin("aabd", 21));

        if (users.get(0) instanceof Admin a) {
            var room = a.createRoom(2, BigDecimal.valueOf(2.0));
            rooms.add(room);
        }
        bookings.add(new Booking(rooms.get(0), users.get(0), LocalDateTime.of(3000, 1, 1, 1, 1), LocalDateTime.of(3000,2,1,1,1)));
        //SearchMenu menu = new SearchMenu(users, rooms, bookings);
        //menu.display();

        //UserSorter.sort(users);
        //UserSorter.sortByAge(users);
        UserSorter.sortByName(users);
        users.reversed().forEach(System.out::println);
        System.out.println();
        System.out.println();
        users.forEach(System.out::println);

        System.out.println();
        System.out.println();

        Map<LocalDateTime, List<Booking>> result = bookings.stream().collect(Collectors.groupingBy(Booking::checkIn));
        result.forEach(((k, v) -> v.forEach(System.out::println)));
    }
}