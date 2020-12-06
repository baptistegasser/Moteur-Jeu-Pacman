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

    @Override
    public void play() {
        player.play();
    }

    @Override
    public void pause() {
        player.pause();
    }

    @Override
    public void unpause() {
        player.play();
    }

    @Override
    public void stop() {
        player.stop();
    }

    @Override
    public void setLoop() {
        player.setCycleCount(MediaPlayer.INDEFINITE);
    }

    @Override
    public void setVolume(double volume) {
        player.setVolume(Math.clamp(0.0, 1.0, volume));
    }

    @Override
    public double getVolume() {
        return player.getVolume();
    }
}
