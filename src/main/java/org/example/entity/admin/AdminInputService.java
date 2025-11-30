package org.example.entity.admin;

import org.example.entity.room.Room;

import java.math.BigDecimal;


/**
 * Interface that defines how {@link Admin} should receive input.
 * @version 1.0
 * @author luka
 */
public interface AdminInputService {
    Integer askInteger(String message);
    BigDecimal askBigDecimal (String message);
    Boolean askBoolean (String message);
    Room.Amenity askRoomAmenity(String message);
}
