package fr.univ.engine.sound;

import fr.univ.engine.utils.CachedResourcesLoader;
import java.io.File;
import javafx.scene.media.*;

public class SoundEngine {


    private CachedResourcesLoader soundLoader;

    /**
     * Set the sound loader to be used by the engine to load sounds.
     * @param loader the loader to use
     */
    public void setSoundLoader(CachedResourcesLoader loader) {
        this.soundLoader = loader;
    }

    public void play (String name , boolean loop) {
        Media media = soundLoader.getMedia(name);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.4);
        mediaPlayer.setAutoPlay(true);
        if(loop) mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public static void staticPlay (String name) {
        CachedResourcesLoader cachedResourcesLoader = new CachedResourcesLoader("assets/");
        Media media = cachedResourcesLoader.getMedia(name);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(0.4);
        mediaPlayer.play();
    }
}
