package org.example.java.entity.guest;

import org.example.java.entity.room.Room;

import java.time.LocalDateTime;

public interface GuestInputService {
    Integer askInteger(String msg, Integer lowerBound, Integer upperBound);
    Integer askInteger(String msg);
    LocalDateTime askLocalDateTime(String msg, LocalDateTime lowerBound, LocalDateTime upperBound);
    LocalDateTime askLocalDateTime(String msg);
    String askString(String msg);
    Room askRoom(String msg);
}
