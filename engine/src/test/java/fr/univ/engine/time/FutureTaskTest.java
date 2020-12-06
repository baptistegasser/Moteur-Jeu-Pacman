package fr.univ.engine.time;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link FutureTask} class.
 */
class FutureTaskTest {
    @Test
    void constructors() {
        long inThreeHour = System.nanoTime() + 10800000000000L;

        FutureTask threeHourTask = new FutureTask(inThreeHour, "three hour", () -> {});
        FutureTask twoHourTask = new FutureTask(2, TimeUnit.HOURS, "two hour", () -> {});
        FutureTask oneHourTask = new FutureTask(1, TimeUnit.HOURS, "one hour", () -> {});

        // Create a list, shuffle it than sort it to be sure the constructor set the time correctly
        List<FutureTask> taskList = Arrays.asList(threeHourTask, twoHourTask, oneHourTask);
        Collections.shuffle(taskList);
        taskList.sort(Comparator.comparingLong(task -> task.startTime));

        assertEquals(threeHourTask, taskList.get(2));
        assertEquals(twoHourTask, taskList.get(1));
        assertEquals(oneHourTask, taskList.get(0));
    }

    @Test
    void defaultIdentifier() {
        FutureTask task = new FutureTask(10, TimeUnit.MILLISECONDS, () -> {});
        assertEquals(FutureTask.NO_IDENTIFIER, task.identifier);
    }
}