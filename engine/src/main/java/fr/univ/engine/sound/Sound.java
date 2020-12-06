package fr.univ.engine.sound;

import java.util.Objects;

/**
 * Abstract sound implementation that act as a proxy for a media player.
 */
public abstract class Sound<T> {
    /**
     * The player used to handle the sound.
     */
    protected final T player;
    /**
     * The name of this sound.
     * This should be unique, one name should map
     * to one and only one sound to ear.
     */
    protected final String name;

    public Sound(T player, String name) {
        this.player = player;
        this.name = name;
    }

    /**
     * Method to call to play the sound.
     */
    public abstract void play();

    /**
     * Method to call to stop the sound.
     */
    public abstract void stop();

    /**
     * Method to call to make the sound repeat.
     */
    public abstract void setLoop();

    /**
     * Method to call to set the sound volume.
     *
     * @param volume the desire volume.
     */
    public abstract void setVolume(double volume);

    /**
     * @return the name of this sound.
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sound<?> sound = (Sound<?>) o;
        return name.equals(sound.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
