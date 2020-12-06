package fr.univ.engine.logging;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link FlushedLogger} class.
 */
class FlushedLoggerTest {
    private final static PrintStream standardOut = System.out;
    private final static ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private static FlushedLogger logger;

    @BeforeAll
    static void beforeAll() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterAll
    static void afterAll() {
        System.setOut(standardOut);
    }

    @BeforeEach
    void setUp() {
        outputStreamCaptor.reset();
        logger = new FlushedLogger();
    }

    /**
     * Assert that the redirect method work.
     */
    @Test
    void assertRedirectOutput() {
        System.out.println("Test redirection");
        assertEquals("Test redirection", outputStreamCaptor.toString().trim());
    }

    /**
     * Assert that logger log.
     */
    @Test
    void assertLogOutput() {
        String expected = "[~? in fr.univ.engine.logging.FlushedLoggerTest#assertLogOutput()] - [INFO] - test message for logging";
        logger.log(Level.INFO, "test message for logging");
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    /**
     * Assert that the output is colored.
     */
    @Test
    void setAutoColor() {
        logger.setAutoColor(true);
        String expected = "\u001b[38;2;255;191;0m[~? in fr.univ.engine.logging.FlushedLoggerTest#setAutoColor()] - [INFO] - test message for logging\u001b[0m\n";
        logger.log(Level.INFO, "test message for logging");
        assertEquals(expected, outputStreamCaptor.toString());
    }
}