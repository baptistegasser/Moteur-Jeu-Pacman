package fr.univ.engine.utils;

/**
 * Exceptions related to the  package usage.
 */ // util exception
class UtilsException extends RuntimeException {
    public UtilsException(String message) {
        super(message);
    }

    public UtilsException(String message, Throwable cause) {
        super(message, cause);
    }
}
