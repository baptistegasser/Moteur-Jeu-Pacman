package fr.univ.engine.time;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

/**
 * Engine used to run task at a future point and generally
 * any time related work should be handled by this engine.
 */
public class TimeEngine {
    /**
     * The delay accumulated by pauses.
     */
    private long delay;
    /**
     * The timestamp when the current pause started.
     */
    private long currentPauseStart;
    /**
     * The scheduled action to run later.
     */
    private final PriorityQueue<FutureTask> scheduledTasks = new PriorityQueue<>(Comparator.comparingLong(o -> o.startTime));

    /**
     * Initialize the time engine, clear any task if needed.
     */
    public void init() {
        if (!scheduledTasks.isEmpty()) {
            cancelAll();
        }
        delay = 0;
    }

    /**
     * Pause the engine and take in account the delay.
     */
    public void pause() {
        this.currentPauseStart = System.nanoTime();
    }

    /**
     * Unpause the engine and take in account the delay.
     */
    public void unpause() {
        this.delay += System.nanoTime() - this.currentPauseStart;
        this.currentPauseStart = 0;
    }

    /**
     * Schedule a task to be run in the future.
     *
     * @param task the task.
     */
    public void schedule(FutureTask task) {
        task.schedule(delay);
        scheduledTasks.add(task);
    }

    /**
     * Construct and schedule a task to be run in the future.
     *
     * @see FutureTask#FutureTask(long, TimeUnit, Runnable) for the constructor used.
     * @param runIn the time to elapse from now before running.
     * @param timeUnit the time unit used by runIn.
     * @param action the action to run later.
     */
    public void schedule(long runIn, TimeUnit timeUnit, Runnable action) {
        schedule(new FutureTask(runIn, timeUnit, action));
    }

    /**
     * Cancel a specific task from running.
     *
     * @param task the task to cancel.
     */
    public void cancel(FutureTask task) {
        this.scheduledTasks.remove(task);
    }

    /**
     * Cancel all task that have a specific identifier.
     *
     * @param identifier the identifier of the tasks to cancel.
     */
    public void cancel(Object identifier) {
        this.scheduledTasks.removeIf(task -> task.identifier.equals(identifier));
    }

    /**
     * Cancel all scheduled tasks.
     */
    public void cancelAll() {
        scheduledTasks.clear();
    }

    /**
     * Update the TimeEngine and run actions if the time has come for them to go.
     */
    public void update() {
        long time = System.nanoTime() - delay;

        FutureTask task;
        while (!scheduledTasks.isEmpty() && scheduledTasks.peek() != null && scheduledTasks.peek().startTime <= time) {
            task = scheduledTasks.remove();
            task.action.run();
        }
    }
}
