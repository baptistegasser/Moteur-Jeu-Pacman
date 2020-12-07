package fr.univ.engine.io;

import java.util.EnumSet;

/**
 * Represent the status of a key.
 */
enum Status {
    /**
     * The key was just pressed in this frame
     */
    DOWN,
    /**
     * The key is held down
     */
    HELD,
    /**
     * The key was just released in this frame
     */
    UP;

    public static final EnumSet<Status> ALL = EnumSet.allOf(Status.class);
    public static final EnumSet<Status> NONE = EnumSet.noneOf(Status.class);
}
