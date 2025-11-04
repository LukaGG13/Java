package org.example.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Class representing a room
 * @version 1.0
 * @author luka
 */
public final class Room {
    private static final Logger log = LoggerFactory.getLogger(Room.class);
    private final Integer numOfBeds;
    private final Integer sizeInSqrM;
    private final BigDecimal pricePerNight;
    private final BigDecimal distanceFromCityCenter;
    private final BigDecimal distanceFromBeach;
    private final Boolean breakfastIncluded;

    /**
     * RoomBuilder - using builder pattern.
     * @version 1.0
     * @author luka
     */
    static final class RoomBuilder {
        private final Integer numOfBeds;
        private final BigDecimal pricePerNight;
        private Integer sizeInSqrM = 0;
        private BigDecimal distanceFromCityCenter =  new BigDecimal(0);
        private BigDecimal distanceFromBeach = new BigDecimal(0);
        private Boolean breakfastIncluded = false;

        /**
         * Constructor for RoomBuilder.
         * @param numOfBeds Number of beds in the room, as {@link Integer}.
         * @param pricePerNight Price for one night in euros, as {@link BigDecimal}.
         */
        public RoomBuilder(Integer numOfBeds, BigDecimal pricePerNight) {
            log.info("Created a room builder with number of beds:{} and price per night:{}",numOfBeds, pricePerNight);
            this.numOfBeds = numOfBeds;
            this.pricePerNight = pricePerNight;
        }

        /**
         * Sets the size of the room in square meters.
         * @param sizeInSqrM the size of the room to be set in meters, as {@link Integer}.
         * @return The current {@link RoomBuilder} for builder pattern.
         */
        public RoomBuilder sizeInSqrM(Integer sizeInSqrM) {
            this.sizeInSqrM = sizeInSqrM;
            return this;
        }

        /**
         * Sets the distance from City Center in kilometers.
         * @param distanceFromCityCenter distance from city center to be set in kilometers, as {@link BigDecimal}.
         * @return The current {@link RoomBuilder} for builder pattern.
         */
        public RoomBuilder distanceFromCityCenter(BigDecimal distanceFromCityCenter) {
            this.distanceFromCityCenter = distanceFromCityCenter;
            return this;
        }

        /**
         * Sets the distance from Beach in kilometers.
         * @param distanceFromBeach distance from beach to be set in kilometers, as {@link BigDecimal}.
         * @return The current {@link RoomBuilder} for builder pattern.
         */
        public RoomBuilder distanceFromBeach(BigDecimal distanceFromBeach) {
            this.distanceFromBeach = distanceFromBeach;
            return this;
        }

        /**
         * Sets if breakfast is included.
         * @param breakfastIncluded Flag to set as {@link Boolean} if breakfast is included.
         * @return The current {@link RoomBuilder} for builder pattern.
         */
        public RoomBuilder breakFastIncluded(Boolean breakfastIncluded) {
           this.breakfastIncluded = breakfastIncluded;
           return this;
        }

        /**
         * Build method for builder pattern.
         * @return A new {@link Room} object.
         */
        public Room build() {
            return new Room(this);
        }
    }

    /**
     * Room constructor using builder.
     * @param roomBuilder {@link RoomBuilder} object to be built from.
     */
    Room(RoomBuilder roomBuilder) {
        log.info("created new room");
        this.numOfBeds = roomBuilder.numOfBeds;
        this.sizeInSqrM = roomBuilder.sizeInSqrM;
        this.pricePerNight = roomBuilder.pricePerNight;
        this.distanceFromCityCenter = roomBuilder.distanceFromCityCenter;
        this.distanceFromBeach = roomBuilder.distanceFromBeach;
        this.breakfastIncluded = roomBuilder.breakfastIncluded;
    }

    /**
     * Gets the number of beds in the room.
     * @return number of beds, as {@link Integer}.
     */
    public Integer getNumOfBeds() {
        return numOfBeds;
    }

    /**
     * Gets the size of the room in square meters.
     * @return size of the room, as {@link Integer}.
     */
    public Integer getSizeInSqrM() {
        return sizeInSqrM;
    }

    /**
     * Returns the price for one night stay in euros.
     * @return the price of stay for one night, as {@link BigDecimal}.
     */
    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    /**
     * Gets the distance of the room form city center in kilometers.
     * @return The distance from city center in kilometers, as {@link BigDecimal}
     */
    public BigDecimal getDistanceFromCityCenter() {
        return distanceFromCityCenter;
    }

    /**
     * Gets the distance of the room form the beach in kilometers.
     * @return The distance from the beach in kilometers, as {@link BigDecimal}
     */
    public BigDecimal getDistanceFromBeach() {
        return distanceFromBeach;
    }

    /**
     * Returns if breakfast is included.
     * @return Returns if breakfast is included, as {@link Boolean}.
     */
    public Boolean getBreakfastIncluded() {
        return breakfastIncluded;
    }

    @Override
    public String toString() {
        return "Room{" +
                "numOfBeds=" + numOfBeds +
                ", sizeInSqrM=" + sizeInSqrM +
                ", pricePerNight=" + pricePerNight +
                ", distanceFromCityCenter=" + distanceFromCityCenter +
                ", distanceFromBeach=" + distanceFromBeach +
                ", breakfastIncluded=" + breakfastIncluded +
                '}';
    }
}