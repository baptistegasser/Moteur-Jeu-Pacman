package fr.univ.engine.physic;
/**
 * Exceptions related to the physic package usage.
 */
public class PhysicException extends RuntimeException {
    public PhysicException(String message) {
        super(message);
    }

    public PhysicException(String message, Throwable cause) {
        super(message, cause);
    }
}
