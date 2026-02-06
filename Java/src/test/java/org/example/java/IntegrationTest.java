package org.example.java;

import org.example.java.entity.admin.Admin;
import org.example.java.entity.booking.Booking;
import org.example.java.entity.guest.Guest;
import org.example.java.entity.review.Review;
import org.example.java.entity.room.Room;
import org.example.java.entity.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Integration Tests")
class IntegrationTest {

    /*
    @Nested
    @DisplayName("Hotel Management Workflow Tests")
    class HotelManagementWorkflow {

        @Test
        @DisplayName("Complete hotel booking workflow")
        void testCompleteBookingWorkflow() {
            // Admin creates room
            Admin admin = new Admin("Manager", 45);
            Room room = admin.createRoom(2, BigDecimal.valueOf(100));

            // Guest books room
            Guest guest = new Guest("John Doe", 30);
            LocalDateTime checkIn = LocalDateTime.now().plusDays(1);
            LocalDateTime checkOut = LocalDateTime.now().plusDays(3);
            Booking booking = new Booking(room, guest, checkIn, checkOut);

            // Guest leaves review
            Review review = new Review(guest, "Excellent stay!", LocalDate.now(), 9);

            // Verify all steps
            assertNotNull(booking);
            assertEquals(room, booking.room());
            assertEquals(guest, booking.user());
            assertEquals(review.guest(), guest);
            assertEquals(9, review.rating());
        }

        @Test
        @DisplayName("Multiple guests booking same room at different times")
        void testMultipleGuestsBookingSameRoom() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .sizeInSqrM(25)
                    .build();

            Guest guest1 = new Guest("Guest1", 25);
            Guest guest2 = new Guest("Guest2", 35);

            LocalDateTime checkIn1 = LocalDateTime.now().plusDays(1);
            LocalDateTime checkOut1 = LocalDateTime.now().plusDays(3);
            Booking booking1 = new Booking(room, guest1, checkIn1, checkOut1);

            LocalDateTime checkIn2 = LocalDateTime.now().plusDays(5);
            LocalDateTime checkOut2 = LocalDateTime.now().plusDays(7);
            Booking booking2 = new Booking(room, guest2, checkIn2, checkOut2);

            assertEquals(room, booking1.room());
            assertEquals(room, booking2.room());
            assertNotEquals(booking1.user(), booking2.user());
        }

        @Test
        @DisplayName("Admin manages multiple rooms with amenities")
        void testAdminMultipleRoomsWithAmenities() {
            Admin admin = new Admin("Manager", 50);

            Room room1 = new Room.RoomBuilder(1, BigDecimal.valueOf(50))
                    .sizeInSqrM(15)
                    .addAmenity(Room.Amenity.WIFI)
                    .build();

            Room room2 = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .sizeInSqrM(30)
                    .addAmenity(Room.Amenity.WIFI)
                    .addAmenity(Room.Amenity.POOL)
                    .build();

            Room room3 = new Room.RoomBuilder(3, BigDecimal.valueOf(150))
                    .sizeInSqrM(45)
                    .addAmenity(Room.Amenity.GYM)
                    .addAmenity(Room.Amenity.SPA)
                    .addAmenity(Room.Amenity.BREAKFAST)
                    .build();

            List<Room> rooms = new ArrayList<>();
            rooms.add(room1);
            rooms.add(room2);
            rooms.add(room3);

            assertEquals(3, rooms.size());
            assertTrue(room3.getAmenities().contains(Room.Amenity.SPA));
        }
    }

     */

    @Nested
    @DisplayName("Review Management Tests")
    class ReviewManagement {

        @Test
        @DisplayName("Guest leaves multiple reviews for different rooms")
        void testGuestMultipleReviews() {
            Guest guest = new Guest("ReviewGuest", 40);

            Review review1 = new Review(guest, "Great experience", LocalDate.now().minusDays(2), 8);
            Review review2 = new Review(guest, "Amazing stay", LocalDate.now().minusDays(1), 9);
            Review review3 = new Review(guest, "Good value", LocalDate.now(), 7);

            List<Review> reviews = new ArrayList<>();
            reviews.add(review1);
            reviews.add(review2);
            reviews.add(review3);

            assertEquals(3, reviews.size());
            assertTrue(reviews.stream().allMatch(r -> r.guest().equals(guest)));
            assertTrue(reviews.stream().allMatch(r -> r.rating() >= 7));
        }

        @Test
        @DisplayName("Multiple guests review same room")
        void testMultipleGuestsReviewRoom() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100)).build();

            Guest guest1 = new Guest("Guest1", 25);
            Guest guest2 = new Guest("Guest2", 35);
            Guest guest3 = new Guest("Guest3", 45);

            Review review1 = new Review(guest1, "Good", LocalDate.now().minusDays(2), 8);
            Review review2 = new Review(guest2, "Great", LocalDate.now().minusDays(1), 9);
            Review review3 = new Review(guest3, "Excellent", LocalDate.now(), 10);

            List<Review> roomReviews = new ArrayList<>();
            roomReviews.add(review1);
            roomReviews.add(review2);
            roomReviews.add(review3);

            assertEquals(3, roomReviews.size());
            double averageRating = roomReviews.stream()
                    .mapToInt(Review::rating)
                    .average()
                    .orElse(0);
            assertEquals(9.0, averageRating);
        }

        @Test
        @DisplayName("Invalid review ratings validation in workflow")
        void testReviewRatingValidation() {
            Guest guest = new Guest("Guest", 30);

            assertThrows(IllegalArgumentException.class,
                    () -> new Review(guest, "Bad", LocalDate.now(), 0));
            assertThrows(IllegalArgumentException.class,
                    () -> new Review(guest, "Bad", LocalDate.now(), 11));

            Review validReview = new Review(guest, "Good", LocalDate.now(), 5);
            assertNotNull(validReview);
        }
    }

    @Nested
    @DisplayName("User Management Tests")
    class UserManagement {

        @Test
        @DisplayName("Admin and Guest coexistence")
        void testAdminGuestCoexistence() {
            Admin admin = new Admin("Manager", 50);
            Guest guest = new Guest("Customer", 30);

            List<User> users = new ArrayList<>();
            users.add(admin);
            users.add(guest);

            assertEquals(2, users.size());
            assertTrue(users.stream().anyMatch(u -> u instanceof Admin));
            assertTrue(users.stream().anyMatch(u -> u instanceof Guest));
        }

        @Test
        @DisplayName("User searchability across types")
        void testUserSearchability() {
            Admin admin = new Admin("AdminName", 50);
            Guest guest = new Guest("GuestName", 30);

            var adminKeywords = admin.getKeyWord();
            var guestKeywords = guest.getKeyWord();

            assertTrue(adminKeywords.stream().anyMatch(p -> p.getValue().equals("AdminName")));
            assertTrue(guestKeywords.stream().anyMatch(p -> p.getValue().equals("GuestName")));
        }
    }

    @Nested
    @DisplayName("Room Amenities Workflow")
    class RoomAmenitiesWorkflow {

        @Test
        @DisplayName("Create luxury room with all amenities")
        void testLuxuryRoomCreation() {
            Room luxuryRoom = new Room.RoomBuilder(4, BigDecimal.valueOf(500))
                    .sizeInSqrM(80)
                    .distanceFromCityCenter(BigDecimal.valueOf(2))
                    .distanceFromBeach(BigDecimal.valueOf(0.5))
                    .addAmenity(Room.Amenity.GYM)
                    .addAmenity(Room.Amenity.WIFI)
                    .addAmenity(Room.Amenity.POOL)
                    .addAmenity(Room.Amenity.PARKING)
                    .addAmenity(Room.Amenity.SPA)
                    .addAmenity(Room.Amenity.BREAKFAST)
                    .build();

            assertEquals(4, luxuryRoom.getNumOfBeds());
            assertEquals(80, luxuryRoom.getSizeInSqrM());
            assertEquals(6, luxuryRoom.getAmenities().size());
            assertEquals(BigDecimal.valueOf(500), luxuryRoom.getPricePerNight());
        }

        @Test
        @DisplayName("Create budget room with minimal amenities")
        void testBudgetRoomCreation() {
            Room budgetRoom = new Room.RoomBuilder(1, BigDecimal.valueOf(30))
                    .sizeInSqrM(10)
                    .build();

            assertEquals(1, budgetRoom.getNumOfBeds());
            assertEquals(10, budgetRoom.getSizeInSqrM());
            assertTrue(budgetRoom.getAmenities().isEmpty());
            assertEquals(BigDecimal.valueOf(30), budgetRoom.getPricePerNight());
        }

        @Test
        @DisplayName("Compare rooms by amenities")
        void testRoomComparison() {
            Room basicRoom = new Room.RoomBuilder(2, BigDecimal.valueOf(80))
                    .addAmenity(Room.Amenity.WIFI)
                    .build();

            Room luxuryRoom = new Room.RoomBuilder(2, BigDecimal.valueOf(120))
                    .addAmenity(Room.Amenity.WIFI)
                    .addAmenity(Room.Amenity.POOL)
                    .addAmenity(Room.Amenity.SPA)
                    .build();

            assertTrue(basicRoom.getAmenities().size() < luxuryRoom.getAmenities().size());
            assertTrue(basicRoom.getPricePerNight().compareTo(luxuryRoom.getPricePerNight()) < 0);
        }
    }

    @Nested
    @DisplayName("Booking Scenarios")
    class BookingScenarios {

        @Test
        @DisplayName("Sequential bookings across multiple days")
        void testSequentialBookings() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100)).build();
            Guest guest1 = new Guest("Guest1", 25);
            Guest guest2 = new Guest("Guest2", 35);

            LocalDateTime day1Start = LocalDateTime.of(2026, 2, 1, 14, 0);
            LocalDateTime day1End = LocalDateTime.of(2026, 2, 2, 11, 0);
            Booking booking1 = new Booking(room, guest1, day1Start, day1End);

            LocalDateTime day2Start = LocalDateTime.of(2026, 2, 2, 14, 0);
            LocalDateTime day2End = LocalDateTime.of(2026, 2, 3, 11, 0);
            Booking booking2 = new Booking(room, guest2, day2Start, day2End);

            assertEquals(room, booking1.room());
            assertEquals(room, booking2.room());
            assertTrue(booking1.checkOut().isBefore(booking2.checkIn()));
        }

        @Test
        @DisplayName("Long-term booking")
        void testLongTermBooking() {
            Room room = new Room.RoomBuilder(1, BigDecimal.valueOf(50)).build();
            Guest guest = new Guest("LongTermGuest", 60);

            LocalDateTime checkIn = LocalDateTime.now().plusDays(1);
            LocalDateTime checkOut = LocalDateTime.now().plusDays(30);
            Booking booking = new Booking(room, guest, checkIn, checkOut);

            assertNotNull(booking);
            assertTrue(booking.checkOut().isAfter(booking.checkIn().plusDays(25)));
        }
    }

    @Nested
    @DisplayName("Data Consistency Tests")
    class DataConsistency {

        @Test
        @DisplayName("Room data persists across operations")
        void testRoomDataPersistence() {
            Room room = new Room.RoomBuilder(3, BigDecimal.valueOf(200))
                    .sizeInSqrM(50)
                    .distanceFromCityCenter(BigDecimal.valueOf(5))
                    .distanceFromBeach(BigDecimal.valueOf(2))
                    .addAmenity(Room.Amenity.POOL)
                    .build();

            // Use room multiple times
            Guest guest1 = new Guest("G1", 30);
            Guest guest2 = new Guest("G2", 40);

            Booking b1 = new Booking(room, guest1, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3));
            Booking b2 = new Booking(room, guest2, LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(7));

            // Verify room data unchanged
            assertEquals(3, room.getNumOfBeds());
            assertEquals(50, room.getSizeInSqrM());
            assertEquals(1, room.getAmenities().size());
        }

        @Test
        @DisplayName("User data consistency across bookings")
        void testUserDataConsistency() {
            Guest guest = new Guest("TestGuest", 35);
            Room room1 = new Room.RoomBuilder(2, BigDecimal.valueOf(100)).build();
            Room room2 = new Room.RoomBuilder(3, BigDecimal.valueOf(150)).build();

            Booking booking1 = new Booking(room1, guest, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3));
            Booking booking2 = new Booking(room2, guest, LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(7));

            assertEquals(booking1.user(), booking2.user());
            assertEquals("TestGuest", booking1.user().getName());
            assertEquals("TestGuest", booking2.user().getName());
        }
    }
}
