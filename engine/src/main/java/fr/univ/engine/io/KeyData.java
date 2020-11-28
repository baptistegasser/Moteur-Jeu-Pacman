package fr.univ.engine.io;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * IOEngine representation of the datas linked to a key.
 */
public class KeyData {
    /**
     * The statuses of this key.
     */
    private final EnumSet<Status> statuses;
    /**
     * The actions to run when the key is pressed.
     */
    private final List<Runnable> onPressed;
    /**
     * The actions to run when the key is released.
     */
    private final List<Runnable> onReleased;

    public KeyData() {
        this.statuses = Status.NONE.clone();
        this.onPressed = new ArrayList<>();
        this.onReleased = new ArrayList<>();
    }

    public EnumSet<Status> statuses() {
        return statuses;
    }

    public List<Runnable> onPressed() {
        return onPressed;
    }

    public List<Runnable> onReleased() {
        return onReleased;
    }
}
