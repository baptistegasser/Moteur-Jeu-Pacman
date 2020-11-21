package fr.univ.engine.io;

import javafx.scene.input.KeyEvent;

/**
 * Classes that want to handle {@link KeyEvent} should implement theses methods.
 */
public interface KeyEventHandler {
    /**
     * Method called when a key is pressed.
     *
     * @param keyEvent the information about this event
     */
    void onKeyPressed(KeyEvent keyEvent);

    /**
     * Method called when a key is released.
     *
     * @param keyEvent the information about this event
     */
    void onKeyReleased(KeyEvent keyEvent);
}
