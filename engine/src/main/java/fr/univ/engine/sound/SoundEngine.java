package fr.univ.engine.sound;

import fr.univ.engine.assets.AssetsLoader;
import fr.univ.engine.math.Math;

import java.util.HashMap;

/**
 * Engine charged of playing sounds.
 */
public class SoundEngine {
    /**
     * Map sounds to their associated keys.
     */
    private final HashMap<Object, HashMap<String, Sound<?>>> keyToSounds = new HashMap<>();
    /**
     * Default key if none is given.
     */
    private final static Object NO_KEY = new Object();
    /**
     * The global volume of the sound engine.
     * A media played at max volume will correspond to this volume.
     * The value is clamped to the range <code>[0.0,&nbsp;1.0]</code>
     */
    private double globalVolume = 1.0;

    /**
     * Initialize the list for a specific key if none present.
     *
     * @param key the key to check and init.
     */
    private void initKeyIfNeeded(Object key) {
        this.keyToSounds.computeIfAbsent(key, k -> new HashMap<>());
    }

    /**
     * Load an and play a clip from assets (short media).
     *
     * @param name the clip name.
     */
    public void playClip(String name) {
        playClip(name, 1.0, false, NO_KEY);
    }

    /**
     * Load an and play a clip from assets (short media).
     *
     * @param name the clip name.
     */
    public void playClip(String name, Object key) {
        playClip(name, 1.0, false, key);
    }

    /**
     * Load an and play a clip from assets (short media).
     *
     * @param name the clip name.
     * @param volume the wanted output volume level.
     */
    public void playClip(String name, double volume) {
        playClip(name, volume, false, NO_KEY);
    }

    /**
     * Load an and play a clip from assets (short media).
     *
     * @param name the clip name.
     * @param volume the wanted output volume level.
     */
    public void playClip(String name, double volume, Object key) {
        playClip(name, volume, false, key);
    }

    /**
     * Load an and play a clip from assets (short media).
     *
     * @param name the clip name.
     * @param volume the wanted output volume level.
     ** @param loop set to true to loop the sound.
     */
    public void playClip(String name, double volume, boolean loop) {
        playClip(name, volume, loop, NO_KEY);
    }

    /**
     * Load an and play a clip from assets (short media).
     *
     * @param name the clip name.
     * @param volume the wanted output volume level.
     ** @param loop set to true to loop the sound.
     */
    public void playClip(String name, double volume, boolean loop, Object key) {
        Clip clip = AssetsLoader.loadClip(name);
        clip.setVolume(volume);
        if (loop) clip.setLoop();
        play(clip, key);
    }

    /**
     * Load an and play a song from assets (long media).
     *
     * @param name the song name.
     */
    public void playSong(String name) {
        playSong(name, 1.0, false, NO_KEY);
    }

    /**
     * Load an and play a song from assets (long media).
     *
     * @param name the song name.
     */
    public void playSong(String name, Object key) {
        playSong(name, 1.0, false, key);
    }

    /**
     * Load an and play a song from assets (long media).
     *
     * @param name the song name.
     * @param volume the wanted output volume level.
     */
    public void playSong(String name, double volume) {
        playSong(name, volume, false, NO_KEY);
    }

    /**
     * Load an and play a song from assets (long media).
     *
     * @param name the song name.
     * @param volume the wanted output volume level.
     */
    public void playSong(String name, double volume, Object key) {
        playSong(name, volume, false, key);
    }

    /**
     * Load an and play a song from assets (long media).
     *
     * @param name the song name.
     * @param volume the wanted output volume level.
     * @param loop set to true to loop the sound.
     */
    public void playSong(String name, double volume, boolean loop) {
        playSong(name, volume, loop, NO_KEY);
    }

    /**
     * Load an and play a song from assets (long media).
     *
     * @param name the song name.
     * @param volume the wanted output volume level.
     * @param loop set to true to loop the sound.
     */
    public void playSong(String name, double volume, boolean loop, Object key) {
        Song song = AssetsLoader.loadSong(name);
        song.setVolume(volume);
        if (loop) song.setLoop();
        play(song, key);
    }

    /**
     * Play a sound without associating a key to it.
     *
     * @param sound the sound to play.
     */
    public void play(Sound<?> sound) {
        play(sound, NO_KEY);
    }

    /**
     * Play a sound and associate it to a key for identification.
     *
     * @param sound the sound to play.
     * @param key the key.
     */
    public void play(Sound<?> sound, Object key) {
        initKeyIfNeeded(key);
        Sound<?> current = keyToSounds.get(key).get(sound.name);
        if (current != null && !(current instanceof Clip)) {
            current.stop();
        }

        sound.play();
        keyToSounds.get(key).put(sound.name, sound);
    }

    /**
     * Stop all sound associated with a key.
     *
     * @param key the key.
     */
    public void stop(Object key) {
        initKeyIfNeeded(key);
        keyToSounds.get(key).forEach((s, sound) -> sound.stop());
        keyToSounds.get(key).clear();
    }

    /**
     * Stop all sound that are not associated to a key
     * and have a specific name.
     *
     * @param soundName the sound name.
     */
    public void stop(String soundName) {
        stop(soundName, NO_KEY);
    }

    /**
     * Stop all sound associated with a key and with a
     * specific name.
     *
     * @param soundName the sound name.
     * @param key the key.
     */
    public void stop(String soundName, Object key) {
        initKeyIfNeeded(key);
        Sound<?> s = keyToSounds.get(key).remove(soundName);
        if (s != null) s.stop();
    }

    /**
     * Stop all currently playing sounds.
     */
    public void stopAll() {
        this.keyToSounds.forEach((o, sounds) -> {
            sounds.forEach((s, sound) -> sound.stop());
            sounds.clear();
        });
    }

    /**
     * Init the sound engine.
     */
    public void init() {
        stopAll();
    }

    /**
     * Set the new global volume value.
     * The value will be forced between 0.0 and 1.0.
     *
     * @param volume the new global volume.
     */
    public void setGlobalVolume(double volume) {
        this.globalVolume = Math.clamp(0.0, 1.0, volume);
    }

    /**
     * @return the current global volume.
     */
    public double getGlobalVolume() {
        return globalVolume;
    }
}
