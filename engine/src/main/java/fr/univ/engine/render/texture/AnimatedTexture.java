package fr.univ.engine.render.texture;

import javafx.scene.image.Image;

import java.util.List;

/**
 * A texture that change the displayed image to create animation.
 */
public class AnimatedTexture extends Texture {
    /**
     * The different frames of this texture.
     */
    private final List<Image> frames;
    /**
     * The number of frames in this texture.
     */
    private final int frameCount;
    /**
     * The duration of a single frame in milliseconds.
     */
    private final int frameDuration;
    /**
     * The current displayed frame.
     */
    private int currentFrame;
    /**
     * The time at which the last frame changed.
     */
    private long lastFrameChange;

    /**
     * Create an animated texture.
     *
     * @param width the width at which to render.
     * @param height the height at which to render.
     * @param frameDuration the duration of a single frame.
     * @param frames the list of frames making the animation.
     */
    public AnimatedTexture(double width, double height, int frameDuration, List<Image> frames) {
        super(width, height);
        this.frames = frames;
        this.frameCount = frames.size();
        this.frameDuration = frameDuration;
        this.currentFrame = -1;
        this.lastFrameChange = System.currentTimeMillis();
    }

    @Override
    public Image getImage() {
        // Init to prevent skipping first image.
        if (currentFrame == -1) {
            currentFrame = 0;
            lastFrameChange = System.currentTimeMillis();
        }

        if (lastFrameChange + frameDuration <= System.currentTimeMillis()) {
            if ((currentFrame+1) >= frameCount) {
                currentFrame = 0;
            } else {
                currentFrame += 1;
            }
            lastFrameChange = System.currentTimeMillis();
        }

        return frames.get(currentFrame);
    }
}
