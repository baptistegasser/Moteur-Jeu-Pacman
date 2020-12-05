package fr.univ.engine.render.texture;

import javafx.scene.image.Image;

import java.util.List;

/**
 * A sprite that change the displayed image over time to create animation.
 */
public class Animation implements ISprite {
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
     * Create an animated sprite.
     *
     * @param frameDuration the duration of a single frame.
     * @param frames the list of frames making the animation.
     */
    public Animation(int frameDuration, List<Image> frames) {
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
