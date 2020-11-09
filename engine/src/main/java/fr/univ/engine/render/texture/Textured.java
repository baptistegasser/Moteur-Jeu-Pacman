package fr.univ.engine.render.texture;

/**
 * An object implementing this interface can be rendered on screen.
 * This interface define a single method allowing to retrieve which texture
 * to use to display an object.
 * As this is a method a texture is valid for an object only at a given time T.
 */
@FunctionalInterface
public interface Textured {
    /**
     * @return a unique identifier allowing to detect which texture should be used.
     */
    String getTextureName();
}
