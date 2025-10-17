package app;

import entity.Booking;
import entity.Room;
import entity.User;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static Integer n = 5;
    private static Room[] rooms = new Room[n];
    private static User[] users = new User[n];
    private static Booking[] bookings = new Booking[n];
    private static Scanner sc = new Scanner(System.in);

    private Main() {

    }

    static void displayUsers () {
        for (Integer i = 0; i < users.length; i++) {
            System.out.println((i + 1) + ": " + users[i].getName() + " " + users[i].getAge());
        }
    }

    static void displayRooms () {
        for (Integer i = 0; i < rooms.length; i++) {
            System.out.println((i + 1) +": Number of beds: " + rooms[i].getNumOfBeds() + " Size in m^2: " + rooms[i].getSizeInSqrM());
        }
    }

    static void enterUser() {
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

    static void enterRoom() {
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

    static void enterBooking() {
        for (Integer i = 0; i < n; i++) {
            Integer idxRoom;
            System.out.println("Select one room to be booked: ");
            displayRooms();
            idxRoom = sc.nextInt();
            sc.nextLine();

            Integer idxUser;
            System.out.println("Select one used to book the room: ");
            displayUsers();
            idxUser = sc.nextInt();
            sc.nextLine();

            System.out.println("Enter check in date: ");
            LocalDate checkIn = LocalDate.parse(sc.nextLine());

            System.out.println("Enter check out date: ");
            LocalDate checkOut = LocalDate.parse(sc.nextLine());

            bookings[i] = new Booking(rooms[idxRoom - 1], users[idxUser - 1], checkIn, checkOut);
        }
    }

    static void filterUserByAge(Integer targetAge) {
        for(Integer i = 0; i < n; i++) {
            if(Objects.equals(users[i].getAge(), targetAge)){
                System.out.println(users[i].getName() + " " + users[i].getAge());
            }
        }
    }

    static void filterUserByName(String targetName) {
        for(Integer i = 0; i < n; i++) {
            if(users[i].getName().equals(targetName)) {
                System.out.println(users[i].getName() + " " + users[i].getAge());
            }
        }
    }

    static User findMinAge() {
        User result = users[0];
        for(int i = 1; i < n; i++){
           if(users[i].getAge() < result.getAge()) {
               result = users[i];
           }
        }
        return result;
    }

    static User findMaxAge() {
        User result = users[0];
        for(int i = 1; i < n; i++){
            if(users[i].getAge() > result.getAge()) {
                result = users[i];
            }
        }
        return result;
    }

    static void filterUser() {
        System.out.println("1) Name 2) Age 3) Max age 4) Min age");
        Integer query = sc.nextInt();
        sc.nextLine();

        if (query == 1) {
            System.out.println("Enter name to be searched");
            filterUserByName(sc.nextLine());
        }
        if (query == 2) {
            System.out.println("Enter age to be searched");
            filterUserByAge(Integer.parseInt(sc.nextLine()));
        }
        if (query == 3) {
            User oldest = findMaxAge();
            System.out.println("Oldest user is " + oldest.getName() + " " + oldest.getAge());
        }
        if (query == 4) {
            User youngest = findMinAge();
            System.out.println("Youngest user is " + youngest.getName() + " " + youngest.getAge());
        }
    }

    static void main() {
        System.out.println("Enter " + n + " users");
        enterUser();

        System.out.println("Enter " + n + " rooms");
        enterRoom();

        System.out.println("Enter " + n + " booking");
        enterBooking();

        while (true) {
            System.out.println("Search:");
            System.out.println("1) Users 2) Quit");
            Integer query = sc.nextInt();
            sc.nextLine();
            if (query == 1) {
                filterUser();
            }
            if(query == 2) {
                break;
            }
        }
    }
}