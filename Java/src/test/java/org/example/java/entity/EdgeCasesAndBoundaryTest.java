package org.example.java.entity;

import javafx.util.Pair;
import org.example.java.entity.guest.Guest;
import org.example.java.entity.interfaces.Searchable;
import org.example.java.entity.room.Room;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Edge Cases and Boundary Tests")
class EdgeCasesAndBoundaryTest {

    @Nested
    @DisplayName("Extreme Values Tests")
    class ExtremeValuesTest {

        @Test
        @DisplayName("Room with very high price")
        void testRoomVeryHighPrice() {
            BigDecimal veryHighPrice = BigDecimal.valueOf(999999.99);
            Room room = new Room.RoomBuilder(2, veryHighPrice).build();

            assertEquals(veryHighPrice, room.getPricePerNight());
        }

        @Test
        @DisplayName("Room with very low price")
        void testRoomVeryLowPrice() {
            BigDecimal veryLowPrice = BigDecimal.valueOf(0.01);
            Room room = new Room.RoomBuilder(1, veryLowPrice).build();

            assertEquals(veryLowPrice, room.getPricePerNight());
        }

        @Test
        @DisplayName("Room with zero price")
        void testRoomZeroPrice() {
            BigDecimal zeroPrice = BigDecimal.ZERO;
            Room room = new Room.RoomBuilder(1, zeroPrice).build();

            assertEquals(zeroPrice, room.getPricePerNight());
        }

        @Test
        @DisplayName("Room with maximum beds")
        void testRoomMaximumBeds() {
            Room room = new Room.RoomBuilder(100, BigDecimal.valueOf(5000)).build();

            assertEquals(100, room.getNumOfBeds());
        }

        @Test
        @DisplayName("Room with one bed")
        void testRoomOneBed() {
            Room room = new Room.RoomBuilder(1, BigDecimal.valueOf(50)).build();

            assertEquals(1, room.getNumOfBeds());
        }

        @Test
        @DisplayName("Guest with age 0")
        void testGuestAgeZero() {
            Guest guest = new Guest("Baby", 0);

            assertEquals(0, guest.getAge());
        }

        @Test
        @DisplayName("Guest with very high age")
        void testGuestVeryHighAge() {
            Guest guest = new Guest("Ancient", 150);

            assertEquals(150, guest.getAge());
        }

        @Test
        @DisplayName("User with very long name")
        void testUserVeryLongName() {
            String longName = "a".repeat(1000);
            Guest guest = new Guest(longName, 30);

            assertEquals(longName, guest.getName());
            assertEquals(1000, guest.getName().length());
        }

        @Test
        @DisplayName("User with single character name")
        void testUserSingleCharName() {
            Guest guest = new Guest("A", 25);

            assertEquals("A", guest.getName());
            assertEquals(1, guest.getName().length());
        }

        @Test
        @DisplayName("Room with very large size")
        void testRoomVeryLargeSize() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .sizeInSqrM(10000)
                    .build();

            assertEquals(10000, room.getSizeInSqrM());
        }

        @Test
        @DisplayName("Room with very small distance")
        void testRoomVerySmallDistance() {
            BigDecimal verySmall = BigDecimal.valueOf(0.001);
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .distanceFromBeach(verySmall)
                    .build();

            assertEquals(verySmall, room.getDistanceFromBeach());
        }
    }

    @Nested
    @DisplayName("Special Characters Tests")
    class SpecialCharactersTest {

        @Test
        @DisplayName("User with special characters in name")
        void testUserSpecialCharacters() {
            String specialName = "José María García-López";
            Guest guest = new Guest(specialName, 30);

            assertEquals(specialName, guest.getName());
        }

        @Test
        @DisplayName("User with unicode characters")
        void testUserUnicodeCharacters() {
            String unicodeName = "李明 (Li Ming)";
            Guest guest = new Guest(unicodeName, 30);

            assertEquals(unicodeName, guest.getName());
        }

        @Test
        @DisplayName("User with numbers in name")
        void testUserNumbersInName() {
            Guest guest = new Guest("Agent007Bond", 40);

            assertEquals("Agent007Bond", guest.getName());
        }

        @Test
        @DisplayName("User with symbols in name")
        void testUserSymbolsInName() {
            Guest guest = new Guest("O'Brien-Smith", 35);

            assertEquals("O'Brien-Smith", guest.getName());
        }

        @Test
        @DisplayName("User with whitespace in name")
        void testUserWhitespaceName() {
            Guest guest = new Guest("Mary   Jane", 28);

            assertEquals("Mary   Jane", guest.getName());
        }
    }

    @Nested
    @DisplayName("Decimal Precision Tests")
    class DecimalPrecisionTest {

        @Test
        @DisplayName("Room price with many decimal places")
        void testRoomPriceDecimalPlaces() {
            BigDecimal price = BigDecimal.valueOf(123.456789);
            Room room = new Room.RoomBuilder(2, price).build();

            assertEquals(price, room.getPricePerNight());
        }

        @Test
        @DisplayName("Room distance with decimal precision")
        void testRoomDistanceDecimalPrecision() {
            BigDecimal distance = new BigDecimal("3.14159265");
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .distanceFromCityCenter(distance)
                    .build();

            assertEquals(distance, room.getDistanceFromCityCenter());
        }

        @Test
        @DisplayName("Negative decimal distance")
        void testNegativeDecimalDistance() {
            BigDecimal negativeDist = BigDecimal.valueOf(-1.5);
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .distanceFromBeach(negativeDist)
                    .build();

            assertEquals(negativeDist, room.getDistanceFromBeach());
        }

        @Test
        @DisplayName("Negative decimal price")
        void testNegativePrice() {
            BigDecimal negativePrice = BigDecimal.valueOf(-50.00);
            Room room = new Room.RoomBuilder(2, negativePrice).build();

            assertEquals(negativePrice, room.getPricePerNight());
        }
    }

    @Nested
    @DisplayName("Searchability Edge Cases")
    class SearchabilityEdgeCasesTest {

        @Test
        @DisplayName("Search keywords with special characters")
        void testSearchKeywordsSpecialCharacters() {
            Guest guest = new Guest("José", 30);
            Set<Pair<String, String>> keywords = guest.getKeyWord();

            assertTrue(keywords.stream().anyMatch(p -> p.getValue().contains("José")));
        }

        @Test
        @DisplayName("Search keywords with numbers")
        void testSearchKeywordsNumbers() {
            Guest guest = new Guest("User123", 30);
            Set<Pair<String, String>> keywords = guest.getKeyWord();

            assertTrue(keywords.stream().anyMatch(p -> p.getValue().contains("User123")));
        }

        @Test
        @DisplayName("Room amenities in keywords")
        void testRoomAmenitiesKeywords() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .addAmenity(Room.Amenity.GYM)
                    .addAmenity(Room.Amenity.WIFI)
                    .build();

            Set<Pair<String, String>> keywords = room.getKeyWord();

            assertTrue(keywords.stream().anyMatch(p -> p.getKey().equals("GYM")));
            assertTrue(keywords.stream().anyMatch(p -> p.getKey().equals("WIFI")));
        }

        @Test
        @DisplayName("Multiple amenities searchable independently")
        void testMultipleAmenitiesSearchable() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .addAmenity(Room.Amenity.POOL)
                    .addAmenity(Room.Amenity.PARKING)
                    .addAmenity(Room.Amenity.BREAKFAST)
                    .build();

            Set<Pair<String, String>> keywords = room.getKeyWord();
            long amenityCount = keywords.stream()
                    .filter(p -> p.getKey().equals(p.getValue()))
                    .filter(p -> p.getValue().matches("POOL|PARKING|BREAKFAST"))
                    .count();

            assertEquals(3, amenityCount);
        }
    }

    @Nested
    @DisplayName("Immutability Tests")
    class ImmutabilityTest {

        @Test
        @DisplayName("Room properties should not change")
        void testRoomPropertiesImmutable() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .sizeInSqrM(25)
                    .build();

            Integer beds1 = room.getNumOfBeds();
            Integer beds2 = room.getNumOfBeds();

            assertEquals(beds1, beds2);
        }

        @Test
        @DisplayName("User properties should not change")
        void testUserPropertiesImmutable() {
            Guest guest = new Guest("John", 30);

            String name1 = guest.getName();
            Integer age1 = guest.getAge();

            String name2 = guest.getName();
            Integer age2 = guest.getAge();

            assertEquals(name1, name2);
            assertEquals(age1, age2);
        }

        @Test
        @DisplayName("User ID should not change")
        void testUserIdImmutable() {
            Guest guest = new Guest("Test", 25);

            var id1 = guest.getId();
            var id2 = guest.getId();

            assertEquals(id1, id2);
        }
    }

    @Nested
    @DisplayName("Collection Behavior Tests")
    class CollectionBehaviorTest {

        @Test
        @DisplayName("Room with all six amenities")
        void testRoomAllAmenities() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .addAmenity(Room.Amenity.GYM)
                    .addAmenity(Room.Amenity.WIFI)
                    .addAmenity(Room.Amenity.POOL)
                    .addAmenity(Room.Amenity.PARKING)
                    .addAmenity(Room.Amenity.SPA)
                    .addAmenity(Room.Amenity.BREAKFAST)
                    .build();

            assertEquals(6, room.getAmenities().size());
            assertEquals(Room.Amenity.values().length, room.getAmenities().size());
        }

        @Test
        @DisplayName("Room amenity set is not null")
        void testRoomAmenitiesNotNull() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100)).build();

            assertNotNull(room.getAmenities());
        }

        @Test
        @DisplayName("Keywords set should not be empty for valid entity")
        void testKeywordsNotEmpty() {
            Guest guest = new Guest("Test", 30);
            Set<Pair<String, String>> keywords = guest.getKeyWord();

            assertNotNull(keywords);
            assertFalse(keywords.isEmpty());
        }
    }

    @Nested
    @DisplayName("Boundary Integer Values")
    class BoundaryIntegerValuesTest {

        @Test
        @DisplayName("Room with Integer.MAX_VALUE beds")
        void testRoomMaxIntegerBeds() {
            Room room = new Room.RoomBuilder(Integer.MAX_VALUE, BigDecimal.valueOf(1000)).build();

            assertEquals(Integer.MAX_VALUE, room.getNumOfBeds());
        }

        @Test
        @DisplayName("Guest with Integer.MAX_VALUE age")
        void testGuestMaxAge() {
            Guest guest = new Guest("Ancient", Integer.MAX_VALUE);

            assertEquals(Integer.MAX_VALUE, guest.getAge());
        }

        @Test
        @DisplayName("Room with Integer.MAX_VALUE size")
        void testRoomMaxSize() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .sizeInSqrM(Integer.MAX_VALUE)
                    .build();

            assertEquals(Integer.MAX_VALUE, room.getSizeInSqrM());
        }

        @Test
        @DisplayName("Room size default is 0")
        void testRoomDefaultSize() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100)).build();

            assertEquals(0, room.getSizeInSqrM());
        }
    }
}
