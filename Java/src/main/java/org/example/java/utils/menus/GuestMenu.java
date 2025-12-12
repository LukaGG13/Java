package org.example.java.utils.menus;

import org.example.java.entity.booking.Booking;
import org.example.java.entity.guest.Guest;
import org.example.java.entity.guest.GuestInputServiceConsole;
import org.example.java.entity.repository.Repository;
import org.example.java.entity.review.Review;
import org.example.java.entity.room.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap;
import java.util.List;

import static java.lang.IO.println;
import static java.lang.IO.readln;

public class GuestMenu implements MenuInterface {
    private final List<String> choices = List.of("1) Book room",
            "2) Leave a review",
            "3) Log out");
    private Integer chosenOption = choices.size();
    private static final Logger log = LoggerFactory.getLogger(GuestMenu.class);
    private final Repository repository;

    public GuestMenu(Repository repository) {
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
                log.error("A parsing error in Guest menu", e);
            }
        }
    }

    Booking bookRoom() {
        if (repository.getActiveUser().orElseThrow() instanceof Guest guest) {
            try {
                return guest.bookRoom(new GuestInputServiceConsole(repository));
            } catch (IllegalStateException e){
                log.error("Error while booking rooms", e);
                println(e.getMessage());
            }
        }
        throw new IllegalStateException("Trying to book room while a non guest users is active.");
    }

    AbstractMap.SimpleEntry<Room, Review> leaveReview() {
        if (repository.getActiveUser().orElseThrow() instanceof Guest guest) {
            try {
                return guest.leaveReview(new GuestInputServiceConsole(repository));
            } catch (IllegalStateException e) {
               log.error("Error while leaving review", e);
               println(e.getMessage());
            }
        }
        throw new IllegalStateException("Trying to leave review while a non guest users is active.");
    }

    @Override
    public MenuResult doChoice() {
        try {
            return switch (chosenOption) {
                case 1 -> new MenuResult.BookingCreated(bookRoom());
                case 2 -> new MenuResult.ReviewCreated(leaveReview());
                case 3 -> new MenuResult.LoggedOut();
                default -> new MenuResult.LoggedOut();
            };
        } catch (IllegalStateException e) {
            log.error("Error in guestMenu", e);
            return new MenuResult.Error("Something is wrong");
        }
    }
}
