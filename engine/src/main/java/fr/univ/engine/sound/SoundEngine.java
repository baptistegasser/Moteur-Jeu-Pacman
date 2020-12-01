package fr.univ.engine.sound;

import fr.univ.engine.assets.AssetsLoader;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Engine charged of playing sounds.
 */
public class SoundEngine {

    HashMap<String, AudioClip> currentlyPlaying = new HashMap<>();
    /**
     * The global volume of the sound engine.
     * A media played at max volume will correspond to this volume.
     * The value is clamped to the range <code>[0.0,&nbsp;1.0]</code>
     */
    private double globalVolume = 1.0;

    /**
     * Set the new global volume value.
     * The value will be forced between 0.0 and 1.0.
     *
     * @param volume the new global volume.
     */
    public void setGlobalVolume(double volume) {
        this.globalVolume = clamp(volume);
    }

    public double getGlobalVolume() {
        return globalVolume;
    }

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
        return play(name, clamp(volume), false);
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
        return play(name, clamp(volume), true);
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
        // The target volume is the desired volume in function of the global volume
        double targetVolume = globalVolume * volume;

        Media media = AssetsLoader.loadSound(name);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(targetVolume);
        if (loop) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }
        mediaPlayer.play();
        return mediaPlayer;
    }

    /**
     * Play a clip at max volume (1.0).
     *
     * @param name the name of the clip.
     * @return the AudioClip being played.
     */
    public AudioClip playClip(String name) {
        return playClip(name, 1.0);
    }

    /**
     * Create a {@link AudioClip} and start playing it at a specific volume.
     *
     * @param name the name of the clip to play.
     * @param volume the output volume level (clamped to the range <code>[0.0,&nbsp;1.0]</code>).
     * @return the media player controlling the sound playing.
     */
    public AudioClip playClip(String name, double volume) {
        // The target volume is the desired volume in function of the global volume
        double targetVolume = globalVolume * clamp(volume);

        AudioClip clip = AssetsLoader.loadClip(name);
        clip.setVolume(targetVolume);
        clip.play();
        currentlyPlaying.put(name, clip);
        return clip;
    }

    /**
     * Stop the given music name
     * If the music is playing, the music is stopped
     * @param name
     */
    public void stopClip (String name) {
        for (Map.Entry<String, AudioClip> clip: currentlyPlaying.entrySet()){
            if(clip.getKey().equals(name)) {
                clip.getValue().stop();
                currentlyPlaying.remove(clip);
            }
        }
    }

    /**
     * Clamp a volume value between the values accepted by
     * JavaFX, i.e. between 0.0 and 1.0.
     *
     * @param volume the volume to clamped.
     * @return the clamped value.
     */
    private double clamp(double volume) {
        return Math.max(0.0, Math.min(volume, 1.0));
    }
}
