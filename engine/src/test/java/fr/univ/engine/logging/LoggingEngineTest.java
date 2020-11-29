package fr.univ.engine.logging;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link LoggingEngine} class.
 */
class LoggingEngineTest {
    private final static PrintStream standardOut = System.out;
    private final static ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeAll
    static void beforeAll() {
        System.setOut(new PrintStream(outputStreamCaptor)); // redirect stream before any thing else
    }

    @AfterAll
    static void afterAll() {
        System.setOut(standardOut);
    }

    @BeforeEach
    void setUp() {
        LoggingEngine.setLevel(Level.ALL); // Reset level to be sure we don't miss
        outputStreamCaptor.reset();
    }

    /**
     * Assert that setting the level change what messages are logged.
     */
    @Test
    void setLevel() {
        String msg = "finest message";
        // Runnable to see if a message of FINEST level is logged
        Runnable r = () -> this.assertMessageLogged(Level.FINEST, msg);

        LoggingEngine.setLevel(Level.SEVERE);
        LoggingEngine.finest(msg);
        assertThrows(AssertionFailedError.class, r::run);

        LoggingEngine.setLevel(Level.FINEST);
        LoggingEngine.finest(msg);
        assertDoesNotThrow(r::run);
    }

    /**
     * Test the manager, nothing really to test...
     */
    @Test
    void manager() {
        assertNotNull(LoggingEngine.manager());
    }

    /**
     * Already tested by {@link ColorFormatterTest#setAutoColor()} !
     * Just adding this for code coverage.
     */
    @Test
    void setAutoColor() {
        LoggingEngine.setAutoColor(true);
        LoggingEngine.setAutoColor(false); // I ruined my test once for auto coloring lol
    }

    /**
     * Test the generic log method.
     */
    @Test
    void log() {
        // Test some levels but not all...
        for (Level lvl : new Level[] {Level.SEVERE, Level.INFO, Level.FINE, Level.FINEST}) {
            LoggingEngine.log(lvl, "testing log");
            assertMessageLogged(lvl, "testing log");
        }
    }

    /**
     * Test the generic colored log method.
     */
    @Test
    void logColor() {
        // Test some levels but not all...
        for (Level lvl : new Level[]{Level.SEVERE, Level.INFO, Level.FINE, Level.FINEST}) {
            LoggingEngine.log(lvl, "testing log colored", Color.RED);
            assertColorMessageLogged(lvl, "testing log colored");
        }
    }

    @Test
    void logWithoutRights() {
        LoggingEngine.manager().setExplicitMode(true);
        LoggingEngine.log(Level.INFO, "no rights");
        assertThrows(AssertionFailedError.class, () -> assertMessageLogged(Level.INFO, "no rights"));
        LoggingEngine.manager().setExplicitMode(false);
    }

    /**
     * Test the shorthand method to log severe messages.
     */
    @Test
    void severe() {
        LoggingEngine.severe("severe message");
        assertMessageLogged(Level.SEVERE, "severe message");
    }

    /**
     * Test the shorthand method to log severe messages with color.
     * For ease of test will use a color fixed as red.
     */
    @Test
    void severeColor() {
        LoggingEngine.severe("severe message", Color.RED);
        assertColorMessageLogged(Level.SEVERE, "severe message");
    }

    /**
     * Test the shorthand method to log warning messages.
     */
    @Test
    void warning() {
        LoggingEngine.warning("warning message");
        assertMessageLogged(Level.WARNING, "warning message");
    }

    /**
     * Test the shorthand method to log warning messages with color.
     * For ease of test will use a color fixed as red.
     */
    @Test
    void warningColor() {
        LoggingEngine.warning("warning message", Color.RED);
        assertColorMessageLogged(Level.WARNING, "warning message");
    }

    /**
     * Test the shorthand method to log info messages.
     */
    @Test
    void info() {
        LoggingEngine.info("info message");
        assertMessageLogged(Level.INFO, "info message");
    }

    /**
     * Test the shorthand method to log info messages with color.
     * For ease of test will use a color fixed as red.
     */
    @Test
    void infoColor() {
        LoggingEngine.info("info message", Color.RED);
        assertColorMessageLogged(Level.INFO, "info message");
    }

    /**
     * Test the shorthand method to log fine messages.
     */
    @Test
    void fine() {
        LoggingEngine.fine("fine message");
        assertMessageLogged(Level.FINE, "fine message");
    }

    /**
     * Test the shorthand method to log fine messages with color.
     * For ease of test will use a color fixed as red.
     */
    @Test
    void fineColor() {
        LoggingEngine.fine("fine message", Color.RED);
        assertColorMessageLogged(Level.FINE, "fine message");
    }

    /**
     * Test the shorthand method to log finer messages.
     */
    @Test
    void finer() {
        LoggingEngine.finer("finer message");
        assertMessageLogged(Level.FINER, "finer message");
    }

    /**
     * Test the shorthand method to log finer messages with color.
     * For ease of test will use a color fixed as red.
     */
    @Test
    void finerColor() {
        LoggingEngine.finer("finer message", Color.RED);
        assertColorMessageLogged(Level.FINER, "finer message");
    }

    /**
     * Test the shorthand method to log finest messages.
     */
    @Test
    void finest() {
        LoggingEngine.finest("finest message");
        assertMessageLogged(Level.FINEST, "finest message");
    }

    /**
     * Test the shorthand method to log finest messages with color.
     * For ease of test will use a color fixed as red.
     */
    @Test
    void finestColor() {
        LoggingEngine.finest("finest message", Color.RED);
        assertColorMessageLogged(Level.FINEST, "finest message");
    }

    /**
     * Test elapsed time method.
     */
    @Test
    void logElapsedTime() {
        long start = System.nanoTime();
        long end = start + 1_000_000;

        LoggingEngine.logElapsedTime(start, end, "measured_task");
        assertMessageLogged(Level.FINEST, "measured_task took 1 MILLISECONDS");
    }

    /**
     * Assert that a message was logged.
     *
     * @param level   the level of the logged message
     * @param message the logged message
     */
    private void assertMessageLogged(Level level, String message) {
        String output = outputStreamCaptor.toString();
        String end = String.format("[%s] - %s\n", level, message);

        assertTrue(output.endsWith(end));
    }

    /**
     * Assert that a message was logged with color.
     * For simplicity assume it was logged in red.
     *
     * @param level   the level of the logged message
     * @param message the logged message
     */
    private void assertColorMessageLogged(Level level, String message) {
        String output = outputStreamCaptor.toString();
        String start = "\u001b[38;2;255;0;0m[";
        String end = String.format("[%s] - %s\u001b[0m\n", level, message);

        assertTrue(output.startsWith(start));
        assertTrue(output.endsWith(end));
    }
}