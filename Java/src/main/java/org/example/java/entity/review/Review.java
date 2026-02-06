package org.example.java.entity.review;

import org.example.java.entity.guest.Guest;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * A record representing a {@link Review} left by the {@link Guest}.
 * @param uuid The {@link UUID} of the{@link Review}.
 * @param guest The {@link Guest} that left the {@link Review}.
 * @param message The {@link String} message left by the {@link Guest}.
 * @param reviewDate The {@link LocalDate} when the {@link Review} was left.
 * @param rating The {@link Integer} representation of the {@link Guest}'s satisfaction
 *               must be in between 1 and 10.
 */
public record Review(UUID uuid, Guest guest, String message, LocalDate reviewDate, Integer rating) {
    /**
     * Constructs a new {@link Review} objects and preforms validity checks.
     * @param uuid The {@link UUID} of the{@link Review}.
     * @param guest The {@link Guest} that left the {@link Review}.
     * @param message The {@link String} message left by the {@link Guest}.
     * @param reviewDate The {@link LocalDate} when the {@link Review} was left.
     * @param rating The {@link Integer} representation of the {@link Guest}'s satisfaction
     *               must be in between 1 and 10.
     * @throws IllegalArgumentException if {@link #reviewDate} is in the future
     *                                  or {@link #rating} is smaller than 1 or bigger than 10.
     */

    public Review {
        Objects.requireNonNull(uuid, "UUID cant be null in review");
        Objects.requireNonNull(guest, "Guest cant be null in review");
        Objects.requireNonNull(message, "Message cant be null in review");
        Objects.requireNonNull(reviewDate, "Review date cant be null in review");
        Objects.requireNonNull(rating, "Rating cant be null in review");

        if(reviewDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Review date cant be in the future");
        }

        if(rating < 1 || rating > 10) {
            throw new IllegalArgumentException("Rating must be in between 1 and 10");
        }
    }
    /**
     * Constructs a new {@link Review} objects and preforms validity checks.
     * @param guest The {@link Guest} that left the {@link Review}.
     * @param message The {@link String} message left by the {@link Guest}.
     * @param reviewDate The {@link LocalDate} when the {@link Review} was left.
     * @param rating The {@link Integer} representation of the {@link Guest}'s satisfaction
     *               must be in between 1 and 10.
     * @throws IllegalArgumentException if {@link #reviewDate} is in the future
     *                                  or {@link #rating} is smaller than 1 or bigger than 10.
     */

    public Review(Guest guest, String message, LocalDate reviewDate, Integer rating) {
        this(UUID.randomUUID(), guest, message, reviewDate, rating);
    }
}
