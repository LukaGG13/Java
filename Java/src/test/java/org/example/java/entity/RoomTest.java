package org.example.java.entity;

import org.example.java.entity.room.Room;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Room Entity Tests")
class RoomTest {

    @Nested
    @DisplayName("RoomBuilder Tests")
    class RoomBuilderTest {

        @Test
        @DisplayName("Should create room with required parameters")
        void testRoomBuilderWithRequiredParams() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .build();

            assertEquals(2, room.getNumOfBeds());
            assertEquals(BigDecimal.valueOf(100), room.getPricePerNight());
        }

        @Test
        @DisplayName("Should create room with all parameters")
        void testRoomBuilderWithAllParams() {
            Room room = new Room.RoomBuilder(3, BigDecimal.valueOf(150))
                    .sizeInSqrM(50)
                    .distanceFromCityCenter(BigDecimal.valueOf(5))
                    .distanceFromBeach(BigDecimal.valueOf(2))
                    .addAmenity(Room.Amenity.WIFI)
                    .addAmenity(Room.Amenity.POOL)
                    .build();

            assertEquals(3, room.getNumOfBeds());
            assertEquals(BigDecimal.valueOf(150), room.getPricePerNight());
            assertEquals(50, room.getSizeInSqrM());
            assertEquals(BigDecimal.valueOf(5), room.getDistanceFromCityCenter());
            assertEquals(BigDecimal.valueOf(2), room.getDistanceFromBeach());
            assertEquals(2, room.getAmenities().size());
            assertTrue(room.getAmenities().contains(Room.Amenity.WIFI));
            assertTrue(room.getAmenities().contains(Room.Amenity.POOL));
        }

        @Test
        @DisplayName("Should support builder chaining")
        void testRoomBuilderChaining() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .sizeInSqrM(30)
                    .distanceFromCityCenter(BigDecimal.valueOf(3))
                    .distanceFromBeach(BigDecimal.valueOf(1))
                    .addAmenity(Room.Amenity.GYM)
                    .build();

            assertNotNull(room);
            assertEquals(2, room.getNumOfBeds());
        }

        @Test
        @DisplayName("Should add multiple amenities")
        void testAddMultipleAmenities() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .addAmenity(Room.Amenity.WIFI)
                    .addAmenity(Room.Amenity.POOL)
                    .addAmenity(Room.Amenity.PARKING)
                    .addAmenity(Room.Amenity.SPA)
                    .addAmenity(Room.Amenity.BREAKFAST)
                    .addAmenity(Room.Amenity.GYM)
                    .build();

            assertEquals(6, room.getAmenities().size());
            assertTrue(room.getAmenities().contains(Room.Amenity.BREAKFAST));
        }

        @Test
        @DisplayName("Should handle duplicate amenities correctly")
        void testDuplicateAmenities() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .addAmenity(Room.Amenity.WIFI)
                    .addAmenity(Room.Amenity.WIFI)
                    .build();

            assertEquals(1, room.getAmenities().size(), "Duplicate amenities should not be added");
        }
    }

    @Nested
    @DisplayName("Room Property Tests")
    class RoomPropertyTest {

        @Test
        @DisplayName("Should get number of beds")
        void testGetNumOfBeds() {
            Room room = new Room.RoomBuilder(4, BigDecimal.valueOf(200)).build();
            assertEquals(4, room.getNumOfBeds());
        }

        @Test
        @DisplayName("Should get size in square meters")
        void testGetSizeInSqrM() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .sizeInSqrM(45)
                    .build();
            assertEquals(45, room.getSizeInSqrM());
        }

        @Test
        @DisplayName("Should get price per night")
        void testGetPricePerNight() {
            BigDecimal price = BigDecimal.valueOf(99.99);
            Room room = new Room.RoomBuilder(2, price).build();
            assertEquals(price, room.getPricePerNight());
        }

        @Test
        @DisplayName("Should get distance from city center")
        void testGetDistanceFromCityCenter() {
            BigDecimal distance = BigDecimal.valueOf(10.5);
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .distanceFromCityCenter(distance)
                    .build();
            assertEquals(distance, room.getDistanceFromCityCenter());
        }

        @Test
        @DisplayName("Should get distance from beach")
        void testGetDistanceFromBeach() {
            BigDecimal distance = BigDecimal.valueOf(2.5);
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .distanceFromBeach(distance)
                    .build();
            assertEquals(distance, room.getDistanceFromBeach());
        }

        @Test
        @DisplayName("Should get amenities")
        void testGetAmenities() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .addAmenity(Room.Amenity.WIFI)
                    .addAmenity(Room.Amenity.POOL)
                    .build();
            Set<Room.Amenity> amenities = room.getAmenities();
            assertNotNull(amenities);
            assertEquals(2, amenities.size());
        }

        @Test
        @DisplayName("Should return empty amenities set if none added")
        void testEmptyAmenities() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100)).build();
            assertTrue(room.getAmenities().isEmpty(), "Amenities should be empty");
        }
    }

    @Nested
    @DisplayName("Room Default Values Tests")
    class RoomDefaultValuesTest {

        @Test
        @DisplayName("Should have default size of 0")
        void testDefaultSize() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100)).build();
            assertEquals(0, room.getSizeInSqrM());
        }

        @Test
        @DisplayName("Should have default distance from city center of 0")
        void testDefaultDistanceFromCityCenter() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100)).build();
            assertEquals(BigDecimal.valueOf(0), room.getDistanceFromCityCenter());
        }

        @Test
        @DisplayName("Should have default distance from beach of 0")
        void testDefaultDistanceFromBeach() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100)).build();
            assertEquals(BigDecimal.valueOf(0), room.getDistanceFromBeach());
        }
    }

    @Nested
    @DisplayName("Room Search Keywords Tests")
    class RoomSearchKeywordsTest {

        @Test
        @DisplayName("Should contain Class keyword")
        void testClassKeyword() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100)).build();
            var keywords = room.getKeyWord();
            assertTrue(keywords.stream().anyMatch(p -> p.getKey().equals("Class") && p.getValue().equals("Room")));
        }

        @Test
        @DisplayName("Should contain numOfBeds keyword")
        void testNumOfBedsKeyword() {
            Room room = new Room.RoomBuilder(3, BigDecimal.valueOf(100)).build();
            var keywords = room.getKeyWord();
            assertTrue(keywords.stream().anyMatch(p -> p.getKey().equals("numOfBeds") && p.getValue().equals("3")));
        }

        @Test
        @DisplayName("Should contain amenity keywords")
        void testAmenityKeywords() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .addAmenity(Room.Amenity.WIFI)
                    .addAmenity(Room.Amenity.POOL)
                    .build();
            var keywords = room.getKeyWord();
            assertTrue(keywords.stream().anyMatch(p -> p.getKey().equals("WIFI")));
            assertTrue(keywords.stream().anyMatch(p -> p.getKey().equals("POOL")));
        }
    }

    @Nested
    @DisplayName("Room toString Tests")
    class RoomToStringTest {

        @Test
        @DisplayName("Should generate toString with room details")
        void testToString() {
            Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                    .sizeInSqrM(30)
                    .build();
            String toString = room.toString();

            assertTrue(toString.contains("numOfBeds=2"));
            assertTrue(toString.contains("sizeInSqrM=30"));
            assertTrue(toString.contains("pricePerNight=100"));
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 8})
    @DisplayName("Should accept various number of beds")
    void testVariousNumberOfBeds(int numBeds) {
        Room room = new Room.RoomBuilder(numBeds, BigDecimal.valueOf(100)).build();
        assertEquals(numBeds, room.getNumOfBeds());
    }
}
