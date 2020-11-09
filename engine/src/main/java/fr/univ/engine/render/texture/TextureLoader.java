package fr.univ.engine.render.texture;

import javafx.scene.image.Image;

/**
 * A texture loader is an object that is responsible of loading textures.
 * Given the name of a texture, a texture loader should attempt to locate
 * and load a texture.
 * A texture is represented as a {@link javafx.scene.image.Image} instance.
 */
@FunctionalInterface
public interface TextureLoader {
    /**
     * Locate and load a texture matching the given key.
     *
     * @param key the unique key identifying a texture
     * @return a texture represented as a {@link javafx.scene.image.Image} instance
     * @throws IllegalArgumentException if the method fail to load a texture for the given key
     */
    Image getTexture(String key) throws IllegalArgumentException;
}
