package org.example.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static java.lang.IO.println;
import static java.lang.IO.readln;

/**
 * Class to get input for {@link Admin} from console.
 * @version 1.0
 * @author luka
 */
public class AdminInputServiceConsole implements AdminInputService {
    private static final Logger log = LoggerFactory.getLogger(AdminInputServiceConsole.class);

    AdminInputServiceConsole() {
    }

    /**
     * Ask the user for a {@link Integer}.
     *
     * @param message to be printed to the user.
     * @return the entered {@link Integer}.
     */
    @Override
    public Integer askInteger(String message) {
        while (true) {
            try {
                return Integer.parseInt(readln(message));
            } catch (NumberFormatException e) {
                log.error("Error in askInteger", e);
                println("Error input couldnt be parsed");
            }
        }
    }

    /**
     * Ask the user for a {@link BigDecimal}.
     *
     * @param message to be printed to the user.
     * @return the entered {@link BigDecimal}.
     */
    @Override
    public BigDecimal askBigDecimal(String message) {
        while (true) {
            try {
                return new BigDecimal(readln(message));
            } catch (NumberFormatException e) {
                log.error("Error in askBigDecimal", e);
                println("Error input couldnt be parsed");
            }
        }
    }

    /**
     * Ask the user for a {@link Boolean}.
     *
     * @param message to be printed to the user.
     * @return the entered {@link Boolean}.
     */
    @Override
    public Boolean askBoolean(String message) {
        while (true) {
            String boolStr = readln(message);
            if ("true".equalsIgnoreCase(boolStr)) {
                return Boolean.TRUE;
            }
            if ("false".equalsIgnoreCase(boolStr)) {
                return Boolean.FALSE;
            }
        }
    }

    @Override
    public Room.Amenity askRoomAmenity(String message) {
        while(true){
            try {
                return Room.Amenity.valueOf(readln(message));
            } catch (IllegalArgumentException e) {
                log.error("Error in askRoomAmenity", e);
                println("Error input couldnt be parsed");
            }
        }
    }
}
