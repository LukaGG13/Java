package org.example.java.utils;

import java.util.List;
import java.util.Map;

public class ControllerUtils {
    private ControllerUtils() {}
    private static final Map<String, List<String>> classToFieldLookUp = Map.of(
            "User", List.of("Field", "Age", "Name"),
            "Booking", List.of("Field", "CheckIn", "CheckOut", "Room", "User"),
            "Room", List.of("Field", "numOfBeds","sizeInSqrM","pricePerNight","distanceFromCityCenter","distanceFromBeach", "GYM","WIFI","POOL","PARKING","SPA","BREAKFAST"),
            "Review", List.of("Field", "Guest", "Message", "Date", "Rating")
    );

    public static List<String> classToFields(String className) {
        return classToFieldLookUp.getOrDefault(className, List.of("Field"));
    }
}
