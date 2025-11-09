package org.example.entity;

import org.example.app.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Scanner;

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
     * Prints out an interface to STDOUT for user to interact with {@link org.example.entity.Room.RoomBuilder}
     * @param roomBuilder {@link org.example.entity.Room.RoomBuilder}
     * @return Returns a {@link org.example.entity.Room.RoomBuilder}
     */
    private Room.RoomBuilder buildRoom(Room.RoomBuilder roomBuilder) {
        Boolean condition = Boolean.TRUE;
        while (condition) {
            System.out.println("""
                    Select:
                    1) sizeInSqrM
                    2) distanceFromCityCenter
                    3) distanceFromBeach
                    4) add amenity
                    5) finish""");

            Scanner sc = new Scanner(System.in);
            Integer answer = sc.nextInt();

            switch (answer) {
                case 1 -> roomBuilder.sizeInSqrM(sc.nextInt());
                case 2 -> roomBuilder.distanceFromCityCenter(sc.nextBigDecimal());
                case 3 -> roomBuilder.distanceFromBeach(sc.nextBigDecimal());
                case 4 -> {
                    for(Room.Amenity amenity : Room.Amenity.values()) {
                        System.out.println(amenity.name());
                    }
                    sc.nextLine();
                    String amenity = sc.nextLine();
                    roomBuilder.addAmenity(Room.Amenity.valueOf(amenity));
                }
                case 5 -> condition = Boolean.FALSE;
                    default -> condition = Boolean.TRUE;
            }
        }
        return roomBuilder;
    }

    @Override
    public Room createRoom(Integer numberOfBeds, BigDecimal pricePerNight) {
        return buildRoom(new Room.RoomBuilder(numberOfBeds, pricePerNight)).build();
    }

}
