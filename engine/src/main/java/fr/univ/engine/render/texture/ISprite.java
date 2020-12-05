package fr.univ.engine.render.texture;

import javafx.scene.image.Image;

/**
 * Represent an image that can be drawn on screen.
 */
@FunctionalInterface
public interface ISprite {
    /**
     * @return the image that should be displayed for this sprite.
     */
    Image getImage();

    /**
     * A sprite that return non image.
     * Can be used to have a sprite that don't render anything,
     * but still exist as a non null instance.
     */
    ISprite NONE = () -> null;
}
