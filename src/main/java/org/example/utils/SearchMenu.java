package org.example.utils;


import org.example.entity.Booking;
import org.example.entity.Room;
import org.example.entity.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class SearchMenu {
    private final Scanner sc;
    private List<User> users;
    private List<Room> rooms;
    private List<Booking> bookings;

    public SearchMenu(List<User> users, List<Room> rooms, List<Booking> bookings) {
        this.users = users;
        this.rooms = rooms;
        this.bookings = bookings;
        this.sc = new Scanner(System.in);
    }

    public void display() {
        boolean condition = Boolean.TRUE;
        while (condition){
            System.out.println("1) User 2) Rooms 3) Booking");
            //TODO fix input mismatch exception
            switch (sc.nextInt()){
                case 1 -> new UserSearchMenu(users, sc).display();
                case 2 -> System.out.println("Not implemented");
                case 3 -> System.out.println("Not implemented");
                case 4 -> condition = Boolean.FALSE;
                default -> System.out.println("Option not found try again");
            }
        }
    }
    //TODO implemet this class
    private final static class BookingSearchMenu {}
    private final static class UserSearchMenu {
        private final Scanner sc;
        private final List<User> users;

        public UserSearchMenu(List<User> users, Scanner sc) {
            this.users = users;
            this.sc = sc;
        }

        private List<User> nameSubDisplay() {
            System.out.println("Enter name to be searched or (min/max)");
            String pattern = sc.nextLine();
            return switch (pattern){
                case "max" ->  users.stream().max(Comparator.comparing(User::getName)).stream().toList();
                case "min" ->  users.stream().min(Comparator.comparing(User::getName)).stream().toList();
                default -> users.stream().filter(name -> name.getName().equals(pattern)).toList();
            };
        }

        private List<User> ageSubDisplay() {
            System.out.println("Enter age to be searched or (min/max)");
            String pattern = sc.nextLine();
            return switch (pattern){
                case "max" ->  users.stream().max(Comparator.comparing(User::getAge)).stream().toList();
                case "min" ->  users.stream().min(Comparator.comparing(User::getAge)).stream().toList();
                default -> users.stream().filter(name -> name.getAge().equals(Integer.parseInt(pattern))).toList();
            };
        }
        public void display() {
            System.out.println("1) Name 2) Age");
            List<User> result = switch (sc.nextInt()){
                case 1 -> {
                    sc.nextLine();
                    yield nameSubDisplay();
                }

                case 2 -> {
                    sc.nextLine();
                    yield ageSubDisplay();
                }

                default -> new ArrayList<>();
            };
                result.forEach(System.out::println);
            }
        }
    }
