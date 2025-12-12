package org.example.java.utils;

import org.example.java.entity.review.Review;
import org.example.java.entity.room.Room;
import org.example.java.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static java.lang.IO.println;

public class InputOutputUtils {
    private InputOutputUtils() {}
    private static final Logger log = LoggerFactory.getLogger(InputOutputUtils.class);

    /**
     * Writes an {@link List} of {@link User} to STDOUT.
     *
     * @param list to be displayed.
     */
    public static void printList(List<?> list) {
        log.trace("Displaying list");
        if (list.isEmpty()) {
            println("Cant display list empty");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            println((i + 1) + ": " + list.get(i));
        }
    }

    /**
     * Writes a {@link List} of {@link Room} to STDOUT>
     * @param rooms {@link List} of type {@link Room} to be displayed.
     * @param reviews {@link Map} where key is {@link Room} object, and value is a {@link List} of
     *                           {@link Review} objects left for that room.
     */
    public static void displayRooms(List<Room> rooms, Map<Room, List<Review>> reviews) {
        log.trace("Displaying rooms and reviews");
        if (rooms.isEmpty()) {
            log.warn("Trying to display rooms while no rooms exist");
            println("Cant display rooms no rooms found");
            return;
        }


        for (int i = 0; i < rooms.size(); i++) {
            println((i + 1) + ": " + rooms.get(i));

            if(!reviews.containsKey(rooms.get(i))){
                continue;
            }

            for(Review review : reviews.get(rooms.get(i))) {
                println("\t" + review.guest().getName() + ": " + review.rating() + "/10" + "\n\t\t" + review.message());
            }
        }
    }
}
