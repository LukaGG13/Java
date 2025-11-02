package org.example.entity;

import java.math.BigDecimal;

public final class Room {
    private final Integer numOfBeds;
    private final Integer sizeInSqrM;
    private final BigDecimal pricePerNight;
    private final BigDecimal distanceFromCityCenter;
    private final BigDecimal distanceFromBeach;
    private final Boolean breakfastIncluded;

    static final class RoomBuilder {
        private final Integer numOfBeds;
        private final BigDecimal pricePerNight;
        private Integer sizeInSqrM = 0;
        private BigDecimal distanceFromCityCenter =  new BigDecimal(0);
        private BigDecimal distanceFromBeach = new BigDecimal(0);
        private Boolean breakfastIncluded = false;

        public RoomBuilder(Integer numOfBeds, BigDecimal pricePerNight) {
            this.numOfBeds = numOfBeds;
            this.pricePerNight = pricePerNight;
        }

        public RoomBuilder sizeInSqrM(Integer sizeInSqrM) {
            this.sizeInSqrM = sizeInSqrM;
            return this;
        }

        public RoomBuilder distanceFromCityCenter(BigDecimal distanceFromCityCenter) {
            this.distanceFromCityCenter = distanceFromCityCenter;
            return this;
        }

        public RoomBuilder distanceFromBeach(BigDecimal distanceFromBeach) {
            this.distanceFromBeach = distanceFromBeach;
            return this;
        }

        public RoomBuilder breakFastIncluded(Boolean breakfastIncluded) {
           this.breakfastIncluded = breakfastIncluded;
           return this;
        }

        public Room build() {
            return new Room(this);
        }
    }

    Room(RoomBuilder roomBuilder) {
        this.numOfBeds = roomBuilder.numOfBeds;
        this.sizeInSqrM = roomBuilder.sizeInSqrM;
        this.pricePerNight = roomBuilder.pricePerNight;
        this.distanceFromCityCenter = roomBuilder.distanceFromCityCenter;
        this.distanceFromBeach = roomBuilder.distanceFromBeach;
        this.breakfastIncluded = roomBuilder.breakfastIncluded;
    }

    public Integer getNumOfBeds() {
        return numOfBeds;
    }

    public Integer getSizeInSqrM() {
        return sizeInSqrM;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public BigDecimal getDistanceFromCityCenter() {
        return distanceFromCityCenter;
    }

    public BigDecimal getDistanceFromBeach() {
        return distanceFromBeach;
    }

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