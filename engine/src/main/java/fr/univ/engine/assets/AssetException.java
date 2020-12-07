package fr.univ.engine.assets;

/**
 * Exception related to the handling of assets.
 */
public class AssetException extends RuntimeException {
    /**
     * Throw a message
     * @param message The message
     */
    public AssetException(String message) {
        super(message);
    }

    /**
     * Throw a mesage and its cause
     * @param message The message
     * @param cause The cause
     */
    public AssetException(String message, Throwable cause) {
        super(message, cause);
    }
}
