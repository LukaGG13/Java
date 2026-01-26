package org.example.java.entity;

import org.example.java.entity.booking.Booking;
import org.example.java.entity.guest.Guest;
import org.example.java.entity.room.Room;
import org.example.java.entity.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Booking Entity Tests")
class BookingTest {

    private Room room;
    private Guest guest;
    private LocalDateTime futureCheckIn;
    private LocalDateTime futureCheckOut;

    @BeforeEach
    void setUp() {
        room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                .sizeInSqrM(25)
                .build();
        guest = new Guest("TestGuest", 30);
        futureCheckIn = LocalDateTime.now().plusDays(1);
        futureCheckOut = LocalDateTime.now().plusDays(3);
    }

    @Nested
    @DisplayName("Valid Booking Creation Tests")
    class ValidBookingTest {

        @Test
        @DisplayName("Should create valid booking with future dates")
        void testCreateValidBooking() {
            Booking booking = new Booking(room, guest, futureCheckIn, futureCheckOut);

            assertEquals(room, booking.room());
            assertEquals(guest, booking.user());
            assertEquals(futureCheckIn, booking.checkIn());
            assertEquals(futureCheckOut, booking.checkOut());
        }

        @Test
        @DisplayName("Should create booking with check-in and check-out on different days")
        void testCreateBookingWithDifferentDays() {
            LocalDateTime checkIn = LocalDateTime.now().plusDays(5);
            LocalDateTime checkOut = LocalDateTime.now().plusDays(10);
            Booking booking = new Booking(room, guest, checkIn, checkOut);

            assertNotNull(booking);
            assertTrue(booking.checkOut().isAfter(booking.checkIn()));
        }

        @Test
        @DisplayName("Should create booking with same day check-in and check-out")
        void testCreateBookingSameDayCheckInOut() {
            LocalDateTime checkIn = LocalDateTime.now().plusHours(2);
            LocalDateTime checkOut = LocalDateTime.now().plusHours(5);
            Booking booking = new Booking(room, guest, checkIn, checkOut);

            assertNotNull(booking);
            assertTrue(booking.checkOut().isAfter(booking.checkIn()));
        }
    }

    @Nested
    @DisplayName("Invalid Booking Creation Tests")
    class InvalidBookingTest {

        @Test
        @DisplayName("Should throw exception when check-in is in the past")
        void testBookingCheckInInPast() {
            LocalDateTime pastCheckIn = LocalDateTime.now().minusDays(1);
            LocalDateTime futureCheckOut = LocalDateTime.now().plusDays(1);

            assertThrows(IllegalArgumentException.class,
                    () -> new Booking(room, guest, pastCheckIn, futureCheckOut),
                    "Should not allow check-in in the past");
        }

        @Test
        @DisplayName("Should throw exception when check-out is before check-in")
        void testBookingCheckOutBeforeCheckIn() {
            LocalDateTime checkIn = LocalDateTime.now().plusDays(5);
            LocalDateTime checkOut = LocalDateTime.now().plusDays(2);

            assertThrows(IllegalArgumentException.class,
                    () -> new Booking(room, guest, checkIn, checkOut),
                    "Check-out cannot be before check-in");
        }

        @Test
        @DisplayName("Should throw exception when check-in and check-out are in the past")
        void testBookingBothDatesInPast() {
            LocalDateTime pastCheckIn = LocalDateTime.now().minusDays(3);
            LocalDateTime pastCheckOut = LocalDateTime.now().minusDays(1);

            assertThrows(IllegalArgumentException.class,
                    () -> new Booking(room, guest, pastCheckIn, pastCheckOut));
        }
    }

    @Nested
    @DisplayName("Booking Null Pointer Tests")
    class BookingNullPointerTest {

        @Test
        @DisplayName("Should throw NullPointerException when room is null")
        void testNullRoom() {
            assertThrows(NullPointerException.class,
                    () -> new Booking(null, guest, futureCheckIn, futureCheckOut));
        }

        @Test
        @DisplayName("Should throw NullPointerException when user is null")
        void testNullUser() {
            assertThrows(NullPointerException.class,
                    () -> new Booking(room, null, futureCheckIn, futureCheckOut));
        }

        @Test
        @DisplayName("Should throw NullPointerException when check-in is null")
        void testNullCheckIn() {
            assertThrows(NullPointerException.class,
                    () -> new Booking(room, guest, null, futureCheckOut));
        }

        @Test
        @DisplayName("Should throw NullPointerException when check-out is null")
        void testNullCheckOut() {
            assertThrows(NullPointerException.class,
                    () -> new Booking(room, guest, futureCheckIn, null));
        }
    }

    @Nested
    @DisplayName("Booking Record Tests")
    class BookingRecordTest {

        @Test
        @DisplayName("Should access booking fields as record components")
        void testRecordComponents() {
            Booking booking = new Booking(room, guest, futureCheckIn, futureCheckOut);

            assertEquals(room, booking.room());
            assertEquals(guest, booking.user());
            assertEquals(futureCheckIn, booking.checkIn());
            assertEquals(futureCheckOut, booking.checkOut());
        }

        @Test
        @DisplayName("Should have correct toString representation")
        void testBookingToString() {
            Booking booking = new Booking(room, guest, futureCheckIn, futureCheckOut);
            String toString = booking.toString();

            assertNotNull(toString);
            assertFalse(toString.isEmpty());
        }
    }

    @Nested
    @DisplayName("Booking with Different Users Tests")
    class BookingDifferentUsersTest {

        @Test
        @DisplayName("Should create booking with admin user")
        void testBookingWithAdmin() {
            User admin = new org.example.java.entity.admin.Admin("AdminName", 50);
            Booking booking = new Booking(room, admin, futureCheckIn, futureCheckOut);

            assertEquals(admin, booking.user());
        }

        @Test
        @DisplayName("Should allow multiple bookings for same room with different users")
        void testMultipleBookingsSameRoom() {
            Guest guest1 = new Guest("Guest1", 25);
            Guest guest2 = new Guest("Guest2", 30);

            Booking booking1 = new Booking(room, guest1, futureCheckIn, futureCheckOut);
            Booking booking2 = new Booking(room, guest2, 
                    futureCheckOut.plusHours(1), 
                    futureCheckOut.plusDays(2));

            assertNotNull(booking1);
            assertNotNull(booking2);
            assertEquals(room, booking1.room());
            assertEquals(room, booking2.room());
        }
    }

    @Nested
    @DisplayName("Booking Date Boundary Tests")
    class BookingDateBoundaryTest {

        @Test
        @DisplayName("Should allow check-in and check-out exactly same time in future")
        void testSameCheckInCheckOut() {
            LocalDateTime time = LocalDateTime.now().plusDays(5);
            assertThrows(IllegalArgumentException.class,
                    () -> new Booking(room, guest, time, time),
                    "Check-out should be after check-in");
        }

        @Test
        @DisplayName("Should allow very far future dates")
        void testFarFutureDates() {
            LocalDateTime checkIn = LocalDateTime.now().plusYears(5);
            LocalDateTime checkOut = LocalDateTime.now().plusYears(5).plusDays(1);

            Booking booking = new Booking(room, guest, checkIn, checkOut);
            assertNotNull(booking);
        }

        @Test
        @DisplayName("Should allow check-in very soon in future")
        void testSoonCheckIn() {
            LocalDateTime checkIn = LocalDateTime.now().plusSeconds(1);
            LocalDateTime checkOut = LocalDateTime.now().plusDays(1);

            Booking booking = new Booking(room, guest, checkIn, checkOut);
            assertNotNull(booking);
        }
    }
}
