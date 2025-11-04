package org.example.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Class representing a guest.
 * @version 1
 * @author luka
 */
public final class Guest extends User implements GuestInterface{
    private static final Logger log = LoggerFactory.getLogger(Guest.class);
    /**
     * Constructs a new {@link Guest}.
     * @param name The name of the {@link Guest}, as {@link String}.
     * @param age The age of the {@link Guest}, as {@link Integer}.
     */
    public Guest(String name, Integer age) {
       super(name, age);
       log.info("Crated guest whit name:\"{}\" and age {}", name, age);
   }

    @Override
    public Booking bookRoom(Room room, LocalDateTime checkIn, LocalDateTime checkOut) {
        return new Booking(room, this, checkIn, checkOut);
    }
}