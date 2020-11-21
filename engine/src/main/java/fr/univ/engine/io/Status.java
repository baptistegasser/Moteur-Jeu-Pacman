package fr.univ.engine.io;

import java.util.EnumSet;

/**
 * Represent the status of a key.
 */
enum Status {
    DOWN, // The key was just pressed in this frame
    HELD, // The key is held down
    UP; // The key was just released in this frame

    public static final EnumSet<Status> ALL = EnumSet.allOf(Status.class);
    public static final EnumSet<Status> NONE = EnumSet.noneOf(Status.class);
}
