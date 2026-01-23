package org.example.java;

import org.example.java.database.DatabaseHelper;
import org.example.java.database.DatabaseUtils;
import org.example.java.entity.guest.Guest;
import org.example.java.entity.room.Room;
import org.example.java.entity.user.User;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseUtilsTest {

    private Connection conn;

    @BeforeEach
    void setupDatabase() throws Exception {
        conn = DatabaseUtils.createConnection();

           DatabaseHelper.createTables();
        //try (Statement stmt = conn.createStatement()) {

            /*
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    IME VARCHAR(100),
                    AGE INT
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS ROOMS (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    num_of_beds INT,
                    size_in_sqr_m INT,
                    price_per_night DECIMAL,
                    distance_from_city_center DECIMAL,
                    distance_from_beach DECIMAL
                )
            """);

        }
             */

    }

    @AfterEach
    void cleanup() throws Exception {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM users");
            stmt.execute("DELETE FROM ROOMS");
        }
        conn.close();
    }

    // ---------------- USERS ----------------

    @Test
    void saveNewUser_and_getAllUsers() throws Exception {
        User user = new Guest("Ana", 23);

        DatabaseUtils.saveNewUser(user);

        List<User> users = DatabaseUtils.getAllUsers();

        assertEquals(1, users.size());
        assertEquals("Ana", users.get(0).getName());
        assertEquals(23, users.get(0).getAge());
    }

    // ---------------- ROOMS ----------------

    @Test
    void saveNewRoom_and_getAllRooms() throws Exception {
        Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100))
                .sizeInSqrM(25)
                .distanceFromCityCenter(BigDecimal.valueOf(3))
                .distanceFromBeach(BigDecimal.valueOf(1))
                .build();

        DatabaseUtils.saveNewRoom(room);

        List<Room> rooms = DatabaseUtils.getAllRooms();

        assertEquals(1, rooms.size());
        assertEquals(2, rooms.getFirst().getNumOfBeds());
        assertEquals(BigDecimal.valueOf(100).setScale(2, RoundingMode.CEILING), rooms.getFirst().getPricePerNight());
    }
}
