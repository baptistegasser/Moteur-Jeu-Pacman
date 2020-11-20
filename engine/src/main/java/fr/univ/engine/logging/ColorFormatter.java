package fr.univ.engine.logging;

import javafx.scene.paint.Color;

import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * Class charged to format and color log message for the engine.
 */
class ColorFormatter extends SimpleFormatter {
    private boolean autoColor = false;

    @Override
    public String format(LogRecord record) {
        String sourceLine = (String) record.getParameters()[0];

        // [line in class#method()] - [LEVEL] - The message
        String message = String.format(
                "[~%s in %s#%s()] - [%s] - %s",
                sourceLine,
                record.getSourceClassName(),
                record.getSourceMethodName(),
                record.getLevel(),
                record.getMessage());

        Color color = (Color) record.getParameters()[1];
        // Handle log with no color
        if (color == null) {
            if (autoColor) {
                color = generateColor(sourceLine + record.getSourceClassName() + record.getSourceMethodName());
                return colorString(color, message) + "\n";
            } else {
                return message + "\n";
            }
        } else {
            return colorString(color, message) + "\n";
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
     * Generate a relatively unique color based on multiple values.
     * The idea is to concat all strings, get the hashcode of thi new
     * string and turn it into an index used to get one of the display colors.
     * The result is not truly unique due to hash collision and limited color list.
     *
     * @param s1 the first string, need at least one
     * @param ss the other strings
     * @return a color instance
     */
    public static Color generateColor(final String s1, final String... ss) {
        StringBuilder sb = new StringBuilder(s1);
        for (String sx : ss) {
            sb.append(sx);
        }
        final String concat = sb.toString();

        int index = Math.abs(concat.hashCode()) % (DisplayColor.colorsCount-1);
        return DisplayColor.colors[index];
    }

    /**
     * Set if the formatter should automatically set a color if none is given.
     *
     * @param autoColor the new value
     * @see #generateColor(String, String...) on how the color is chosen
     */
    public void setAutoColor(boolean autoColor) {
        this.autoColor = autoColor;
    }
}
