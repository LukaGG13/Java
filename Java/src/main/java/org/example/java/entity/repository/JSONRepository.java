package org.example.java.entity.repository;

import org.example.java.entity.booking.Booking;
import org.example.java.entity.review.Review;
import org.example.java.entity.room.Room;
import org.example.java.entity.user.User;
import org.example.java.files.JSONUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JSONRepository implements Repository {

    private static final String ROOMS_FILE_PATH = "src/main/resources/rooms.json";
    private static final String USERS_FILE_PATH = "src/main/resources/users.json";

    private static final JSONUtils<Room> roomJsonUtils = new JSONUtils<>(ROOMS_FILE_PATH);
    private static final JSONUtils<User> userJsonUtils = new JSONUtils<>(USERS_FILE_PATH);

    @Override
    public List<Room> getRooms() {
        return roomJsonUtils.get(_ -> true);
        //return List.of();
    }

    @Override
    public List<User> getUsers() {
        return userJsonUtils.get(_ -> true);
        //return List.of();
    }

    @Override
    public List<Booking> getBookings() {
        return List.of();
    }

    @Override
    public Map<Room, List<Review>> getReviews() {
        return Map.of();
    }

    @Override
    public Optional<User> getActiveUser() {
        return Optional.empty();
    }

    @Override
    public void setActiveUser(User user) {

    }

    @Override
    public void addUser(User user) {
        userJsonUtils.save(user);
    }

    @Override
    public void updateUser(User user) {
        var users = userJsonUtils.get(oldUser -> oldUser.getId() != user.getId());
        users.add(user);
        userJsonUtils.deleteAll();
        userJsonUtils.saveAll(users);
    }

    @Override
    public void deleteUser(User user) {
        var users = userJsonUtils.get(oldUser -> oldUser.getId() != user.getId());
        userJsonUtils.deleteAll();
        userJsonUtils.saveAll(users);
    }

    @Override
    public void addRoom(Room room) {
        roomJsonUtils.save(room);
    }

    @Override
    public void addBooking(Booking booking) {

    }

    @Override
    public void deleteBooking(Booking booking) {

    }

    @Override
    public void updateBooking(Booking booking) {

    }
}
