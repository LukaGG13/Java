package org.example.entity;

import java.math.BigDecimal;
import java.util.Scanner;

public final class Admin extends User implements AdminInterface{
    public Admin(String name, Integer age) {
        super(name, age);
    }

    private Room.RoomBuilder buildRoom(Room.RoomBuilder roomBuilder) {
        Boolean condition = Boolean.TRUE;
        while (condition) {
            System.out.println("""
                    Select:
                    1) sizeInSqrM
                    2) distanceFromCityCenter
                    3) distanceFromBeach
                    4) breakfastIncluded
                    5) finish""");

            Scanner sc = new Scanner(System.in);
            Integer answer = sc.nextInt();

            switch (answer) {
                case 1 -> roomBuilder.sizeInSqrM(sc.nextInt());
                case 2 -> roomBuilder.distanceFromCityCenter(sc.nextBigDecimal());
                case 3 -> roomBuilder.distanceFromBeach(sc.nextBigDecimal());
                case 4 -> roomBuilder.breakFastIncluded(sc.nextBoolean());
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
