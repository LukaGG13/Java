package app;

import entity.Booking;
import entity.Room;
import entity.User;
import utils.SearchMenu;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    static void displayUsers (User[] users) {
        for (Integer i = 0; i < users.length; i++) {
            System.out.println((i + 1) + ": " + users[i].getName() + " " + users[i].getAge());
        }
    }

    static void displayRooms (Room[] rooms) {
        for (Integer i = 0; i < rooms.length; i++) {
            System.out.println((i + 1) +": Number of beds: " + rooms[i].getNumOfBeds() + " Size in m^2: " + rooms[i].getSizeInSqrM());
        }
    }

    static void enterUser(User[] users, Integer n , Scanner sc) {
        for(Integer i = 0; i < n; i++) {
            String userName;
            System.out.print("Enter name of user: ");
            userName = sc.nextLine();

            Integer userAge;
            System.out.print("Age of user: ");
            userAge = sc.nextInt();
            sc.nextLine();

            users[i] = new User(userName, userAge);
        }
    }

    static void enterRoom(Room[] rooms, Integer n, Scanner sc) {
        for(Integer i = 0; i < n; i++) {
            Integer numOfBeds;
            System.out.print("Enter number of beds: ");
            numOfBeds = sc.nextInt();
            sc.nextLine();

            Integer sizeInSqM;
            System.out.print("Size of the room in square meters: ");
            sizeInSqM = sc.nextInt();
            sc.nextLine();

            rooms[i] = new Room(numOfBeds, sizeInSqM);
        }
    }

    static void enterBooking(Room[] rooms, User[] users, Booking[] bookings, Integer n, Scanner sc) {
        for (Integer i = 0; i < n; i++) {
            Integer idxRoom;
            System.out.println("Select one room to be booked: ");
            displayRooms(rooms);
            idxRoom = sc.nextInt();
            sc.nextLine();

            Integer idxUser;
            System.out.println("Select one used to book the room: ");
            displayUsers(users);
            idxUser = sc.nextInt();
            sc.nextLine();

            System.out.println("Enter check in date: ");
            LocalDate checkIn = LocalDate.parse(sc.nextLine());

            System.out.println("Enter check out date: ");
            LocalDate checkOut = LocalDate.parse(sc.nextLine());

            bookings[i] = new Booking(rooms[idxRoom - 1], users[idxUser - 1], checkIn, checkOut);
        }
    }

    static void main() {
        final Integer NUMBER_OF_CLASSES_TO_ENTER = 5;
        Room[] rooms = new Room[NUMBER_OF_CLASSES_TO_ENTER];
        User[] users = new User[NUMBER_OF_CLASSES_TO_ENTER];
        Booking[] bookings = new Booking[NUMBER_OF_CLASSES_TO_ENTER];
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter " + NUMBER_OF_CLASSES_TO_ENTER + " users");
        enterUser(users, NUMBER_OF_CLASSES_TO_ENTER, sc);

        System.out.println("Enter " + NUMBER_OF_CLASSES_TO_ENTER + " rooms");
        enterRoom(rooms, NUMBER_OF_CLASSES_TO_ENTER, sc);

        System.out.println("Enter " + NUMBER_OF_CLASSES_TO_ENTER + " booking");
        enterBooking(rooms, users, bookings,  NUMBER_OF_CLASSES_TO_ENTER, sc);

        SearchMenu menu = new SearchMenu(users, rooms, bookings);
        menu.display();

    }
}