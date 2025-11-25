package org.example.utils.menus;

import org.example.entity.Admin;
import org.example.entity.Guest;
import org.example.entity.Repository;
import org.example.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.lang.IO.println;
import static java.lang.IO.readln;

import static org.example.utils.InputOutputUtils.*;

public class DefaultMenu implements MenuInterface{
    private final List<String> choices = List.of("1) Create admin",
            "2) Create guest",
            "3) Log in",
            "4) Exit");
    private Integer chosenOption = choices.size();
    private static final Logger log = LoggerFactory.getLogger(DefaultMenu.class);
    private final Repository repository;

    public DefaultMenu(Repository repository){
        this.repository = repository;
    }

    @Override
    public void printMenu() {
        choices.forEach(IO::println);
    }

    @Override
    public void askChoice() {
        while(true){
            try {
                chosenOption = Integer.parseInt(readln("Select option: "));
                if (1 <= chosenOption && chosenOption < choices.size() + 1){
                    break;
                }
            } catch (NumberFormatException e){
                println("Something when wrong try again");
                log.error("A parsing error in default menu", e);
            }
        }
    }

    private Admin createAdmin() {
        String name = readln("Enter admin name: ");
        Integer age;
        while(true){
            try {
                age = Integer.parseInt(readln("Enter age: "));
                break;
            } catch (NumberFormatException _) {
                println("Try again");
            }
        }
        return new Admin(name, age);
    }

    private Guest createGuest() {
        String name = readln("Enter guest name: ");
        Integer age;
        while(true){
            try {
                age = Integer.parseInt(readln("Enter age: "));
                break;
            } catch (NumberFormatException _) {
                println("Try again");
            }
        }
        return new Guest(name, age);
    }

    User selectUser() {
        printList(repository.getUsers());
        Integer idx;
        while(true) {
            try {
                idx = Integer.parseInt(readln("Enter user idx: "));
                if (idx - 1 < repository.getUsers().size() && idx > 0){
                    idx = idx - 1;
                    break;
                }
            } catch (NumberFormatException _) {
                println("Try again");
            }
        }
        return repository.getUsers().get(idx);
    }

    @Override
    public MenuResult doChoice() {
        return switch (chosenOption) {
            case 1 -> new MenuResult.AdminCreated(createAdmin());
            case 2 -> new MenuResult.GuestCreated(createGuest());
            case 3 -> new MenuResult.LogIn(selectUser());
            case 4 -> new MenuResult.ExitProgram();
            default -> new MenuResult.ExitProgram();
        };
    }
}
