package fr.univ.engine.time;

import java.util.concurrent.TimeUnit;

/**
 * Representation of an action that should be run in the future.
 */
public class FutureTask {
    /**
     * The time at which this task should start.
     * This time is expressed as a timestamp in nanoseconds.
     */
    long startTime;
    /**
     * The time to add to the timestamp when scheduled to get the start time.
     */
    private final long runIn;
    /**
     * An identifier that allow to find this task.
     * The identifier might not be unique allowing to find multiple tasks.
     */
    final Object identifier;
    /**
     * A default identifier that can be used when none is given.
     */
    protected final static Object NO_IDENTIFIER = new Object();
    /**
     * The action to run later.
     */
    final Runnable action;

    /**
     * Create a new task that should start when a given count of nanoseconds elapsed.
     *
     * @param runIn the time to elapse from now before running.
     * @param identifier a unique identifier.
     * @param action the action to run later.
     */
    public FutureTask(long runIn, Object identifier, Runnable action) {
        this.runIn = runIn;
        this.identifier = identifier;
        this.action = action;
    }

    /**
     * Create a new task that should start when a given count of some time unit elapsed.
     *
     * @param runIn the time to elapse from now before running.
     * @param timeUnit the time unit used by runIn.
     * @param identifier an identifier for this task.
     * @param action the action to run later.
     */
    public FutureTask(long runIn, TimeUnit timeUnit, Object identifier, Runnable action) {
        this(timeUnit.toNanos(runIn), identifier, action);
    }


    /**
     * Create a new task without identifier that should start when a given count of some time unit elapsed.
     *
     * @param runIn the time to elapse from now before running.
     * @param timeUnit the time unit used by runIn.
     * @param action the action to run later.
     */
    public FutureTask(long runIn, TimeUnit timeUnit, Runnable action) {
        this(timeUnit.toNanos(runIn), NO_IDENTIFIER, action);
    }

    /**
     * Method called when task is scheduled.
     *
     * @param delay the delay accumulated to take in account.
     */
    void schedule(long delay) {
        this.startTime = System.nanoTime() + runIn - delay;
    }
}
