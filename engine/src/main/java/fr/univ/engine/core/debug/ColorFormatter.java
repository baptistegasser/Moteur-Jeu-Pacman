package fr.univ.engine.core.debug;

import javafx.scene.paint.Color;

import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * Class charged to format and color log message for the engine.
 */
class ColorFormatter extends SimpleFormatter {

    @Override
    public String format(LogRecord record) {
        String sourceLine = (String) record.getParameters()[0];

        // [line in class#method()] - [LEVEL] - The message
        String message = String.format(
                "[%s in %s#%s()] - [%s] - %s\n",
                sourceLine,
                record.getSourceClassName(),
                record.getSourceMethodName(),
                record.getLevel(),
                record.getMessage());

        Color color = (Color) record.getParameters()[1];
        // Handle log with no color
        if (color == null) {
            return message;
        } else {
            return colorString(color, message);
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
        return String.format("\u001b[38;2;%d;%d;%dm %s \u001b[0m ",
                (int) (255 * color.getRed()),
                (int) (255 * color.getGreen()),
                (int) (255 * color.getBlue()),
                s);
    }

    /**
     * Generate a relatively unique color based on multiple values.
     * The idea is to concat all strings, split them in 3 and use each split
     * to generate R, G and B values based on the hashcode.
     * The result is not truly unique due to hash collision and rgb value in a small range of 0-255.
     *
     * @param s1 the first string, need at least one
     * @param ss the other strings
     * @return a color instance
     */
    public static Color generateColor(final String s1, final String... ss) {
        StringBuilder base = new StringBuilder(s1);
        for (String sx : ss) {
            base.append(sx);
        }

        int l = (int) Math.floor(base.length() / 3d);

        int r = Math.abs(base.substring(0, l).hashCode()) % 255;
        int g = Math.abs(base.substring(l, l * 2).hashCode()) % 255;
        int b = Math.abs(base.substring(l*2, l * 3).hashCode()) % 255;

        return Color.rgb(r, g, b);
    }
}
