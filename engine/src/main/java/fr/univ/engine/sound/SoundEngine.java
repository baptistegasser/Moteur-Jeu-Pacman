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

    public void play (String name) {
        Media media = new Media(new File(name).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }
}
