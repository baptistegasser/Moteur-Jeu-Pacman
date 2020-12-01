package fr.univ.engine.render;

/**
 * Exceptions related to the render package usage.
 */
public class RenderException extends RuntimeException {
    /**
     * A render exception with an error message.
     *
     * @param message the message.
     */
    public RenderException(String message) {
        super(message);
    }

    /**
     * A render exception with an error message.
     *
     * @param message the message.
     * @param cause the cause of this exception.
     */
    public RenderException(String message, Throwable cause) {
        super(message, cause);
    }
}
