package org.example.entity.guest;

import org.example.entity.booking.Booking;
import org.example.entity.review.Review;
import org.example.entity.room.Room;
import org.example.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.AbstractMap;

/**
 * Class representing a guest.
 * @version 1
 * @author luka
 */
public final class Guest extends User {
    private static final Logger log = LoggerFactory.getLogger(Guest.class);

    /**
     * Constructs a new {@link Guest}.
     * @param name The name of the {@link Guest}, as {@link String}.
     * @param age The age of the {@link Guest}, as {@link Integer}.
     */
    public Guest(String name, Integer age) {
        log.info("Crated guest whit name:\"{}\" and age {}", name, age);
        super(name, age);
   }

    public Booking bookRoom(GuestInputService inputService) {
        Room roomToBeBooked = inputService.askRoom("Select room to book: ");
        Guest guest = this;
        LocalDateTime checkIn = inputService.askLocalDateTime("Select checkIn (dd.MM.yyyy HH:mm:ss): ");
        LocalDateTime checkOut = inputService.askLocalDateTime("Select checkOut (dd.MM.yyyy HH:mm:ss): ", checkIn, LocalDateTime.MAX);
        return new Booking(roomToBeBooked, guest, checkIn, checkOut);
    }

    public AbstractMap.SimpleEntry<Room, Review> leaveReview(GuestInputService inputService) {
        Room roomToBeReview = inputService.askRoom("Select room to review: ");

        Guest guest = this;
        String msg = inputService.askString("Leave your review: ");
        LocalDate dateOfReview = LocalDate.now();
        Integer rating = inputService.askInteger("Leave your rating 1 to 5", 1, 6);

        Review review = new Review(guest, msg, dateOfReview, rating);

        return new AbstractMap.SimpleEntry<>(roomToBeReview, review);
    }

}