package fr.univ.engine.physic;
/**
 * Exceptions related to the physic package usage.
 */
public class PhysicException extends RuntimeException {
    /**
     * A physic exception with an error message.
     *
     * @param message the message.
     */
    public PhysicException(String message) {
        super(message);
    }

    /**
     * A physic exception with an error message.
     *
     * @param message the message.
     * @param cause the cause of this exception.
     */
    public PhysicException(String message, Throwable cause) {
        super(message, cause);
    }
}
