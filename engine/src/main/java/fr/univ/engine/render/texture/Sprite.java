package fr.univ.engine.render.texture;

import javafx.scene.image.Image;

/**
 * A simple sprite made from one image that never change.
 */
public class Sprite implements ISprite {
    /**
     * The image of this sprite.
     */
    private final Image image;

    public Sprite(Image image) {
        this.image = image;
    }

    @Override
    public Image getImage() {
        return this.image;
    }
}
