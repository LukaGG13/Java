package org.example.java.database;

import org.example.java.entity.guest.Guest;
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


public class DatabaseUtils {

    private static final String SELECT_USERS = "SELECT ime, age FROM users";
    private static final String INSERT_USER = "INSERT INTO users (IME, AGE) VALUES(?,?)";
    private static final String USER_NAME_COLUMN = "IME";
    private static final String USER_AGE_COLUMN = "AGE";

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
        Connection conn = createConnection();

        try (var preparedStatement = conn.prepareStatement(SELECT_USERS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                Integer age = rs.getInt(USER_AGE_COLUMN);
                String name = rs.getString(USER_NAME_COLUMN);

                //TODO posebna tablica za admin i za guest
                Guest student = new Guest(name, age);
                users.add(student);
            }
        }
        catch(SQLException e) {
            throw new DatabaseException(e);
        }

        closeConnection(conn);

        return users;
    }

    public static void saveNewUser(User user) throws DatabaseException, IOException {
        log.info("Adding users into db");
        Connection conn = DatabaseUtils.createConnection();

        try (var pstmt = conn.prepareStatement(INSERT_USER)){
            pstmt.setString(1, user.getName());
            pstmt.setInt(2, user.getAge());
            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new DatabaseException(e);
        }

        DatabaseUtils.closeConnection(conn);
    }

}
