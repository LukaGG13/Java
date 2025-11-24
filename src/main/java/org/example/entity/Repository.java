package org.example.entity;

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
