package org.example.java.entity.guest;

import org.example.java.entity.repository.Repository;
import org.example.java.entity.room.Room;
import org.example.java.utils.InputOutputUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.lang.IO.println;
import static java.lang.IO.readln;

public class GuestInputServiceConsole implements GuestInputService{

    private static final Logger log = LoggerFactory.getLogger(GuestInputServiceConsole.class);
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
    private final Repository repository;

    public GuestInputServiceConsole(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Integer askInteger(String msg, Integer lowerBound, Integer upperBound) {
        while(true){
            try {
                Integer integer = Integer.parseInt(readln(msg));
                if(lowerBound <= integer && integer < upperBound) {
                    return integer;
                } else {
                    println("Integer to small or to large");
                }
            } catch (NumberFormatException e) {
                log.error("Error in askInteger", e);
                println("Error input couldn't be parsed");
            }
        }
    }

    @Override
    public Integer askInteger(String msg) {
        while(true){
            try {
                return Integer.parseInt(readln(msg));
            } catch (NumberFormatException e) {
                log.error("Error in askInteger", e);
                println("Error input couldn't be parsed");
            }
        }
    }

    @Override
    public LocalDateTime askLocalDateTime(String msg, LocalDateTime lowerBound, LocalDateTime upperBound) {
        while(true) {
            try {
                LocalDateTime dateTime = LocalDateTime.parse(readln(msg), fmt);
                if (dateTime.isAfter(lowerBound) && dateTime.isBefore(upperBound)){
                    return dateTime;
                } else {
                    println("Date time is before lower bound or after upper bound");
                }
            } catch (DateTimeParseException e){
                log.error("Error in askLocalDateTime", e);
                println("Error date time can't be parsed.");
            }
        }
    }

    @Override
    public LocalDateTime askLocalDateTime(String msg) {
        while(true) {
            try {
                return LocalDateTime.parse(readln(msg), fmt);
            } catch (DateTimeParseException e){
                log.error("Error in askLocalDateTime", e);
                println("Error date time can't be parsed.");
            }
        }
    }

    @Override
    public String askString(String msg) {
        return readln(msg);
    }

    @Override
    public Room askRoom(String msg) {
        while (!repository.getRooms().isEmpty()) {
            try {
                InputOutputUtils.displayRooms(repository.getRooms(), repository.getReviews());
                int idx = Integer.parseInt(readln(msg));
                idx--;
                return repository.getRooms().get(idx);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                log.error("Error in askRoom", e);
                println("Something went wrong try again.");
            }
        }
        throw new IllegalStateException("Asking for rooms while no rooms");
    }
}
