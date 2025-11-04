package org.example.entity;

import java.math.BigDecimal;

/**
 * Admin interface to be implemented by the admin.
 * @version 1
 * @author luka
 */
public sealed interface AdminInterface permits Admin{
    /**
     * Creates a new {@link Room}.
     * @param numberOfBeds the number of beds to be in the room, as {@link Integer}.
     * @param pricePerNight the price of stay for one night in euros, as {@link BigDecimal}.
     * @return A new {@link Room}.
     */
    Room createRoom(Integer numberOfBeds, BigDecimal pricePerNight);
}
