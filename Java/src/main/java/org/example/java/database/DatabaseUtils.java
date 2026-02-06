package org.example.java.database;

import org.example.java.entity.guest.Guest;
import org.example.java.entity.room.Room;
import org.example.java.entity.user.User;
import org.example.java.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;


public class DatabaseUtils {

    private DatabaseUtils(){}

    private static final String USER_ID = "ID";
    private static final String USER_NAME_COLUMN = "IME";
    private static final String USER_AGE_COLUMN = "AGE";
    private static final String SELECT_USERS = "SELECT %s, %s, %s FROM users".formatted(USER_ID, USER_NAME_COLUMN, USER_AGE_COLUMN);
    private static final String INSERT_USER = "INSERT INTO users (%s, %s) VALUES(?,?)".formatted(USER_NAME_COLUMN, USER_AGE_COLUMN);
    private static final String UPDATE_USER = "UPDATE users SET %s = ?, %s = ? WHERE %s = ?;".formatted(USER_NAME_COLUMN, USER_AGE_COLUMN, USER_ID);
    private static final String DELETE_USER = "DELETE FROM users WHERE %s = ?;".formatted(USER_ID);

    private static final String ROOM_ID = "ID";
    private static final String ROOM_NUMBER_OF_BEDS = "num_of_beds";
    private static final String ROOM_SIZE_IN_SQUARE_METERS = "size_in_sqr_m";
    private static final String ROOM_PRICE_PER_NIGHT = "price_per_night";
    private static final String ROOM_DISTANCE_FROM_CITY_CENTER = "distance_from_city_center";
    private static final String ROOM_DISTANCE_FROM_BEACH = "distance_from_beach";

    private static final String SELECT_ROOMS =
            "SELECT %s, %s, %s, %s, %s, %s FROM ROOMS"
                    .formatted(
                            ROOM_ID,
                            ROOM_NUMBER_OF_BEDS,
                            ROOM_SIZE_IN_SQUARE_METERS,
                            ROOM_PRICE_PER_NIGHT,
                            ROOM_DISTANCE_FROM_CITY_CENTER,
                            ROOM_DISTANCE_FROM_BEACH
                    );
    private static final String INSERT_ROOM =
            "INSERT INTO ROOMS (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)"
                    .formatted(
                            ROOM_NUMBER_OF_BEDS,
                            ROOM_SIZE_IN_SQUARE_METERS,
                            ROOM_PRICE_PER_NIGHT,
                            ROOM_DISTANCE_FROM_CITY_CENTER,
                            ROOM_DISTANCE_FROM_BEACH
                    );

    private static final Logger log = LoggerFactory.getLogger(DatabaseUtils.class);
    private static final String DATABASE_FILE = "src/main/resources/database.properties";

    public static Connection createConnection() throws DatabaseException, IOException {
        try (var reader = new FileReader(DATABASE_FILE)) {

            var properties = new Properties();
            properties.load(reader);

            var url  = properties.getProperty("url");
            var user = properties.getProperty("username");
            var pass = properties.getProperty("password");

            return DriverManager.getConnection(url, user, pass);
        }
        catch(SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public static void closeConnection(Connection conn) throws DatabaseException {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }


    public static List<User> getAllUsers() throws DatabaseException, IOException {
        log.info("Fetching users from db");
        List<User> users = new ArrayList<>();

        try (   var conn = createConnection();
                var preparedStatement = conn.prepareStatement(SELECT_USERS);
                var rs = preparedStatement.executeQuery()
        ) {
            while(rs.next()) {
                UUID id = UUID.fromString(rs.getString(USER_ID));
                Integer age = rs.getInt(USER_AGE_COLUMN);
                String name = rs.getString(USER_NAME_COLUMN);

                //TODO(skip) posebna tablica za admin i za guest i join i add uuid
                log.debug("creating user with uuid " + id );
                Guest student = new Guest(id, name, age);
                users.add(student);
            }
        }
        catch(SQLException e) {
            throw new DatabaseException(e);
        }

        return users;
    }

    public static void saveNewUser(User user) throws DatabaseException, IOException {
        log.info("Adding users into db");

        try (   var conn = createConnection();
                var pstmt = conn.prepareStatement(INSERT_USER)){
            //TODO: insert u guest ili admin table
            pstmt.setString(1, user.getName());
            pstmt.setInt(2, user.getAge());
            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public static void updateUser(User user) throws DatabaseException, IOException {
        log.info("Updating user {} into db", user);

        try (   var conn = createConnection();
                //TODO: fix
                var pstmt = conn.prepareStatement(UPDATE_USER)){
            pstmt.setString(1, user.getName());
            pstmt.setInt(2, user.getAge());
            pstmt.setString(3, user.getId().toString());
            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public static void deleteUser(User user) throws DatabaseException, IOException {
        log.info("Deleting user {} into db", user);

        try (   var conn = createConnection();
                var pstmt = conn.prepareStatement(DELETE_USER)){
            pstmt.setString(1, user.getId().toString());
            log.debug("delete to string" + pstmt.toString());
            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public static List<Room> getAllRooms() throws DatabaseException, IOException {
        List<Room> rooms = new ArrayList<>();

           try (    var conn = createConnection();
                    var pstm = conn.prepareStatement(SELECT_ROOMS);
                    var rs = pstm.executeQuery()
           ){
               while (rs.next()) {

                   //TODO all fields and switch string with string constant variables defined above
                   var id = rs.getInt(ROOM_ID);
                   var numberOfBeds = rs.getInt(ROOM_NUMBER_OF_BEDS);
                   var sizeInSqrM = rs.getInt(ROOM_SIZE_IN_SQUARE_METERS);
                   var pricePerNight = rs.getBigDecimal(ROOM_PRICE_PER_NIGHT);
                   var distanceFromCityCenter = rs.getBigDecimal(ROOM_DISTANCE_FROM_CITY_CENTER);
                   var distanceFromBeach = rs.getBigDecimal(ROOM_DISTANCE_FROM_BEACH);

                   Room room = new Room.RoomBuilder(numberOfBeds, pricePerNight)
                           .sizeInSqrM(sizeInSqrM)
                           .distanceFromCityCenter(distanceFromCityCenter)
                           .distanceFromBeach(distanceFromBeach)
                           .build();
                   rooms.add(room);
               }
               return rooms;
           } catch (SQLException e) {
               throw new DatabaseException(e);
           }
    }

    public static void saveNewRoom(Room room) throws DatabaseException, IOException {
        log.info("Adding room into db");

        try (   var conn = createConnection();
                var pstmt = conn.prepareStatement(INSERT_ROOM)
        ){
            pstmt.setInt(1, room.getNumOfBeds());
            pstmt.setInt(2, room.getSizeInSqrM());
            pstmt.setBigDecimal(3, room.getPricePerNight());
            pstmt.setBigDecimal(4, room.getDistanceFromCityCenter());
            pstmt.setBigDecimal(5, room.getDistanceFromBeach());

            pstmt.executeUpdate();
        } catch(SQLException e) {
            throw new DatabaseException(e);
        }
    }
}