package org.example.java.entity.repository;

import org.example.java.entity.booking.Booking;
import org.example.java.entity.review.Review;
import org.example.java.entity.room.Room;
import org.example.java.entity.user.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Repository {
    List<Room> getRooms();
    List<User> getUsers();
    List<Booking> getBookings();
    Map<Room, List<Review>> getReviews();
    Optional<User> getActiveUser();
    void addUser(User user);
}
