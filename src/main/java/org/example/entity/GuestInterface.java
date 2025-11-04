package org.example.entity;

import java.time.LocalDateTime;

/**
 * Interface for guests.
 * @version 1
 * @author luka
 */
public interface GuestInterface {
    /**
     * Creates a new {@link Booking}
     * @param room {@link Room} the be booked.
     * @param checkIn {@link LocalDateTime} the time the check in will happen shouldn't be in the past.
     * @param checkOut {@link LocalDateTime} the time the check in will happen shouldn't be before on or the same day as the checkin.
     * @return {@link Booking}
     */
    Booking bookRoom(Room room, LocalDateTime checkIn, LocalDateTime checkOut);
}
