package fr.univ.engine.utils;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.text.Font;

import java.io.File;
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

    /**
     * The cache mapping keys to load media
     */
    private final HashMap<String, Media> cachedMedia;

    /**
     * The cache mapping keys to load media
     */
    private final HashMap<String, Font> cachedFont;

    /**
     * @param folder the resources folder to use as root to locate textures.
     */
    public CachedResourcesLoader(String folder) {
        this.folder = folder;
        this.cachedImages = new HashMap<>();
        this.cachedMedia = new HashMap<>();
        this.cachedFont = new HashMap<>();
    }

    /**
     * Load an image from the cache or file system.
     *
     * @param filePath the path to the image file
     * @return the image instance or null if not found/loaded
     */
    public Image getImage(String filePath) {
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
        } catch (UtilsException e) {
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
    private Image tryLoadImage(String filePath) throws UtilsException {
        // Get InputStream, assert not null
        InputStream is = getClass().getClassLoader().getResourceAsStream(folder + filePath);
        if (is == null) {
            throw new UtilsException(String.format("No file found at '%s'", folder + filePath));
        }

        // Load texture, cache it, return it
        try {
            return new Image(is);
        } catch (Exception e) {
            throw new UtilsException(String.format("Error instantiating javafx.scene.image.Image from file '%s'", folder + filePath), e);
        }
    }

    /**
     * Cache the given list of images by loading them once.
     *
     * @param paths the list of texture to load
     */
    public void precacheImages(List<String> paths) {
        for (String filePath : paths) {
            getImage(filePath);
        }
    }



    /**
     * Load a media from the cache or file system.
     *
     * @param filePath the path to the media file
     * @return the media instance or null if not found/loaded
     */
    public Media getMedia(String filePath) {
        // Don't even bother with null values
        if (filePath == null) {
            return null;
        }

        // If texture is cached return it
        if (cachedMedia.containsKey(filePath)) {
            return cachedMedia.get(filePath);
        }

        try {
            Media media = tryLoadMedia(filePath);
            cachedMedia.put(filePath, media);
            return media;
        } catch (UtilsException e) {
            // We failed to load the sound
            // Log the error, cache the null value and return null
            System.err.println("Failed to load media:");
            System.err.println(e.getMessage());
            cachedMedia.put(filePath, null);
            return null;
        }
    }

    /**
     * Attempt to load a media from a file.
     *
     * @param filePath the path to the media file
     * @return a {@link javafx.scene.media.Media} instance containing the media data
     * @throws IllegalArgumentException if the path is not valid or the media fail to load
     */
    private Media tryLoadMedia(String filePath) throws UtilsException {
        // Get InputStream, assert not null
        String file = new File(folder + filePath).toURI().toString();
        if (file == null) {
            throw new UtilsException(String.format("No file found at '%s'", folder + filePath));
        }

        // Load media, cache it, return it
        try {
            return new Media(file);
        } catch (Exception e) {
            throw new UtilsException(String.format("Error instantiating javafx.scene.media.Media from file '%s'", folder + filePath), e);
        }
    }

    /**
     * Cache the given list of medias by loading them once.
     *
     * @param paths the list of medias to load
     */
    public void precacheSounds(List<String> paths) {
        for (String filePath : paths) {
            getMedia(filePath);
        }
    }

    /**
     * Load a font from the cache or file system.
     *
     * @param filePath the path to the font file
     * @return the font instance or null if not found/loaded
     */
    public Font getFont(String filePath) {
        // Don't even bother with null values
        if (filePath == null) {
            return null;
        }

        // If texture is cached return it
        if (cachedFont.containsKey(filePath)) {
            return cachedFont.get(filePath);
        }

        try {
            Font font = tryLoadFont(filePath);
            cachedFont.put(filePath, font);
            return font;
        } catch (UtilsException e) {
            // We failed to load the image
            // Log the error, cache the null value and return null
            System.err.println("Failed to load font:");
            System.err.println(e.getMessage());
            cachedImages.put(filePath, null);
            return null;
        }
    }

    /**
     * Attempt to load a font from a file.
     *
     * @param filePath the path to the font file
     * @return a {@link javafx.scene.text.Font} instance containing the font data
     * @throws IllegalArgumentException if the path is not valid or the font fail to load
     */
    private Font tryLoadFont(String filePath) throws UtilsException {
        // Get InputStream, assert not null
        InputStream is = getClass().getClassLoader().getResourceAsStream(folder + filePath);
        if (is == null) {
            throw new UtilsException(String.format("No file found at '%s'", folder + filePath));
        }

        // Load media, cache it, return it
        try {
            return Font.loadFont(is, 1);
        } catch (Exception e) {
            throw new UtilsException(String.format("Error instantiating javafx.scene.text.Font from file '%s'", folder + filePath), e);
        }
    }

    /**
     * Cache the given list of fonts by loading them once.
     *
     * @param paths the list of fonts to load
     */
    public void precacheFonts(List<String> paths) {
        for (String filePath : paths) {
            getFont(filePath);
        }
    }
}