package fr.univ.engine.time;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Engine used to manipulate time sensitive events.
 */
public class TimeEngine {
    /**
     * Map a list of actions to the point in time they should start.
     * The point of start is expressed in nanoseconds.
     */
    private final Map<Long, List<Runnable>> timeToActions = new HashMap<>();

    /**
     * Run a given function when a duration will have passed.
     *
     * @param duration the time to wait.
     * @param unit the unit in which the time to wait is mesure.
     * @param r the action to schedule.
     */
    public void runIn(long duration, TimeUnit unit, Runnable r) {
        long start = System.nanoTime() + unit.toNanos(duration);

        List<Runnable> actions = timeToActions.getOrDefault(start, new ArrayList<>());
        actions.add(r);
        timeToActions.put(start, actions);
    }

    /**
     * Update the TimeEngine and call actions if the time has come.
     */
    public void update() {
        long time = System.nanoTime();

        timeToActions.forEach((start, runnables) -> {
            if (start <= time) {
                runnables.forEach(Runnable::run);
            }
        });
    }
}
