package fr.univ.engine.render.texture;

import javafx.scene.image.Image;

/**
 * Abstract base class for texture objects.
 */
public abstract class Texture {
    /**
     * The width at which to render this texture.
     */
    private final double width;
    /**
     * The height at which to render this texture.
     */
    private final double height;
    /**
     * Z-index used to tell if this texture should be behind or in front of another.
     */
    private int zIndex;

    protected Texture(double width, double height) {
        this.width = width;
        this.height = height;
        this.zIndex = 0;
    }

    /**
     * @return the image to render for this texture.
     */
    public abstract Image getImage();

    /**
     * @return the width at which to render this texture.
     */
    public double width() {
        return this.width;
    }

    /**
     * @return the height at which to render this texture.
     */
    public double height() {
        return this.height;
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
