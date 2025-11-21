package org.example.entity;

import org.example.app.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.IO.println;

/**
 * Class for representing admins.
 * @version 1
 * @author luka
 */
public final class Admin extends User implements AdminInterface{
    private static final Logger log = LoggerFactory.getLogger(Admin.class);
    /**
     * Construct a new {@link Admin} object.
     * @param name The name of the admin, as {@link String}.
     * @param age The age of the admin, as {@link Integer}.
     */
    public Admin(String name, Integer age) {
        log.info("Created admin with name:\"{}\" and age:{}", name ,age);
        super(name, age);
    }

    /**
     * Creates a room using {@link org.example.entity.Room.RoomBuilder}.
     * @param roomBuilder {@link org.example.entity.Room.RoomBuilder}.
     * @param inputService class from witch input to get input for creating room must implement {@link AdminInputService}.
     * @return Returns a {@link org.example.entity.Room.RoomBuilder}.
     */
    public  Room.RoomBuilder buildRoom(Room.RoomBuilder roomBuilder, AdminInputService inputService) {
        boolean finished = false;
        while (!finished) {
            println("""
                Select:
                1) SizeInSqrM
                2) DistanceFromCityCenter
                3) DistanceFromBeach
                4) Add amenity
                5) Finish""");
            switch (inputService.askInteger("Chose option: ")){
                case 1 -> roomBuilder.sizeInSqrM(inputService.askInteger("Enter size in Square meters: "));
                case 2 -> roomBuilder.distanceFromCityCenter(inputService.askBigDecimal("Enter distance from city center in km: "));
                case 3 -> roomBuilder.distanceFromBeach(inputService.askBigDecimal("Enter distance form beach in km: "));
                case 4 -> roomBuilder.addAmenity(inputService.askRoomAmenity("Chose amenity " + Arrays.toString(Room.Amenity.values()) + " "));
                case 5 -> finished = true;
                default -> println("Incorrect input try again");
            }
        }

        return roomBuilder;
    }

    public <T extends AdminInputService> Room createRoom(Integer numberOfBeds, BigDecimal pricePerNight, T inputService ) {
        return buildRoom(new Room.RoomBuilder(numberOfBeds, pricePerNight), inputService).build();
    }

    @Override
    public Room createRoom(Integer numberOfBeds, BigDecimal pricePerNight) {
        return createRoom(numberOfBeds, pricePerNight, new AdminInputServiceConsole());
    }

}
