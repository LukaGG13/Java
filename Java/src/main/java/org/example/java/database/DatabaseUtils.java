package org.example.java.database;

import org.example.java.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


public class DatabaseUtils {

    private static final String SELECT_STUDENTS_QUERY = "SELECT * FROM STUDENTI";
    private static final String INSERT_NEW_STUDENT_QUERY = "INSERT INTO STUDENTI (IME, PREZIME, JMBAG, DATUM_RODJENJA) VALUES (?, ?, ?, ?)";

    private static final String STUDENT_COLUMN_NAME_ID = "ID";
    private static final String STUDENT_COLUMN_NAME_FIRST_NAME = "IME";
    private static final String STUDENT_COLUMN_NAME_LAST_NAME = "PREZIME";
    private static final String STUDENT_COLUMN_NAME_JMBAG = "JMBAG";
    private static final String STUDENT_COLUMN_NAME_DATE_OF_BIRTH = "DATUM_RODJENJA";
    private static final String DATABASE_FILE = "src/main/resources/database.properties";

    private static final Logger log = LoggerFactory.getLogger(DatabaseUtils.class);

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

    public static void createTables() throws DatabaseException, IOException, SQLException {
        try (var connection = createConnection()) {
            log.debug("Creating table users");
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS users (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          ime VARCHAR(50) NOT NULL,
                          age INT NOT NULL
                    );                    
                    """);
            preparedStatement.executeUpdate();
        }
    }
    /*
    public static List<Student> getAllStudents() throws DatabaseException, IOException {

        List<Student> students = new ArrayList<>();
        Connection conn = createConnection();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_STUDENTS_QUERY);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                Integer id = rs.getInt(STUDENT_COLUMN_NAME_ID);
                String firstName = rs.getString(STUDENT_COLUMN_NAME_FIRST_NAME);
                String lastName = rs.getString(STUDENT_COLUMN_NAME_LAST_NAME);
                String jmbag = rs.getString(STUDENT_COLUMN_NAME_JMBAG);
                LocalDate dateOfBirth = rs.getDate(STUDENT_COLUMN_NAME_DATE_OF_BIRTH).toLocalDate();

                Student student = new Student(id, firstName, lastName, jmbag, dateOfBirth);
                students.add(student);
            }
        }
        catch(SQLException e) {
            throw new DatabaseException(e);
        }

        closeConnection(conn);

        return students;
    }

    public static void saveNewStudent(Student student) throws DatabaseException, IOException {
        Connection conn = DatabaseUtils.createConnection();

        try {
            PreparedStatement pstmt = conn.prepareStatement(INSERT_NEW_STUDENT_QUERY);
            pstmt.setString(1, student.getIme());
            pstmt.setString(2, student.getPrezime());
            pstmt.setString(3, student.getJmbag());
            pstmt.setDate(4, Date.valueOf(student.getDatumRodjenja()));
            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new DatabaseException(e);
        }

        DatabaseUtils.closeConnection(conn);
    }
    */
}
