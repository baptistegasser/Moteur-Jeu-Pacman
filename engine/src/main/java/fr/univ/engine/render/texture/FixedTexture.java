package fr.univ.engine.render.texture;

import javafx.scene.image.Image;

/**
 * A simple texture made from one image that never change.
 */
public class FixedTexture extends Texture {
    /**
     * The image of this texture.
     */
    private final Image image;

    public FixedTexture(double width, double height, Image image) {
        super(width, height);
        this.image = image;
    }

    @Override
    public Image getImage() {
        return this.image;
    }
}
