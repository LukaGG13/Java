package org.example.java.entity.repository;

import org.example.java.entity.booking.Booking;
import org.example.java.entity.review.Review;
import org.example.java.entity.room.Room;
import org.example.java.entity.user.User;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Clase used to give data to other classes.
 * @param activeUser {@link Optional} of {@link User}'s that should show the active user.
 * @param rooms {@link List} of {@link Room}'s that can be accessed with {@link #getRooms()}.
 * @param users {@link List} of {@link User}'s that can be accessed with {@link #getUsers()}.
 * @param bookings {@link List} of {@link Booking}'s that can be accessed with {@link #getBookings()}.
 * @param reviews {@link List} of {@link Review}'s that can be accessed with {@link #getReviews()}.
 */
public record DataRepository(Optional<User> activeUser, List<Room> rooms, List<User> users, List<Booking> bookings, Map<Room, List<Review>> reviews) implements Repository {

    private static final Logger log = LoggerFactory.getLogger(DataRepository.class);

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

    @Override
    public void addUser(User user) {
        log.debug("Adding user {}", user);
        users.add(user);
        log.debug("User list is now {}", users);
    }
}