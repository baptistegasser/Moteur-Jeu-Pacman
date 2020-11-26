package fr.univ.engine.render;

/**
 * Exceptions related to the  package usage.
 */
class RenderException extends RuntimeException {
    public RenderException(String message) {
        super(message);
    }

    public RenderException(String message, Throwable cause) {
        super(message, cause);
    }
}
