package org.example.java.entity;

import org.example.java.entity.guest.Guest;
import org.example.java.entity.review.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Review Entity Tests")
class ReviewTest {

    private Guest guest;

    @BeforeEach
    void setUp() {
        guest = new Guest("ReviewGuest", 30);
    }

    @Nested
    @DisplayName("Valid Review Creation Tests")
    class ValidReviewTest {

        @Test
        @DisplayName("Should create review with valid data")
        void testCreateValidReview() {
            Review review = new Review(guest, "Great stay!", LocalDate.now(), 5);

            assertEquals(guest, review.guest());
            assertEquals("Great stay!", review.message());
            assertEquals(LocalDate.now(), review.reviewDate());
            assertEquals(5, review.rating());
        }

        @Test
        @DisplayName("Should create review with minimum rating")
        void testCreateReviewMinimumRating() {
            Review review = new Review(guest, "Not great", LocalDate.now(), 1);
            assertEquals(1, review.rating());
        }

        @Test
        @DisplayName("Should create review with maximum rating")
        void testCreateReviewMaximumRating() {
            Review review = new Review(guest, "Excellent!", LocalDate.now(), 10);
            assertEquals(10, review.rating());
        }

        @Test
        @DisplayName("Should create review with past date")
        void testCreateReviewPastDate() {
            LocalDate pastDate = LocalDate.now().minusDays(1);
            Review review = new Review(guest, "Good", pastDate, 8);
            assertEquals(pastDate, review.reviewDate());
        }

        @Test
        @DisplayName("Should create review with today date")
        void testCreateReviewTodayDate() {
            Review review = new Review(guest, "Good", LocalDate.now(), 7);
            assertEquals(LocalDate.now(), review.reviewDate());
        }

        @Test
        @DisplayName("Should create review with long message")
        void testCreateReviewLongMessage() {
            String longMessage = "a".repeat(1000);
            Review review = new Review(guest, longMessage, LocalDate.now(), 5);
            assertEquals(longMessage, review.message());
        }

        @Test
        @DisplayName("Should create review with empty message")
        void testCreateReviewEmptyMessage() {
            Review review = new Review(guest, "", LocalDate.now(), 5);
            assertEquals("", review.message());
        }
    }

    @Nested
    @DisplayName("Invalid Review Tests")
    class InvalidReviewTest {

        @Test
        @DisplayName("Should throw exception for rating below 1")
        void testRatingBelowMinimum() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Review(guest, "Bad", LocalDate.now(), 0),
                    "Rating must be at least 1");
        }

        @Test
        @DisplayName("Should throw exception for rating above 10")
        void testRatingAboveMaximum() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Review(guest, "Bad", LocalDate.now(), 11),
                    "Rating must be at most 10");
        }

        @Test
        @DisplayName("Should throw exception for negative rating")
        void testNegativeRating() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Review(guest, "Bad", LocalDate.now(), -5));
        }

        @Test
        @DisplayName("Should throw exception for future review date")
        void testFutureReviewDate() {
            LocalDate futureDate = LocalDate.now().plusDays(1);
            assertThrows(IllegalArgumentException.class,
                    () -> new Review(guest, "Future review", futureDate, 5),
                    "Review date cannot be in the future");
        }

        @Test
        @DisplayName("Should throw exception for far future date")
        void testFarFutureReviewDate() {
            LocalDate farFutureDate = LocalDate.now().plusYears(10);
            assertThrows(IllegalArgumentException.class,
                    () -> new Review(guest, "Future review", farFutureDate, 5));
        }
    }

    @Nested
    @DisplayName("Null Pointer Tests")
    class ReviewNullPointerTest {

        @Test
        @DisplayName("Should throw NullPointerException when guest is null")
        void testNullGuest() {
            assertThrows(NullPointerException.class,
                    () -> new Review(null, "Message", LocalDate.now(), 5));
        }

        @Test
        @DisplayName("Should throw NullPointerException when message is null")
        void testNullMessage() {
            assertThrows(NullPointerException.class,
                    () -> new Review(guest, null, LocalDate.now(), 5));
        }

        @Test
        @DisplayName("Should throw NullPointerException when date is null")
        void testNullDate() {
            assertThrows(NullPointerException.class,
                    () -> new Review(guest, "Message", null, 5));
        }

        @Test
        @DisplayName("Should throw NullPointerException when rating is null")
        void testNullRating() {
            assertThrows(NullPointerException.class,
                    () -> new Review(guest, "Message", LocalDate.now(), null));
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    @DisplayName("Should accept all valid ratings from 1 to 10")
    void testValidRatings(int rating) {
        Review review = new Review(guest, "Test", LocalDate.now(), rating);
        assertEquals(rating, review.rating());
    }

    @Nested
    @DisplayName("Review Record Tests")
    class ReviewRecordTest {

        @Test
        @DisplayName("Should access review fields as record components")
        void testRecordComponents() {
            Review review = new Review(guest, "Good stay", LocalDate.now(), 8);

            assertEquals(guest, review.guest());
            assertEquals("Good stay", review.message());
            assertEquals(LocalDate.now(), review.reviewDate());
            assertEquals(8, review.rating());
        }

        @Test
        @DisplayName("Should have correct toString representation")
        void testReviewToString() {
            Review review = new Review(guest, "Test", LocalDate.now(), 5);
            String toString = review.toString();

            assertNotNull(toString);
            assertFalse(toString.isEmpty());
        }
    }

    @Nested
    @DisplayName("Multiple Review Tests")
    class MultipleReviewTest {

        @Test
        @DisplayName("Should create multiple reviews for same guest")
        void testMultipleReviewsSameGuest() {
            Review review1 = new Review(guest, "First visit", LocalDate.now().minusDays(1), 7);
            Review review2 = new Review(guest, "Second visit", LocalDate.now(), 9);

            assertNotNull(review1);
            assertNotNull(review2);
            assertEquals(guest, review1.guest());
            assertEquals(guest, review2.guest());
        }

        @Test
        @DisplayName("Should create reviews with different guests")
        void testReviewsDifferentGuests() {
            Guest guest1 = new Guest("Guest1", 25);
            Guest guest2 = new Guest("Guest2", 35);

            Review review1 = new Review(guest1, "Good", LocalDate.now(), 8);
            Review review2 = new Review(guest2, "Great", LocalDate.now(), 9);

            assertNotEquals(review1.guest(), review2.guest());
        }
    }

    @Nested
    @DisplayName("Review Date Edge Cases")
    class ReviewDateEdgeCasesTest {

        @Test
        @DisplayName("Should allow review from very old date")
        void testVeryOldReviewDate() {
            LocalDate oldDate = LocalDate.now().minusYears(10);
            Review review = new Review(guest, "Old review", oldDate, 5);
            assertEquals(oldDate, review.reviewDate());
        }

        @Test
        @DisplayName("Should not allow review from tomorrow")
        void testTomorrowReviewDate() {
            LocalDate tomorrow = LocalDate.now().plusDays(1);
            assertThrows(IllegalArgumentException.class,
                    () -> new Review(guest, "Future", tomorrow, 5));
        }
    }
}
