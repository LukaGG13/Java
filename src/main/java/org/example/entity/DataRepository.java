package org.example.entity;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public record DataRepository(Optional<User> activeUser, List<Room> rooms, List<User> users, List<Booking> bookings, Map<Room, List<Review>> reviews) implements Repository{

    public DataRepository {
        Objects.requireNonNull(activeUser, "Active user can't be null");
        Objects.requireNonNull(users, "Users can't be null");
        Objects.requireNonNull(rooms, "Rooms can't be null");
        Objects.requireNonNull(bookings, "Bookings can't be null");
        Objects.requireNonNull(reviews, "Reviews can't be null");
    }

    @Override
    public List<Room> getRooms() {
        return rooms;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public List<Booking> getBookings() {
        return bookings;
    }

    @Override
    public Map<Room, List<Review>> getReviews() {
        return reviews;
    }

    @Override
    public Optional<User> getActiveUser() {
        return activeUser;
    }
}