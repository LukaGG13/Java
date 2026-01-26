package org.example.java.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DatabaseException Tests")
class DatabaseExceptionTest {

    @Test
    @DisplayName("Should create DatabaseException with message")
    void testCreateDatabaseExceptionWithMessage() {
        String message = "Database connection failed";
        DatabaseException exception = new DatabaseException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("Should create DatabaseException with cause")
    void testCreateDatabaseExceptionWithCause() {
        SQLException cause = new SQLException("Connection error");
        DatabaseException exception = new DatabaseException(cause);

        assertEquals(cause, exception.getCause());
        assertTrue(exception instanceof Exception);
    }

    @Test
    @DisplayName("Should create DatabaseException with message and cause")
    void testCreateDatabaseExceptionWithMessageAndCause() {
        SQLException cause = new SQLException("SQL error");
        String message = "Database operation failed";
        DatabaseException exception = new DatabaseException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("Should be throwable")
    void testDatabaseExceptionThrowable() {
        assertThrows(DatabaseException.class, () -> {
            throw new DatabaseException("Test exception");
        });
    }

    @Test
    @DisplayName("Should be instance of Exception")
    void testDatabaseExceptionIsException() {
        DatabaseException exception = new DatabaseException("Test");
        assertTrue(exception instanceof Exception);
    }

    @Test
    @DisplayName("Should preserve stack trace")
    void testDatabaseExceptionStackTrace() {
        DatabaseException exception = new DatabaseException("Test error");
        StackTraceElement[] stackTrace = exception.getStackTrace();

        assertNotNull(stackTrace);
        assertTrue(stackTrace.length > 0);
    }

    @Test
    @DisplayName("Should wrap SQLException")
    void testDatabaseExceptionWrapsSQL() {
        SQLException sqlException = new SQLException("SQL error details");
        DatabaseException exception = new DatabaseException(sqlException);

        assertSame(sqlException, exception.getCause());
    }

    @Test
    @DisplayName("Should have meaningful string representation")
    void testDatabaseExceptionToString() {
        DatabaseException exception = new DatabaseException("Connection failed");
        String toString = exception.toString();

        assertTrue(toString.contains("DatabaseException"));
    }

    @Test
    @DisplayName("Should catch as Exception")
    void testCatchAsException() {
        assertThrows(Exception.class, () -> {
            throw new DatabaseException("Test");
        });
    }

    @Test
    @DisplayName("Multiple DatabaseExceptions should be independent")
    void testMultipleDatabaseExceptions() {
        DatabaseException exception1 = new DatabaseException("Error 1");
        DatabaseException exception2 = new DatabaseException("Error 2");

        assertNotEquals(exception1.getMessage(), exception2.getMessage());
    }

    @Test
    @DisplayName("Should maintain exception chain")
    void testExceptionChain() {
        SQLException sql = new SQLException("SQL error");
        DatabaseException exception = new DatabaseException(sql);

        assertTrue(exception.getCause() instanceof SQLException);
    }
}
