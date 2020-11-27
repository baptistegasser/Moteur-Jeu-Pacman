package fr.univ.engine.render.entity;

import fr.univ.engine.math.Point;
import fr.univ.engine.render.texture.Texture;

/**
 * A Render entity is an entity able to be rendered.
 * It provide informations in the form of a {@link RenderObject} that contain necessary
 * elements to render the {@code RenderEntity} instance.
 */
public interface RenderEntity {
    /**
     * This function is called before every render of the object, thus once per frame.
     * Provide a default update implementation that does nothing.
     */
    default void update() {
    }

    /**
     * @return the texture to render for this object
     */
    Texture getTexture();

    /**
     * @return the position of this object (centered)
     */
    Point pos();
}
