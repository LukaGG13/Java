package org.example.java.entity;

import javafx.util.Pair;
import org.example.java.entity.admin.Admin;
import org.example.java.entity.booking.Booking;
import org.example.java.entity.guest.Guest;
import org.example.java.entity.interfaces.Searchable;
import org.example.java.entity.review.Review;
import org.example.java.entity.room.Room;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Searchable Interface Tests")
class SearchableTest {

    @Nested
    @DisplayName("Guest Searchability Tests")
    class GuestSearchabilityTest {

        @Test
        @DisplayName("Guest should implement Searchable")
        void testGuestImplementsSearchable() {
            Guest guest = new Guest("SearchGuest", 30);
            assertTrue(guest instanceof Searchable);
        }

        @Test
        @DisplayName("Guest should return keywords")
        void testGuestKeywords() {
            Guest guest = new Guest("Alice", 25);
            Set<Pair<String, String>> keywords = guest.getKeyWord();

            assertNotNull(keywords);
            assertFalse(keywords.isEmpty());
        }

        @Test
        @DisplayName("Guest keywords should contain Class")
        void testGuestKeywordsContainClass() {
            Guest guest = new Guest("Bob", 30);
            Set<Pair<String, String>> keywords = guest.getKeyWord();

            assertTrue(keywords.stream()
                    .anyMatch(p -> p.getKey().equals("Class") && p.getValue().equals("User")));
        }

        @Test
        @DisplayName("Guest keywords should contain Name")
        void testGuestKeywordsContainName() {
            Guest guest = new Guest("TestName", 30);
            Set<Pair<String, String>> keywords = guest.getKeyWord();

            assertTrue(keywords.stream()
                    .anyMatch(p -> p.getKey().equals("Name") && p.getValue().equals("TestName")));
        }

        @Test
        @DisplayName("Guest keywords should contain Age")
        void testGuestKeywordsContainAge() {
            Guest guest = new Guest("TestGuest", 42);
            Set<Pair<String, String>> keywords = guest.getKeyWord();

            assertTrue(keywords.stream()
                    .anyMatch(p -> p.getKey().equals("Age") && p.getValue().equals("42")));
        }
    }

    @Nested
    @DisplayName("Admin Searchability Tests")
    class AdminSearchabilityTest {

        @Test
        @DisplayName("Admin should implement Searchable")
        void testAdminImplementsSearchable() {
            Admin admin = new Admin("AdminUser", 40);
            assertTrue(admin instanceof Searchable);
        }

        @Test
        @DisplayName("Admin should return keywords")
        void testAdminKeywords() {
            Admin admin = new Admin("Manager", 45);
            Set<Pair<String, String>> keywords = admin.getKeyWord();

            assertNotNull(keywords);
            assertFalse(keywords.isEmpty());
        }

        @Test
        @DisplayName("Admin keywords should contain Admin info")
        void testAdminKeywordsContent() {
            Admin admin = new Admin("TestAdmin", 50);
            Set<Pair<String, String>> keywords = admin.getKeyWord();

            assertTrue(keywords.stream()
                    .anyMatch(p -> p.getKey().equals("Name") && p.getValue().equals("TestAdmin")));
            assertTrue(keywords.stream()
                    .anyMatch(p -> p.getKey().equals("Age") && p.getValue().equals("50")));
        }
    }

    @Nested
    @DisplayName("Room Searchability Tests")
    class RoomSearchabilityTest {

        @Test
        @DisplayName("Room should implement Searchable")
        void testRoomImplementsSearchable() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100)).build();
            assertTrue(room instanceof Searchable);
        }

        @Test
        @DisplayName("Room should return keywords")
        void testRoomKeywords() {
            Room room = new Room.RoomBuilder(3, BigDecimal.valueOf(150)).build();
            Set<Pair<String, String>> keywords = room.getKeyWord();

            assertNotNull(keywords);
            assertFalse(keywords.isEmpty());
        }

        @Test
        @DisplayName("Room keywords should contain Class")
        void testRoomKeywordsContainClass() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100)).build();
            Set<Pair<String, String>> keywords = room.getKeyWord();

            assertTrue(keywords.stream()
                    .anyMatch(p -> p.getKey().equals("Class") && p.getValue().equals("Room")));
        }

        @Test
        @DisplayName("Room keywords should contain field values")
        void testRoomKeywordsContainFields() {
            Room room = new Room.RoomBuilder(4, BigDecimal.valueOf(200))
                    .sizeInSqrM(60)
                    .build();
            Set<Pair<String, String>> keywords = room.getKeyWord();

            assertTrue(keywords.stream().anyMatch(p -> p.getKey().equals("numOfBeds")));
            assertTrue(keywords.stream().anyMatch(p -> p.getKey().equals("sizeInSqrM")));
            assertTrue(keywords.stream().anyMatch(p -> p.getKey().equals("pricePerNight")));
        }

        @Test
        @DisplayName("Room keywords should contain amenities")
        void testRoomKeywordsContainAmenities() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .addAmenity(Room.Amenity.WIFI)
                    .addAmenity(Room.Amenity.POOL)
                    .build();
            Set<Pair<String, String>> keywords = room.getKeyWord();

            assertTrue(keywords.stream()
                    .anyMatch(p -> p.getKey().equals("WIFI") && p.getValue().equals("WIFI")));
            assertTrue(keywords.stream()
                    .anyMatch(p -> p.getKey().equals("POOL") && p.getValue().equals("POOL")));
        }

        @Test
        @DisplayName("Room with no amenities should not have amenity keywords")
        void testRoomNoAmenities() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100)).build();
            Set<Pair<String, String>> keywords = room.getKeyWord();

            assertTrue(keywords.stream()
                    .noneMatch(p -> p.getKey().equals("WIFI")));
        }
    }

    @Nested
    @DisplayName("Integration Searchability Tests")
    class IntegrationSearchabilityTest {

        @Test
        @DisplayName("Different entities should have different keyword sets")
        void testDifferentEntitiesKeywords() {
            Guest guest = new Guest("Guest", 30);
            Admin admin = new Admin("Admin", 40);
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100)).build();

            Set<Pair<String, String>> guestKeywords = guest.getKeyWord();
            Set<Pair<String, String>> adminKeywords = admin.getKeyWord();
            Set<Pair<String, String>> roomKeywords = room.getKeyWord();

            assertNotNull(guestKeywords);
            assertNotNull(adminKeywords);
            assertNotNull(roomKeywords);
        }

        @Test
        @DisplayName("Searchable entities should have consistent keyword format")
        void testKeywordFormat() {
            Guest guest = new Guest("Test", 25);
            Set<Pair<String, String>> keywords = guest.getKeyWord();

            keywords.forEach(pair -> {
                assertNotNull(pair.getKey());
                assertNotNull(pair.getValue());
                assertFalse(pair.getKey().isEmpty());
                assertFalse(pair.getValue().isEmpty());
            });
        }

        @Test
        @DisplayName("Keywords should be searchable by key-value")
        void testKeywordSearching() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .sizeInSqrM(30)
                    .addAmenity(Room.Amenity.WIFI)
                    .build();
            Set<Pair<String, String>> keywords = room.getKeyWord();

            // Search for room with 2 beds
            boolean foundBeds = keywords.stream()
                    .anyMatch(p -> "numOfBeds".equals(p.getKey()) && "2".equals(p.getValue()));

            assertTrue(foundBeds);
        }
    }

    @Nested
    @DisplayName("Edge Case Searchability Tests")
    class EdgeCaseSearchabilityTest {

        @Test
        @DisplayName("Guest with special characters in name")
        void testGuestSpecialCharacterName() {
            Guest guest = new Guest("John-Paul O'Brien", 30);
            Set<Pair<String, String>> keywords = guest.getKeyWord();

            assertTrue(keywords.stream()
                    .anyMatch(p -> p.getValue().contains("John")));
        }

        @Test
        @DisplayName("Guest with very old age")
        void testGuestOldAge() {
            Guest guest = new Guest("Elderly", 120);
            Set<Pair<String, String>> keywords = guest.getKeyWord();

            assertTrue(keywords.stream()
                    .anyMatch(p -> p.getKey().equals("Age") && p.getValue().equals("120")));
        }

        @Test
        @DisplayName("Room with high price")
        void testRoomHighPrice() {
            Room room = new Room.RoomBuilder(5, BigDecimal.valueOf(9999.99)).build();
            Set<Pair<String, String>> keywords = room.getKeyWord();

            assertTrue(keywords.stream()
                    .anyMatch(p -> p.getKey().equals("pricePerNight")));
        }

        @Test
        @DisplayName("Room with all amenities")
        void testRoomAllAmenities() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .addAmenity(Room.Amenity.GYM)
                    .addAmenity(Room.Amenity.WIFI)
                    .addAmenity(Room.Amenity.POOL)
                    .addAmenity(Room.Amenity.PARKING)
                    .addAmenity(Room.Amenity.SPA)
                    .addAmenity(Room.Amenity.BREAKFAST)
                    .build();
            Set<Pair<String, String>> keywords = room.getKeyWord();

            assertEquals(6, room.getAmenities().size());
            // Keywords should include class + fields + 6 amenities
            assertTrue(keywords.size() >= 12);
        }
    }
}
