package com.codeforall.online.gladinator.exceptions;

/**
 * Exception thrown when the application fails to obtain
 * a valid response from the AI integration layer.
 */
public class AiIntegrationException extends RuntimeException {

    /**
     * Creates a new exception with the given message.
     *
     * @param message the exception message
     */
    public AiIntegrationException(String message) {
        super(message);
    }

    /**
     * Creates a new exception with the given message and cause.
     *
     * @param message the exception message
     * @param cause the original cause of the failure
     */
    public AiIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
