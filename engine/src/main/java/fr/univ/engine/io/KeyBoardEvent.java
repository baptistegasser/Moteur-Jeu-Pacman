package fr.univ.engine.io;

import javafx.scene.input.KeyCode;

/**
 * Representation of a keyboard key event.
 */
class KeyBoardEvent {
    /**
     * The status of the key.
     */
    public final Status status;
    /**
     * The code of the key.
     */
    public final KeyCode code;

    public KeyBoardEvent(KeyCode code, Status status) {
        this.code = code;
        this.status = status;
    }
}
