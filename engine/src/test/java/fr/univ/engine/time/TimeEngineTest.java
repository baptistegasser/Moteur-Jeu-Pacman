package fr.univ.engine.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link TimeEngine} class.
 */
class TimeEngineTest {
    /**
     * Time engine used for test.
     */
    private final TimeEngine timeEngine = new TimeEngine();

    @BeforeEach
    void setUp() {
        timeEngine.init();
    }

    @Test
    void init() throws InterruptedException {
        AtomicBoolean flag = new AtomicBoolean(false);
        TimeEngine customEngine = new TimeEngine();
        assertDoesNotThrow(customEngine::init);
        customEngine.schedule(1, TimeUnit.SECONDS, () -> flag.set(true));
        assertDoesNotThrow(customEngine::init);
        Thread.sleep(1000);
        customEngine.update();
        assertFalse(flag.get());
    }

    @Test
    void scheduleTask() throws InterruptedException {
        final boolean[] values = new boolean[] {
                false,
                false,
                false
        };
        final FutureTask[] taskArray = new FutureTask[] {
                new FutureTask(3, TimeUnit.SECONDS, "task 1", () -> values[0] = true),
                new FutureTask(6, TimeUnit.SECONDS, "task 2", () -> values[1] = true),
                new FutureTask(9, TimeUnit.SECONDS, "task 3", () -> values[2] = true)
        };

        // Shuffle to schedule task in a more or less random manner
        List<FutureTask> taskList = Arrays.asList(taskArray);
        Collections.shuffle(taskList);
        taskList.forEach(timeEngine::schedule);

        // Sleep 3 seconds, check values, sleep again...
        for (int i = 0; i < values.length; i++) {
            Thread.sleep(3000);
            timeEngine.update();

            // Assert our task has run
            assertTrue(values[i]);
            // Assert further tasks didn't run
            for (int j = i+1; j < values.length; j++) {
                assertFalse(values[j]);
            }
        }
    }

    @Test
    void createAndScheduleTask() throws InterruptedException {
        AtomicBoolean called = new AtomicBoolean(false);
        timeEngine.schedule(3, TimeUnit.SECONDS, () -> called.set(true));

        // Too early
        Thread.sleep(1000);
        timeEngine.update();
        assertFalse(called.get());

        // Should be good
        Thread.sleep(2000);
        timeEngine.update();
        assertTrue(called.get());
    }

    @Test
    void cancelTask() throws InterruptedException {
        AtomicBoolean called = new AtomicBoolean(false);
        FutureTask setFalse = new FutureTask(1, TimeUnit.SECONDS, () -> called.set(false));
        FutureTask setTrue = new FutureTask(2, TimeUnit.SECONDS, () -> called.set(true));

        timeEngine.schedule(setTrue);
        timeEngine.schedule(setFalse);

        Thread.sleep(2000);
        timeEngine.cancel(setTrue);
        timeEngine.update();
        assertFalse(called.get());
    }

    @Test
    void cancelByIdentifier() throws InterruptedException {
        AtomicInteger value = new AtomicInteger(-1);

        timeEngine.schedule(new FutureTask(1, TimeUnit.SECONDS, "cancel me", () -> value.set(1)));
        timeEngine.schedule(new FutureTask(2, TimeUnit.SECONDS, "run me", () -> value.set(2)));
        timeEngine.schedule(new FutureTask(3, TimeUnit.SECONDS, "cancel me", () -> value.set(3)));

        Thread.sleep(3000);
        timeEngine.cancel("cancel me");
        timeEngine.update();
        assertEquals(2, value.get());
    }

    @Test
    void cancelAll() throws InterruptedException {
        AtomicInteger value = new AtomicInteger(-1);

        timeEngine.schedule(new FutureTask(1, TimeUnit.SECONDS, "cancel me", () -> value.set(1)));
        timeEngine.schedule(new FutureTask(2, TimeUnit.SECONDS, "run me", () -> value.set(2)));
        timeEngine.schedule(new FutureTask(3, TimeUnit.SECONDS, "cancel me", () -> value.set(3)));

        Thread.sleep(3000);
        timeEngine.cancelAll();
        timeEngine.update();
        assertEquals(-1, value.get());
    }
}