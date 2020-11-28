package fr.univ.engine.assets;

/**
 * Exception related to the handling of assets.
 */
public class AssetException extends RuntimeException {
    public AssetException(String message) {
        super(message);
    }

    public AssetException(String message, Throwable cause) {
        super(message, cause);
    }
}
