package fr.univ.engine.render.texture;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A texture that can be shown on screen.
 */
public class Texture extends ImageView {
    /**
     * Creates a new texture for the given image.
     */
    Texture(Image image) {
        super(image);
    }
}
