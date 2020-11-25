package fr.univ.engine.logging;

import javafx.scene.paint.Color;

import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * Class charged to format and color log message for the engine.
 */
class ColorFormatter extends SimpleFormatter {
    private boolean autoColor = false;

    /**
     * Format the given LogRecord.
     * The record can contains parameters:
     *  * 0: (String or int) the line of code that logged
     *  * 1: (javafx.scene.paint.Color) the color to use
     *
     * @param record the log record to be formatted
     * @return a formatted string representing the log record
     */
    @Override
    public String format(final LogRecord record) {
        // Retrieve source line and color while handling null case
        String sourceLine = parseSourceLine(record.getParameters());
        Color color = parseColor(record.getParameters());

        // [~line in class#method()] - [LEVEL] - The message
        String message = String.format(
                "[~%s in %s#%s()] - [%s] - %s",
                sourceLine,
                record.getSourceClassName(),
                record.getSourceMethodName(),
                record.getLevel(),
                record.getMessage());

        // Handle log with no color
        if (color == null) {
            if (autoColor) {
                color = DisplayColor.get(sourceLine + record.getSourceClassName() + record.getSourceMethodName());
                return colorString(color, message) + "\n";
            } else {
                return message + "\n";
            }
        } else {
            return colorString(color, message) + "\n";
        }
    }

    /**
     * Attempt to retrieve a valid string or int representing the line number.
     * Attempt to find the info in {@code params[0]}.
     *
     * @param params the parameters of a record
     * @return the line as a string, or "?" if not found
     */
    private String parseSourceLine(Object[] params) {
        if (params == null || params.length < 1) {
            return "?";
        }
        if (params[0] instanceof String) {
            return (String) params[0];
        } else if (params[0] instanceof Integer) {
            return String.valueOf(params[0]);
        } else {
            return "?";
        }
    }

    /**
     * Attempt to retrieve a valid Color representing the color of this message.
     * Attempt to find the info in {@code params[1]}.
     *
     * @param params the parameters of a record
     * @return the color instance, or null if not found
     */
    private Color parseColor(Object[] params) {
        if (params == null || params.length < 2 || !(params[1] instanceof Color)) {
            return null;
        } else {
            return (Color) params[1];
        }
    }

    /**
     * Add color to a string via ANSI special codes.
     *
     * @param color the color to use on the string
     * @param s the string to color
     * @return the string with ansi delimiters
     */
    private String colorString(Color color, String s) {
        // ESC[38;2;r;g;bm%TEXT%ESC[0m
        return String.format("\u001b[38;2;%d;%d;%dm%s\u001b[0m",
                (int) (255 * color.getRed()),
                (int) (255 * color.getGreen()),
                (int) (255 * color.getBlue()),
                s);
    }

    /**
     * Set if the formatter should automatically set a color if none is given.
     *
     * @param autoColor the new value
     */
    public void setAutoColor(boolean autoColor) {
        this.autoColor = autoColor;
    }
}
