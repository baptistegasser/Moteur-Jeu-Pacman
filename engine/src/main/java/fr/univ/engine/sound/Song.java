package fr.univ.engine.sound;

import fr.univ.engine.math.Math;
import javafx.scene.media.MediaPlayer;

/**
 * Sound implementation using a {@link MediaPlayer} as player.
 * Recommended for longer sound such as music.
 */
public class Song extends Sound<MediaPlayer> {
    public Song(MediaPlayer player, String name) {
        super(player, name);
    }

    /**
     * Play a song
     */
    @Override
    public void play() {
        player.play();
    }
    /**
     * Pause a song
     */
    @Override
    public void pause() {
        player.pause();
    }
    /**
     * Resume a song
     */
    @Override
    public void unpause() {
        player.play();
    }
    /**
     * Stop a song
     */
    @Override
    public void stop() {
        player.stop();
    }
    /**
     * Play a song as an infinite loop
     */
    @Override
    public void setLoop() {
        player.setCycleCount(MediaPlayer.INDEFINITE);
    }
    /**
     * Set the volume of a song
     */
    @Override
    public void setVolume(double volume) {
        player.setVolume(Math.clamp(0.0, 1.0, volume));
    }

    /**
     * Get the song volume
     */
    @Override
    public double getVolume() {
        return player.getVolume();
    }
}
