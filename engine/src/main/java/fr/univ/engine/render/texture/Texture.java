package fr.univ.engine.render.texture;

import fr.univ.engine.logging.LoggingEngine;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * A texture is composed of multiples sprites ordered as channels.
 * A channel contain a single sprite.
 * This allow to easily change the displayed sprite.
 */
public class Texture {
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
    /**
     * The different channels.
     * Each channel have a unique name mapping it to the sprite to render.
     */
    private final Map<String, ISprite> channels;
    /**
     * The current channel to render.
     */
    private String currentChannel;
    /**
     * The default channel name.
     */
    public static final String DEFAULT_CHANNEL = "DEFAULT_TEXTURE_CHANNEL";

    /**
     * Create a new texture that will be rendered with a specific size.
     * Note: the sprites images size might differ from the displayed size.
     *
     * @param width the width at which to render.
     * @param height the height at which to render.
     */
    public Texture(double width, double height) {
        this(width, height, 0);
    }

    /**
     * Create a new texture that will be rendered with a specific size and z-index.
     * Note: the sprites images size might differ from the displayed size.
     *
     * @param width the width at which to render.
     * @param height the height at which to render.
     * @param zIndex the z-index in which to render.
     */
    public Texture(double width, double height, int zIndex) {
        this.width = width;
        this.height = height;
        this.zIndex = zIndex;
        this.channels = new HashMap<>();
        this.currentChannel = DEFAULT_CHANNEL;
    }
    /**
     * Create a new texture that will be rendered with a specific size.
     * Note: the sprites images size might differ from the displayed size.
     *
     * @param width the width at which to render.
     * @param height the height at which to render.
     */
    public Texture(double width, double height, ISprite defaultSprite) {
        this(width, height, 0);
        setDefaultSprite(defaultSprite);
    }

    /**
     * Create a new texture that will be rendered with a specific size and z-index.
     * Note: the sprites images size might differ from the displayed size.
     *
     * @param width the width at which to render.
     * @param height the height at which to render.
     * @param zIndex the z-index in which to render.
     */
    public Texture(double width, double height, int zIndex, ISprite defaultSprite) {
        this(width, height, zIndex);
        setDefaultSprite(defaultSprite);
    }

    /**
     * @return the image to render for this texture.
     */
    public Image getImage() {
        ISprite sprite = channels.get(currentChannel);
        if (sprite == null) {
            LoggingEngine.warning(String.format("Missing sprite for channel '%s'", currentChannel));
            return null;
        } else {
            return sprite.getImage();
        }
    }

    /**
     * Add a default sprite to this texture.
     * Note: there can be one and only one default sprite at a time.
     *
     * @param sprite the sprite to use as default.
     */
    public void setDefaultSprite(ISprite sprite) {
        channels.put(DEFAULT_CHANNEL, sprite);
    }

    /**
     * Add a new sprite to this texture.
     *
     * @param channel the channel name to store the sprite.
     * @param sprite the sprite to add.
     */
    public void addSprite(String channel, ISprite sprite) {
        channels.put(channel, sprite);
    }

    /**
     * Set the current channel to render.
     *
     * @param channel the desired channel name.
     */
    public void setCurrentChannel(String channel) {
        this.currentChannel = channel;
    }

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
     * @return the z-index value of this texture
     */
    public int zIndex() {
        return zIndex;
    }

    /**
     * Update the z-index position.
     *
     * @param zIndex the new z-index value
     */
    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }
}
