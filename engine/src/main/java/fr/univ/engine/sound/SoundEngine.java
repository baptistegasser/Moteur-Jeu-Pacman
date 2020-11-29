package fr.univ.engine.sound;

import fr.univ.engine.assets.AssetsLoader;
import fr.univ.engine.utils.CachedResourcesLoader;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Engine charged of playing sounds.
 */
public class SoundEngine {

    /**
     * Play a sound at max volume (1.0).
     *
     * @param name the name of the assets.
     * @return the media player controlling the sound playing.
     */
    public MediaPlayer play(String name) {
        return play(name, 1.0);
    }

    /**
     * Play a sound at a specified volume.
     *
     * @param name the name of the assets.
     * @param volume the desired volume level (clamped to the range <code>[0.0,&nbsp;1.0]</code>).
     * @return the media player controlling the sound playing.
     */
    public MediaPlayer play(String name, double volume) {
        return play(name, volume, false);
    }


    /**
     * Play a sound at max volume (1.0) and loop it indefinitely.
     *
     * @param name the name of the assets.
     * @return the media player controlling the sound playing.
     */
    public MediaPlayer playLoop(String name) {
        return playLoop(name, 1.0);
    }


    /**
     * Play a sound at a specified volume and loop it indefinitely.
     *
     * @param name the name of the assets.
     * @param volume the desired volume level (clamped to the range <code>[0.0,&nbsp;1.0]</code>).
     * @return the media player controlling the sound playing.
     */
    public MediaPlayer playLoop(String name, double volume) {
        return play(name, volume, true);
    }

    /**
     * Create a {@link MediaPlayer} and start playing a sound.
     *
     * @param name the name of the assets sound to play.
     * @param volume the output volume level (clamped to the range <code>[0.0,&nbsp;1.0]</code>).
     * @param loop specify if the sound should loop until stopped.
     * @return the media player controlling the sound playing.
     */
    private MediaPlayer play(String name, double volume, boolean loop) {
        Media media = AssetsLoader.loadSound(name);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(volume);
        if (loop) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }
        mediaPlayer.play();
        return mediaPlayer;
    }
}
