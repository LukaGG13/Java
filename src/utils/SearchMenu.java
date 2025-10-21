package utils;

import entity.Booking;
import entity.Room;
import entity.User;

import java.util.Scanner;

public class SearchMenu {
    private final Scanner sc;
    private User[] users;
    private Room[] rooms;
    private Booking[] bookings;

    public SearchMenu(User[] users, Room[] rooms, Booking[] bookings) {
        this.users = users;
        this.rooms = rooms;
        this.bookings = bookings;
        this.sc = new Scanner(System.in);
    }

    public void display() {
        boolean condition = Boolean.TRUE;
        while (condition){
            System.out.println("1) User 2) Rooms 3) Booking");
            switch (sc.nextInt()){
                case 1 -> new UserSearchMenu(users, sc).display();
                case 2 -> System.out.println("Not implemented");
                case 3 -> System.out.println("Not implemented");
                case 4 -> condition = Boolean.FALSE;
                default -> System.out.println("Option not found try again");
            }
        }
    }
    private static class UserSearchMenu {
        private Scanner sc;
        private User[] users;
        private UserFilter filter;
        public UserSearchMenu(User[] users, Scanner sc) {
            this.users = users;
            this.sc = sc;
            this.filter = new UserFilter();
        }
        private User[] nameSubDisplay() {
            System.out.println("Enter name to be searched or (min/max)");
            String pattern = sc.nextLine();
            return switch (pattern){
                case "min" -> new User[] {filter.findMinName(users)};
                case "max" -> new User[] {filter.findMaxName(users)};
                default -> filter.filterUserByName(users, pattern);
            };
        }

        private User[] ageSubDisplay() {
            System.out.println("Enter age to be searched or (min/max)");
            String pattern = sc.nextLine();
            return switch (pattern){
                case "min" -> new User[] {filter.findMinAge(users)};
                case "max" -> new User[] {filter.findMaxAge(users)};
                default -> filter.filterUserByAge(users, Integer.parseInt(pattern));
            };
        }
        public void display() {
            System.out.println("1) Name 2) Age");
            User[] result = switch (sc.nextInt()){
                case 1 -> {
                    sc.nextLine();
                    yield nameSubDisplay();
                }

                case 2 -> {
                    sc.nextLine();
                    yield ageSubDisplay();
                }

                default -> new User[0];
            };
            for (Integer i = 0; i < result.length; i++) {
                System.out.println(result[i].getName() + " " + result[i].getAge());
            }
        }
    }
}