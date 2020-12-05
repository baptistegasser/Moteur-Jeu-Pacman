package fr.univ.engine.render.texture;

import fr.univ.engine.render.RenderException;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * A texture that contain multiple textures.
 * The goal is to be able to easily switch textures on the fly.
 */
public class CompositeTexture extends Texture {
    /**
     * The different channels.
     * Each channel have a unique game mapping it to the
     * texture to render.
     */
    private final Map<String, Texture> channels;
    /**
     * The current channel to render.
     */
    private String currentChannel;

    protected CompositeTexture(double width, double height, String defaultChannel) {
        super(width, height);
        this.channels = new HashMap<>();
    }

    /**
     * Add a new channel to this composite texture.
     *
     * @param channel the channel name.
     * @param texture the texture to map to the channel.
     */
    public void addChannel(String channel, Texture texture) {
        channels.put(channel, texture);
    }

    /**
     * Set the current channel to render.
     *
     * @param channel the desired channel name.
     */
    public void setChannel(String channel) {
        this.currentChannel = channel;
    }

    @Override
    public Image getImage() {
        Texture texture = channels.get(currentChannel);
        if (texture == null) {
            throw new RenderException(String.format("No texture set for channel '%s' !", currentChannel));
        } else {
            return texture.getImage();
        }
    }
}
