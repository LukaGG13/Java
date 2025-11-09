package org.example.entity;

/**
 * Class to be thrown when user enter incorrect input.
 *
 * @version 1
 * @author luka
 */
public class IncorrectInputException extends Exception {
    public IncorrectInputException() {
    }

    public IncorrectInputException(String message) {
        super(message);
    }

    public IncorrectInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectInputException(Throwable cause) {
        super(cause);
    }

    public IncorrectInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String toString() {
        return "IncorrectInput{}";
    }
}
