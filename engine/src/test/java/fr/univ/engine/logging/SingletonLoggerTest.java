package fr.univ.engine.logging;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link SingletonLogger} class.
 */
class SingletonLoggerTest {
    private final static PrintStream standardOut = System.out;
    private final static ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private static SingletonLogger logger;

    /**
     * Assert correct instanciation of singleton.
     */
    @BeforeAll
    static void getInstance() {
        System.setOut(new PrintStream(outputStreamCaptor));

        assertNotNull(SingletonLogger.getInstance());
        logger = SingletonLogger.getInstance();
    }

    @AfterAll
    static void afterAll() {
        System.setOut(standardOut);
    }

    @BeforeEach
    void setUp() {
        outputStreamCaptor.reset();
        logger.setAutoColor(false); // Assert default set to false
    }

    /**
     * Assert that the {@link #()} method work.
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
        String expected = "[~? in fr.univ.engine.logging.SingletonLoggerTest#assertLogOutput()] - [INFO] - test message for logging";
        logger.log(Level.INFO, "test message for logging");
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    /**
     * Assert that the output is colored.
     */
    @Test
    void setAutoColor() {
        logger.setAutoColor(true);
        String expected = "\u001b[38;2;255;36;0m[~? in fr.univ.engine.logging.SingletonLoggerTest#setAutoColor()] - [INFO] - test message for logging\u001b[0m\n";
        logger.log(Level.INFO, "test message for logging");
        assertEquals(expected, outputStreamCaptor.toString());
    }
}