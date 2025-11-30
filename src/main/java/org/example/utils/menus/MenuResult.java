package org.example.utils.menus;

import org.example.entity.admin.Admin;
import org.example.entity.booking.Booking;
import org.example.entity.guest.Guest;
import org.example.entity.review.Review;
import org.example.entity.room.Room;
import org.example.entity.user.User;

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
