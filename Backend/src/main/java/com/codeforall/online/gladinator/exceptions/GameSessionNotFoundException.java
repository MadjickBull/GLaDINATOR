package com.codeforall.online.gladinator.exceptions;

/**
 * Exception thrown when a game session cannot be found.
 */
public class GameSessionNotFoundException extends RuntimeException {

    /**
     * Creates a new exception with the given message.
     *
     * @param message the exception message
     */
    public GameSessionNotFoundException(String message) {
        super(message);
    }
}
