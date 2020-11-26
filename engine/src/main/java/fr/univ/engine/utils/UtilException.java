package fr.univ.engine.utils;

/**
 * Exceptions related to the render package usage.
 */
class UtilException extends RuntimeException {
    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }
}
