package fr.univ.engine.sound;

import fr.univ.engine.math.Math;
import javafx.scene.media.AudioClip;

/**
 * Sound implementation using a {@link AudioClip} as player.
 * Recommended for short sound such as hit sound.
 */
public class Clip extends Sound<AudioClip> {
    /**
     * Store the current volume for pause/unpause.
     */
    private double currentVolume;
    /**
     * True if this clip paused.
     */
    private boolean paused;

    public Clip(AudioClip player, String name) {
        super(player, name);
    }

    @Override
    public void play() {
        player.play();
    }

    /**
     * Pause the clip by setting the volume to 0
     */
    @Override
    public void pause() {
        // We can't pause nor unpause, just muting the sound lmao
        player.setVolume(0);
        paused = true;
    }

    /**
     * Resume the song
     */
    @Override
    public void unpause() {
        // We can't pause nor unpause, just muting the sound lmao
        player.setVolume(currentVolume);
        paused = false;
    }

    /**
     * Stop the song
     */
    @Override
    public void stop() {
        player.stop();
    }

    /**
     * Loop the song
     */
    @Override
    public void setLoop() {
        player.setCycleCount(AudioClip.INDEFINITE);
    }

    /**
     * Set the volume
     * @param volume the desire volume.
     */
    @Override
    public void setVolume(double volume) {
        if (!paused) {
            player.setVolume(Math.clamp(0.0, 1.0, volume));
        }
        this.currentVolume = volume;
    }

    /**
     * Get the volume
     * @return The volume of the audio clip
     */
    @Override
    public double getVolume() {
        return player.getVolume();
    }
}
