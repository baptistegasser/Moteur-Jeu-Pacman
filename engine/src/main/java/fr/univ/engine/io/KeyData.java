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
     * The action linked to this key.
     */
    private final List<Runnable> actions;

    public KeyData() {
        this.statuses = Status.NONE.clone();
        this.actions = new ArrayList<>();
    }

    public EnumSet<Status> statuses() {
        return statuses;
    }

    public List<Runnable> actions() {
        return actions;
    }
}
