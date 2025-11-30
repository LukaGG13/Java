package org.example.entity.repository;

import org.example.entity.booking.Booking;
import org.example.entity.review.Review;
import org.example.entity.room.Room;
import org.example.entity.user.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Repository {
    List<Room> getRooms();
    List<User> getUsers();
    List<Booking> getBookings();
    Map<Room, List<Review>> getReviews();
    Optional<User> getActiveUser();
}
