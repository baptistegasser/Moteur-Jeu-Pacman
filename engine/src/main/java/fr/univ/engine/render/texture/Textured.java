package fr.univ.engine.render.texture;

/**
 * Every object that use a texture should implement this interface.
 */
@FunctionalInterface
public interface Textured {
    /**
     * @return a unique identifier allowing to detect which texture should be used.
     */
    String getTextureName();
}
