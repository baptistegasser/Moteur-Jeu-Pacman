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
    private Image image;

    /**
     * The texture can be an animation
     */
    private Animation animation;

    /**
     * Creates a new texture for the given image without animation .
     *
     * @param width the width of this texture.
     * @param height the height of this texture.
     * @param image the image used by this texture.
     */
    public Texture(double width, double height, Image image) {
        this.width = width;
        this.height = height;
        this.zIndex = 0;
        this.image = image;
        this.animation = null;
    }

    /**
     * Creates a new texture with animation.
     *
     * @param width the width of this texture.
     * @param height the height of this texture.
     * @param animation the images animation.
     */
    public Texture(double width, double height, Animation animation) {
        this.width = width;
        this.height = height;
        this.zIndex = 0;
        this.animation = animation;
        this.image = animation.getCurrentImage();
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

    /**
     * get the image depending on the animation
     * @return the image
     */
    public Image getImage() {
        if (haveAnimation()) {
            animation.nextFrame();
            image = animation.getCurrentImage();
        }
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public double width() {
        return this.width;
    }

    public double height() {
        return this.height;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public boolean haveAnimation() {
        return animation != null;
    }

    @Override
    public String toString() {
        return "Texture{" +
                "zIndex=" + zIndex +
                ", image=" + image.getUrl() +
                '}';
    }
}
