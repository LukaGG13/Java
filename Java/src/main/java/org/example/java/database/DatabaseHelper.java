package org.example.java.database;

import org.example.java.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

import static org.example.java.database.DatabaseUtils.createConnection;

public class DatabaseHelper {
    private DatabaseHelper() {}
    private static final Logger log = LoggerFactory.getLogger(DatabaseHelper.class);

    public static void createTables() throws DatabaseException, IOException, SQLException {
        try (var connection = createConnection()) {
            log.debug("Creating table users");
            var preparedStatement = connection.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS users (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          ime VARCHAR(50) NOT NULL,
                          age INT NOT NULL
                    );                    
                    """);
            preparedStatement.executeUpdate();

            connection.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS rooms (
                        id INT AUTO_INCREMENT PRIMARY KEY,           
                        num_of_beds INT NOT NULL,                    
                        size_in_sqr_m INT NOT NULL,                 
                        price_per_night DECIMAL(10, 2) NOT NULL,     
                        distance_from_city_center DECIMAL(10, 2) NOT NULL,  
                        distance_from_beach DECIMAL(10, 2) NOT NULL  
                    );
                    """).executeUpdate();

            connection.prepareStatement("""
                        CREATE TABLE IF NOT EXISTS amenities (
                            id INT AUTO_INCREMENT PRIMARY KEY,          
                            name VARCHAR(50) NOT NULL UNIQUE 
                        );
                    """).executeUpdate();

            /*
            connection.prepareStatement("""
                    INSERT IGNORE INTO amenities (name)
                    VALUES
                        ('GYM'),
                        ('WIFI'),
                        ('POOL'),
                        ('PARKING'),
                        ('SPA'),
                        ('BREAKFAST');
                    """).executeUpdate();

             */
            connection.prepareStatement("""
                        CREATE TABLE IF NOT EXISTS room_amenities (
                            room_id INT NOT NULL,
                            amenity_id INT NOT NULL,
                            PRIMARY KEY (room_id, amenity_id),
                            FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE,
                            FOREIGN KEY (amenity_id) REFERENCES amenities(id) ON DELETE CASCADE
                        );
                    """).executeUpdate();
        }
    }
}
