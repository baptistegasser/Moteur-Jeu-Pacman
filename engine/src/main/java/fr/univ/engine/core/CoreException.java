package fr.univ.engine.core;

/**
 * Exceptions related to the Core package usage.
 */
public class CoreException extends RuntimeException {
    /**
     * Create an exception not based on a previous exception.
     *
     * @param message the message of the exception
     */
    public CoreException(String message) {
        super(message);
    }

    /**
     * Create an exception based on a previous exception.
     *
     * @param message the message of the exception
     * @param cause the previous exception that triggered the creation of this exception
     */
    public CoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
