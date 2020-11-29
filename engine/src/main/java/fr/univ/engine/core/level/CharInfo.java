package fr.univ.engine.core.level;

/**
 * Information about an entity stored as a char.
 * This information is used/created by the {@link TextLevelLoader} class.
 */
public class CharInfo implements LevelInfo {
    /**
     * The horizontal position of the char in the text.
     */
    int x = 0;
    /**
     * The vertical position of the char in the text.
     */
    int y = 0;

    /**
     * @return the horizontal position of the char in the text.
     */
    public int x() {
        return x;
    }

    /**
     * @return the vertical position of the char in the text.
     */
    public int y() {
        return y;
    }
}
