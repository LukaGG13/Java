package org.example.entity;

import java.time.LocalDateTime;

public interface GuestInterface {
    Booking bookRoom(Room room, LocalDateTime checkIn, LocalDateTime checkOut);
}
