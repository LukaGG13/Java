package org.example.entity;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Record representing a booking.
 * @param room The {@link Room} which is booked.
 * @param user The {@link User} that booked the room.
 * @param checkIn The {@link LocalDateTime} when the checkin will happen.
 * @param checkOut The {@link LocalDateTime} when the checkout will happen.
 * @version 1
 * @author luka
 */
public record Booking(Room room, User user, LocalDateTime checkIn, LocalDateTime checkOut){
    private static final Logger log = LoggerFactory.getLogger(Booking.class);
    /**
     * Constructs a new {@link Booking}.
     * @param room The {@link Room} to be booked.
     * @param user The {@link User} that booked the room.
     * @param checkIn The {@link LocalDateTime} when the checkin will happen.
     * @param checkOut The {@link LocalDateTime} when the checkout will happen.
     * @throws IllegalArgumentException if the booking is in the past of checkout is before the checkin.
     */
    public Booking(Room room, User user, LocalDateTime checkIn, LocalDateTime checkOut) {
        this.room = Objects.requireNonNull(room);
        this.user = Objects.requireNonNull(user);
        this.checkIn = Objects.requireNonNull(checkIn);
        this.checkOut = Objects.requireNonNull(checkOut);
        if (checkIn.isBefore(LocalDateTime.now())) {
            log.warn("Tried to crate booking in the past");
           throw new IllegalArgumentException("Cant checkin in in the past");
        }
        if(checkOut.isBefore(checkIn)) {
            log.warn("Tried to create booking that had checkout before the checkin");
            throw new IllegalArgumentException("Cant checkout before the checkin");
        }
        log.info("Created new booking");
    }
}
