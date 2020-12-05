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
     * The scheduled action to run later.
     */
    private final PriorityQueue<FutureTask> scheduledTasks = new PriorityQueue<>(Comparator.comparingLong(o -> o.startTime));

    /**
     * Schedule a task to be run in the future.
     *
     * @param task the task.
     */
    public void schedule(FutureTask task) {
        scheduledTasks.add(task);
    }

    /**
     * Construct and schedule a task to be run in the future.
     *
     * @see FutureTask#FutureTask(long, TimeUnit, Runnable) for the constructor used.
     */
    public void schedule(long runIn, TimeUnit timeUnit, Runnable action) {
        scheduledTasks.add(new FutureTask(runIn, timeUnit, action));
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
     * Update the TimeEngine and run actions if the time has come for them to go.
     */
    public void update() {
        long time = System.nanoTime();

        FutureTask task;
        while (!scheduledTasks.isEmpty() && scheduledTasks.peek() != null && scheduledTasks.peek().startTime <= time) {
            task = scheduledTasks.remove();
            task.action.run();
        }
    }
}
