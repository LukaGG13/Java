package app;

import entity.*;
import utils.SearchMenu;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    static void displayUsers(User[] users) {
        if (users.length == 0) {
            System.out.println("Cant display users no users found");
            return;
        }

        for (Integer i = 0; i < users.length; i++) {
            System.out.println((i + 1) + ": " + users[i]);
        }
    }

    static void displayRooms(Room[] rooms) {
        if (rooms.length == 0) {
            System.out.println("Cant display rooms no rooms found");
            return;
        }

        for (Integer i = 0; i < rooms.length; i++) {
            System.out.println((i + 1) + ": " + rooms[i]);
        }
    }

    static void enterUser(User[] users, Room[] rooms, Booking[] bookings, Integer n, Scanner sc) {
        for (Integer i = 0; i < n; i++) {
            try {
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

                users = Arrays.copyOf(users, users.length + 1);
                if (choice == 1) {
                    users[i] = new Admin(userName, userAge);
                    if (users[i] instanceof Admin admin) {
                        System.out.println("Create room");
                        System.out.println("Enter number of beds and price");

                        rooms = Arrays.copyOf(rooms, rooms.length + 1);
                        rooms[rooms.length - 1] = admin.createRoom(sc.nextInt(), sc.nextBigDecimal());
                        sc.nextLine();
                    }
                } else if (choice == 2) {
                    users[i] = new Guest(userName, userAge);
                    if (users[i] instanceof Guest guest) {
                        System.out.println("Create booking for user");
                        System.out.println("Chose room ");
                        if (rooms.length == 0) {
                            System.out.println("No rooms to book try another time or maybe create a room with admin");
                            continue;
                        }
                        displayRooms(rooms);
                        Integer roomIdx = sc.nextInt() - 1;
                        sc.nextLine();

                        System.out.println("Enter check in date and checkout day (dd/MM/yyyy)");
                        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyyHH:mm");
                        LocalDateTime checkIn = LocalDateTime.parse(sc.nextLine() + "08:00", fmt);
                        LocalDateTime checkOut = LocalDateTime.parse(sc.nextLine() + "08:00", fmt);

                        bookings = Arrays.copyOf(bookings, bookings.length + 1);
                        bookings[bookings.length - 1] = guest.bookRoom(rooms[roomIdx], checkIn, checkOut);
                    }
                }
            } catch (InputMismatchException _) {
                sc.nextLine();
                System.out.println("Wrong input try again");
                i--;
            }
        }
    }

    static void main(String[] args) {
        final Integer NUMBER_OF_CLASSES_TO_ENTER = 5;
        Room[] rooms = new Room[0];
        User[] users = new User[0];
        Booking[] bookings = new Booking[0];
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter " + NUMBER_OF_CLASSES_TO_ENTER + " users");
        enterUser(users, rooms, bookings, NUMBER_OF_CLASSES_TO_ENTER, sc);

        SearchMenu menu = new SearchMenu(users, rooms, bookings);
        menu.display();
    }
}