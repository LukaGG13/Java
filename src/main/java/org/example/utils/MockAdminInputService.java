package org.example.utils;

import org.example.entity.AdminInputService;
import org.example.entity.Room;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;

public class MockAdminInputService implements AdminInputService {

    public static final Random random = new Random();

    @Override
    public Integer askInteger(String message) {
        return random.nextInt(1, 6);
    }

    @Override
    public BigDecimal askBigDecimal(String message) {
        return BigDecimal.valueOf(random.nextFloat()).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public Boolean askBoolean(String message) {
        return random.nextBoolean();
    }

    @Override
    public Room.Amenity askRoomAmenity(String message) {
        return Room.Amenity.values()[random.nextInt(0, Room.Amenity.values().length)];
    }
}
