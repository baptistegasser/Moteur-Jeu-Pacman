package fr.univ.engine.logging;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link ColorFormatter} class.
 */
class ColorFormatterTest {
    /**
     * Formatter instance used by each test.
     */
    private ColorFormatter formatter;

    /**
     * Simple info record with only the message set as "empty".
     */
    private final static LogRecord info;
    /**
     * Simple info record with the message set as "empty" and color to red.
     */
    private final static LogRecord redInfo;
    /**
     * Full severe record with all informations set.
     */
    private final static LogRecord fullSevere;

    // Configure the log records.
    static {
        info = new LogRecord(Level.INFO, "empty");

        redInfo = new LogRecord(Level.INFO, "empty");
        redInfo.setParameters(new Object[] {null, Color.RED});

        fullSevere = new LogRecord(Level.SEVERE, "A severe log");
        fullSevere.setParameters(new Object[] {95}); // Line 95 was the call
        fullSevere.setSourceClassName("package.my.class");
        fullSevere.setSourceMethodName("methodName");
    }

    /**
     * Reset the formatter before each test.
     */
    @BeforeEach
    void setUp() {
        formatter = new ColorFormatter();
    }

    /**
     * Test the formatting of a line.
     */
    @Test
    void format() {
        String expectedSevere = "[~95 in package.my.class#methodName()] - [SEVERE] - A severe log\n";
        assertEquals(expectedSevere, formatter.format(fullSevere));
    }

    /**
     * Assert that setting autoColor to true add colors automatically.
     */
    @Test
    void setAutoColor() {
        final String noColor = "[~? in null#null()] - [INFO] - empty\n";
        final String redColor = "\u001b[38;2;255;0;0m[~? in null#null()] - [INFO] - empty\u001b[0m\n";
        final String autoColor = "\u001b[38;2;255;0;255m[~? in null#null()] - [INFO] - empty\u001b[0m\n";

        // Test without color
        formatter.setAutoColor(false);
        assertEquals(noColor, formatter.format(info));
        assertEquals(redColor, formatter.format(redInfo));

        formatter.setAutoColor(true);
        assertEquals(autoColor, formatter.format(info));
        assertEquals(redColor, formatter.format(redInfo));
    }

    /**
     * Assert source line number can be passed as string too.
     */
    @Test
    void testStringSourceLine() {
        final int i = (int) fullSevere.getParameters()[0];
        fullSevere.setParameters(new Object[] { String.valueOf(i) });

        final String full = "[~95 in package.my.class#methodName()] - [SEVERE] - A severe log\n";
        assertEquals(full, formatter.format(fullSevere));

        fullSevere.getParameters()[0] = i;
    }
}