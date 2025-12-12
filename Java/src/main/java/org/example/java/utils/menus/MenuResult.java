package org.example.java.utils.menus;

import org.example.java.entity.admin.Admin;
import org.example.java.entity.booking.Booking;
import org.example.java.entity.guest.Guest;
import org.example.java.entity.review.Review;
import org.example.java.entity.room.Room;
import org.example.java.entity.user.User;

import java.util.AbstractMap;

public interface MenuResult {
    record LoggedOut() implements MenuResult{}
    record RoomCreated(Room room) implements MenuResult{}
    record BookingCreated(Booking booking) implements  MenuResult{}
    record ReviewCreated(AbstractMap.SimpleEntry<Room, Review> roomReviewSimpleEntry) implements MenuResult{}
    record AdminCreated(Admin admin) implements MenuResult {}
    record GuestCreated(Guest guest) implements MenuResult {}
    record ExitProgram() implements MenuResult {}
    record LogIn(User user) implements MenuResult{}
    record Error(String msg) implements MenuResult{}
}
