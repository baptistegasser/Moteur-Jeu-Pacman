package fr.univ.engine.sound;

import fr.univ.engine.math.Math;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;

/**
 * Sound implementation using a {@link AudioClip} as player.
 * Recommended for short sound such as hit sound.
 */
public class Clip extends Sound<AudioClip> {
    public Clip(AudioClip player, String name) {
        super(player, name);
    }

    @Override
    public void play() {
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
}
