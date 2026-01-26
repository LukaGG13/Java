package org.example.java.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ControllerUtils Tests")
class ControllerUtilsTest {

    @Test
    @DisplayName("Should return User fields")
    void testClassToFieldsUser() {
        List<String> fields = ControllerUtils.classToFields("User");

        assertNotNull(fields);
        assertTrue(fields.contains("Field"));
        assertTrue(fields.contains("Age"));
        assertTrue(fields.contains("Name"));
        assertEquals(3, fields.size());
    }

    @Test
    @DisplayName("Should return Booking fields")
    void testClassToFieldsBooking() {
        List<String> fields = ControllerUtils.classToFields("Booking");

        assertNotNull(fields);
        assertTrue(fields.contains("Field"));
        assertTrue(fields.contains("CheckIn"));
        assertTrue(fields.contains("CheckOut"));
        assertTrue(fields.contains("Room"));
        assertTrue(fields.contains("User"));
        assertEquals(5, fields.size());
    }

    @Test
    @DisplayName("Should return Room fields and amenities")
    void testClassToFieldsRoom() {
        List<String> fields = ControllerUtils.classToFields("Room");

        assertNotNull(fields);
        assertTrue(fields.contains("Field"));
        assertTrue(fields.contains("numOfBeds"));
        assertTrue(fields.contains("sizeInSqrM"));
        assertTrue(fields.contains("pricePerNight"));
        assertTrue(fields.contains("distanceFromCityCenter"));
        assertTrue(fields.contains("distanceFromBeach"));
        // Amenities
        assertTrue(fields.contains("GYM"));
        assertTrue(fields.contains("WIFI"));
        assertTrue(fields.contains("POOL"));
        assertTrue(fields.contains("PARKING"));
        assertTrue(fields.contains("SPA"));
        assertTrue(fields.contains("BREAKFAST"));
        assertEquals(12, fields.size());
    }

    @Test
    @DisplayName("Should return Review fields")
    void testClassToFieldsReview() {
        List<String> fields = ControllerUtils.classToFields("Review");

        assertNotNull(fields);
        assertTrue(fields.contains("Field"));
        assertTrue(fields.contains("Guest"));
        assertTrue(fields.contains("Message"));
        assertTrue(fields.contains("Date"));
        assertTrue(fields.contains("Rating"));
        assertEquals(5, fields.size());
    }

    @Test
    @DisplayName("Should return default Field for unknown class")
    void testClassToFieldsUnknownClass() {
        List<String> fields = ControllerUtils.classToFields("UnknownClass");

        assertNotNull(fields);
        assertEquals(1, fields.size());
        assertTrue(fields.contains("Field"));
    }

    @Test
    @DisplayName("Should return default Field for null class name")
    void testClassToFieldsNullClassName() {
        List<String> fields = ControllerUtils.classToFields(null);

        assertNotNull(fields);
        assertEquals(1, fields.size());
        assertTrue(fields.contains("Field"));
    }

    @Test
    @DisplayName("Should return default Field for empty string")
    void testClassToFieldsEmptyString() {
        List<String> fields = ControllerUtils.classToFields("");

        assertNotNull(fields);
        assertEquals(1, fields.size());
        assertTrue(fields.contains("Field"));
    }

    @Test
    @DisplayName("Should return fields for case-sensitive class names")
    void testClassToFieldsCaseSensitive() {
        List<String> fieldsLower = ControllerUtils.classToFields("user");
        List<String> fieldsUpper = ControllerUtils.classToFields("USER");

        // Should return default since case matters
        assertEquals(1, fieldsLower.size());
        assertEquals(1, fieldsUpper.size());
    }

    @Test
    @DisplayName("Should always return non-null list")
    void testClassToFieldsNeverNull() {
        List<String> fields1 = ControllerUtils.classToFields("Room");
        List<String> fields2 = ControllerUtils.classToFields("RandomClass");

        assertNotNull(fields1);
        assertNotNull(fields2);
    }

    @Test
    @DisplayName("Should return consistent results for same class")
    void testClassToFieldsConsistency() {
        List<String> fields1 = ControllerUtils.classToFields("Booking");
        List<String> fields2 = ControllerUtils.classToFields("Booking");

        assertEquals(fields1, fields2);
    }

    @Test
    @DisplayName("Room amenities should all be present")
    void testRoomAmenitiesComplete() {
        List<String> fields = ControllerUtils.classToFields("Room");

        assertTrue(fields.stream().anyMatch(f -> f.equals("GYM")));
        assertTrue(fields.stream().anyMatch(f -> f.equals("WIFI")));
        assertTrue(fields.stream().anyMatch(f -> f.equals("POOL")));
        assertTrue(fields.stream().anyMatch(f -> f.equals("PARKING")));
        assertTrue(fields.stream().anyMatch(f -> f.equals("SPA")));
        assertTrue(fields.stream().anyMatch(f -> f.equals("BREAKFAST")));
    }
}
