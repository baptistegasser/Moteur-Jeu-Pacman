package fr.univ.engine.render.texture;

import javafx.scene.image.Image;

/**
 * A texture that can be shown on screen.
 */
public class Texture {
    private final double width;
    private final double height;
    /**
     * Z-index used to tell if this texture should be behind or in front of another.
     */
    private int zIndex;
    /**
     * The image of this texture.
     */
    private final Image image;

    /**
     * Creates a new texture for the given image.
     */
    public Texture(double width, double height, Image image) {
        this.width = width;
        this.height = height;
        this.zIndex = 0;
        this.image = image;
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

    public Image getImage() {
        return image;
    }

    public double width() {
        return this.width;
    }

    public double height() {
        return this.height;
    }
}
