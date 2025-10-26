package entity;

import java.time.LocalDateTime;

public record Booking(Room room, User user, LocalDateTime checkIn, LocalDateTime checkOut){ }
