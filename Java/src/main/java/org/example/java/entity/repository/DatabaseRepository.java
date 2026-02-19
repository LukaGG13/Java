package org.example.java.entity.repository;

import org.example.java.database.DatabaseHelper;
import org.example.java.database.DatabaseUtils;
import org.example.java.entity.booking.Booking;
import org.example.java.entity.review.Review;
import org.example.java.entity.room.Room;
import org.example.java.entity.user.User;
import org.example.java.exception.DatabaseException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DatabaseRepository implements Repository {

    private Optional<User> activeUser = Optional.empty();

    public DatabaseRepository() {
        try {
            DatabaseHelper.createTables();
        } catch (Exception e) {
            throw new DatabaseException("Can't create tables", e);
        }
    }

    @Override
    public List<Room> getRooms() {
        try {
            return DatabaseUtils.getAllRooms();
        } catch (IOException _) {
            throw  new RuntimeException();
        }
    }

    @Override
    public List<User> getUsers() {
        try {
            return DatabaseUtils.getAllUsers();
        } catch (IOException _) {
            throw  new RuntimeException();
        }
    }

    @Override
    public List<Booking> getBookings() {
        try {
            return DatabaseUtils.getAllBookings();
        } catch (IOException _) {
            throw new RuntimeException();
        }
    }

    @Override
    public Map<Room, List<Review>> getReviews() {
        //TODO databse igs, za sobe
        return Map.of();
    }

    @Override
    public Optional<User> getActiveUser() {
        return activeUser;
    }

    @Override
    public void setActiveUser(User user) {
       activeUser = Optional.of(user);
    }

    @Override
    public void addUser(User user) {
        try {
            DatabaseUtils.saveNewUser(user);
        } catch (IOException _) {
            throw  new RuntimeException();
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            DatabaseUtils.updateUser(user);
        } catch (IOException _) {
            throw  new RuntimeException();
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            DatabaseUtils.deleteUser(user);
        } catch (IOException _) {
            throw  new RuntimeException();
        }
    }

    @Override
    public void addRoom(Room room) {
        try {
            DatabaseUtils.saveNewRoom(room);
        } catch (IOException _) {
            throw  new RuntimeException();
        }
    }

    @Override
    public void addBooking(Booking booking) {
        try {
            DatabaseUtils.saveNewBooking(booking);
        } catch (IOException _) {
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteBooking(Booking booking) {
        try {
            DatabaseUtils.deleteBooking(booking);
        } catch (IOException _) {
            throw new RuntimeException();
        }
    }

    @Override
    public void updateBooking(Booking booking) {
        try {
            DatabaseUtils.updateBooking(booking);
        } catch (IOException _) {
            throw new RuntimeException();
        }
    }
}
