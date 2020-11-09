package fr.univ.engine.render.texture;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;

/**
 * A texture loader attempting to find texture inside a dir from the app's resources.
 * Implement a cache to prevent multiple read of the same file.
 *
 * @implNote this implementation use a simple cache, this might create memory usage trouble
 * for games with consequent amount of textures.
 */
public class CachedResourcesLoader implements TextureLoader {
    /**
     * The folder to search inside the resources.
     */
    private final String folder;
    /**
     * The cache mapping keys to loaded textures.
     */
    private final HashMap<String, Image> cache;

    /**
     * @param folder the resources folder to use as root to locate textures.
     */
    public CachedResourcesLoader(String folder) {
        this.folder = folder;
        this.cache = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     *
     * @param filePath the relative path of the texture file inside the root {@link #folder}.
     */
    @Override
    public Image getTexture(String filePath) throws IllegalArgumentException {
        // If texture is cached return it
        if (cache.containsKey(filePath)) {
            return cache.get(filePath);
        }

        // Get InputStream, assert not null
        InputStream is = getClass().getClassLoader().getResourceAsStream(folder + filePath);
        if (is == null) {
            throw new IllegalArgumentException(String.format("No file found at '%s'", folder + filePath));
        }

        // Load texture, cache it, return it
        try {
            Image texture = new Image(is);
            cache.put(filePath, texture);
            return texture;
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Failed to load image from '%s'", folder + filePath), e);
        }
    }
}
