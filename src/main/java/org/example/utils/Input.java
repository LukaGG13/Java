package org.example.utils;

import net.datafaker.Faker;
import org.example.entity.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

/**
 * A utility class made for making getting input easer
 */
public final class Input {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();
    private static final Logger log = LoggerFactory.getLogger(Input.class);

    private static final Integer DEFAULT_NUMBER = 10;

    private static final Integer ODS_FOR_ADMIN = 10;

    private static final Integer MIN_AGE_OF_USER = 18;
    private static final Integer MAX_AGE_OF_USER  = 90;

    private static final Integer MIN_NUMBER_OF_BEDS = 1;
    private static final Integer MAX_NUMBER_OF_BEDS  = 6;

    private static final Double MIN_PICE_PER_NIGHT = 10.0;
    private static final Double MAX_PICE_PER_NIGHT = 500.0;

    private static final Integer MAX_DAYS_UNTIL_BOOKING = 14;
    private static final Integer MAX_BOOKING_LENGHT = 14;

    private static final Integer CHECK_IN_HOUR = 8;
    private static final Integer CHECK_IN_MINUTE = 0;

    private static final Integer CHECK_OUT_HOUR = 10;
    private static final Integer CHECK_OUT_MINUTE = 0;

    private Input() {}

    /**
     * Returns  a mock {@link Admin}.
     * @return the mocked {@link Admin}.
     */
    public static Admin returnMockAdmin(){
        log.trace("Creating mock Admin");
        Integer age = random.nextInt(MIN_AGE_OF_USER, MAX_AGE_OF_USER);
        String name = faker.spongebob().characters();

        return new Admin(name, age);
    }

    /**
     * Returns  a mock {@link Guest}.
     * @return the mocked {@link Guest}.
     */
    public static Guest returnMockGuest(){
        log.trace("Creating mock guest");
        Integer age = random.nextInt(MIN_AGE_OF_USER, MAX_AGE_OF_USER);
        String name = faker.starWars().alternateCharacterSpelling();

        return new Guest(name, age);
    }

    /**
     * Adds n fake {@link User}'s to a {@link List} of {@link User}'s.
     * @param users A {@link List} of {@link User}'s to witch the fake users will be added.
     * @param n The number of fake users to create.
     */
    public static void mockUsers(List<User> users, Integer n) {
        log.trace("Mocking {} users", n);
        for (Integer i = 0; i < n; i++) {
            if (random.nextInt(100) <= ODS_FOR_ADMIN) {
                users.add(returnMockAdmin());
            } else {
                users.add(returnMockGuest());
            }
        }
    }

    /**
     * Writest the default number of mocked {@link User}'s to the {@link List}.
     * @param users the {@link List} where the {@link User}'s will be saved.
     */
    public static void mockUsers(List<User> users) {
        mockUsers(users, DEFAULT_NUMBER);
    }

    /**
     * Returns a mocked {@link Room}.
     * @return the mocked {@link Room}.
     */
    public static Room returnMockRoom(){
        log.trace("Mocking room");
        Admin tempAdmin = returnMockAdmin();
        Integer numberOfBeds = random.nextInt(MIN_NUMBER_OF_BEDS, MAX_NUMBER_OF_BEDS);
        BigDecimal pricePerNight = BigDecimal.valueOf(random.nextDouble(MIN_PICE_PER_NIGHT, MAX_PICE_PER_NIGHT)).setScale(2, RoundingMode.HALF_UP);
        return tempAdmin.createRoom(numberOfBeds, pricePerNight, new MockAdminInputService());
    }

    /**
     * Write n mocked {@link Room}'s to the provided {@link List}.
     * @param rooms The {@link List} where the {@link Room}'s will be written.
     * @param n The number of rooms to mock.
     */
    public static void mockRooms(List<Room> rooms, Integer n){
        log.trace("Mocking {} rooms", n);
        for (Integer i = 0; i < n; i++){
           rooms.add(returnMockRoom());
        }
    }

    /**
     * Writest the default number of mocked {@link Room}'s to the {@link List}.
     * @param rooms the {@link List} where the {@link Room}'s will be saved.
     */
    public static void mockRooms(List<Room> rooms) {
        mockRooms(rooms, DEFAULT_NUMBER);
    }

    /**
     * Mock a {@link Booking}.
     * @return the mocked {@link Booking}.
     */
    public static Booking returnMockBooking() {
        log.trace("Mocking booking");
        Integer daysUntilBooking = random.nextInt(1, MAX_DAYS_UNTIL_BOOKING);
        Integer lengthOfBooking = random.nextInt(1, MAX_BOOKING_LENGHT);

        LocalDateTime checkIn = LocalDateTime.of(LocalDate.now().plusDays(daysUntilBooking), LocalTime.of(CHECK_IN_HOUR, CHECK_IN_MINUTE));
        LocalDateTime checkOut = LocalDateTime.of(checkIn.toLocalDate().plusDays(lengthOfBooking), LocalTime.of(CHECK_OUT_HOUR, CHECK_OUT_MINUTE));

        Guest guest = returnMockGuest();
        Room room = returnMockRoom();

        return new Booking(room, guest, checkIn, checkOut);
    }

    /**
     * Adds n fake {@link Booking}'s to a {@link List} of {@link Booking}'s.
     * @param bookings A {@link List} of {@link Booking}'s to witch the fake bookings will be added.
     * @param n The number of fake {@link Booking}'s to create.
     */
    public static void mockBookings(List<Booking> bookings, Integer n) {
        log.trace("Mocking {} bookings", n);
        for(Integer i = 0; i < n; i++){
            bookings.add(returnMockBooking());
        }
    }

    /**
     * Adds the default numbers of fake {@link Booking}'s to a {@link List} of {@link Booking}'s.
     * @param bookings A {@link List} of {@link Booking}'s to witch the fake bookings will be added.
     */
    public static void mockBookings(List<Booking> bookings) {
        mockBookings(bookings, DEFAULT_NUMBER);
    }

    //TODO mock reviews.
}