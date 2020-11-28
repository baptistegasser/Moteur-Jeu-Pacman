package fr.univ.engine.sound;

import fr.univ.engine.utils.CachedResourcesLoader;
import javafx.scene.media.*;

/**
 * Class used to play music, regardless of the context
 */
public class SoundEngine {

    /**
     * Used to return a media with the given path
     */
    private CachedResourcesLoader soundLoader;

    /**
     * Set the sound loader to be used by the engine to load sounds.
     * @param loader the loader to use
     */
    public void setSoundLoader(CachedResourcesLoader loader) {
        this.soundLoader = loader;
    }

    /**
     * Playing music if the class is instanciated
     * @param name The path to the music
     * @param loop If you want to loop the music
     */
    public void play (String name , boolean loop) {
        Media media = soundLoader.getMedia(name);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.4);
        mediaPlayer.setAutoPlay(true);
        if(loop) mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    /**
     * Playing the music as a static methoc, without class instanciation
     * @param name The path to the music
     */
    public static void staticPlay (String name, double volume) {
        if(volume > 1 ) volume = 1.0;
        if (volume < 0 ) volume = 0;
        CachedResourcesLoader cachedResourcesLoader = new CachedResourcesLoader("assets/");
        Media media = cachedResourcesLoader.getMedia(name);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(volume);
        mediaPlayer.play();
    }
}
