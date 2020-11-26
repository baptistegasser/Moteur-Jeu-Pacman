package fr.univ.engine.utils;

import fr.univ.engine.utils.UtilException;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * A texture loader attempting to find texture inside a dir from the app's resources.
 * Implement a cache to prevent multiple read of the same file.
 *
 * @implNote this implementation use a simple cache, this might create memory usage trouble
 * for games with consequent amount of textures.
 */
public class CachedResourcesLoader {
    /**
     * The folder to search inside the resources.
     */
    private final String folder;
    /**
     * The cache mapping keys to loaded textures.
     */
    private final HashMap<String, Image> cachedImages;

    private final HashMap<String, Media> cachedMedia;

    /**
     * @param folder the resources folder to use as root to locate textures.
     */
    public CachedResourcesLoader(String folder) {
        this.folder = folder;
        this.cachedImages = new HashMap<>();
        this.cachedMedia = new HashMap<>();
    }

    /**
     * Load an image from the cache or file system.
     *
     * @param filePath the path to the image file
     * @return the image instance or null if not found/loaded
     */
    public Image getTexture(String filePath) {
        // Don't even bother with null values
        if (filePath == null) {
            return null;
        }

        // If texture is cached return it
        if (cachedImages.containsKey(filePath)) {
            return cachedImages.get(filePath);
        }

        try {
            Image texture = tryLoadImage(filePath);
            cachedImages.put(filePath, texture);
            return texture;
        } catch (UtilException e) {
            // We failed to load the image
            // Log the error, cache the null value and return null
            System.err.println("Failed to load texture:");
            System.err.println(e.getMessage());
            cachedImages.put(filePath, null);
            return null;
        }
    }

    /**
     * Attempt to load an image from a file.
     *
     * @param filePath the path to the image file
     * @return a {@link javafx.scene.image.Image} instance containing the image data
     * @throws IllegalArgumentException if the path is not valid or the image fail to load
     */
    public Image tryLoadImage(String filePath) throws UtilException {
        // Get InputStream, assert not null
        InputStream is = getClass().getClassLoader().getResourceAsStream(folder + filePath);
        if (is == null) {
            throw new UtilException(String.format("No file found at '%s'", folder + filePath));
        }

        // Load texture, cache it, return it
        try {
            return new Image(is);
        } catch (Exception e) {
            throw new UtilException(String.format("Error instantiating javafx.scene.image.Image from file '%s'", folder + filePath), e);
        }
    }

    /**
     * Cache the given list of images by loading them once.
     *
     * @param paths the list of texture to load
     */
    public void precacheImages(List<String> paths) {
        for (String filePath : paths) {
            getTexture(filePath);
        }
    }
}
