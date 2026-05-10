package com.codeforall.online.gladinator.exceptions;

/**
 * Exception thrown when the current session state does not allow
 * the requested game operation.
 */
public class InvalidGameStateException extends RuntimeException {

    /**
     * Creates a new exception with the given message.
     *
     * @param message the exception message
     */
    public InvalidGameStateException(String message) {
        super(message);
    }
}
