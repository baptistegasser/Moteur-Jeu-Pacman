package fr.univ.engine.render.texture;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A texture that can be shown on screen.
 */
public class Texture extends ImageView {
    /**
     * Z-index used to tell if this texture should be behind or in front of another.
     */
    private int zIndex;

    /**
     * Creates a new texture for the given image.
     */
    public Texture(Image image) {
        super(image);
        this.zIndex = 0;
    }

    /**
     * Update the z-index position.
     *
     * @param zIndex the new z-index value
     */
    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    /**
     * @return the z-index value of this texture
     */
    public int zIndex() {
        return zIndex;
    }
}
