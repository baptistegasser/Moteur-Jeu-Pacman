package fr.univ.engine.logging;

import javafx.scene.paint.Color;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Engine charged of offering a simple api to log messages with support
 * for formatting, coloring and managing right of classes and packages to log.
 */
public final class LoggingEngine {
    /**
     * The logger charged of logging messages.
     *
     * @see ColorFormatter for the log formatting
     * @see FlushedLogger for the configuration
     */
    private static final FlushedLogger logger = new FlushedLogger();
    /**
     * Manager charged to determine which class is allowed to log.
     */
    private static final LoggingManager manager = new LoggingManager();

    /**
     * Set the log level specifying which message levels will be logged.
     *
     * @param newLevel the new value for the log level (may be null)
     */
    public static void setLevel(Level newLevel) {
        manager.setLevel(newLevel);
        logger.setLevel(newLevel);
    }

    /**
     * @return the {@link LoggingManager} used by the engine.
     */
    public static LoggingManager manager() {
        return manager;
    }

    /**
     * @see ColorFormatter#setAutoColor(boolean)
     */
    public static void setAutoColor(boolean autoColor) {
        logger.setAutoColor(autoColor);
    }

    /**
     * Log a message with a given level.
     *
     * @param level the level of this message
     * @param msg   the message to log
     */
    public static void log(Level level, String msg) {
        logImpl(level, msg, null);
    }

    /**
     * Log a colored message with a given level.
     *
     * @param level the level of this message
     * @param msg   the message to log
     * @param color the color to use on the text
     */
    public static void log(Level level, String msg, Color color) {
        logImpl(level, msg, color);
    }

    /**
     * Log a SEVERE message.
     *
     * @param msg the message to log
     */
    public static void severe(String msg) {
        logImpl(Level.SEVERE, msg, null);
    }

    /**
     * Log a colored SEVERE message.
     *
     * @param msg   the message to log
     * @param color the color to use on the text
     */
    public static void severe(String msg, Color color) {
        logImpl(Level.SEVERE, msg, color);
    }

    /**
     * Log a WARNING message.
     *
     * @param msg the message to log
     */
    public static void warning(String msg) {
        logImpl(Level.WARNING, msg, null);
    }

    /**
     * Log a colored WARNING message.
     *
     * @param msg   the message to log
     * @param color the color to use on the text
     */
    public static void warning(String msg, Color color) {
        logImpl(Level.WARNING, msg, color);
    }

    /**
     * Log a INFO message.
     *
     * @param msg the message to log
     */
    public static void info(String msg) {
        logImpl(Level.INFO, msg, null);
    }

    /**
     * Log a colored INFO message.
     *
     * @param msg   the message to log
     * @param color the color to use on the text
     */
    public static void info(String msg, Color color) {
        logImpl(Level.INFO, msg, color);
    }

    /**
     * Log a FINE message.
     *
     * @param msg the message to log
     */
    public static void fine(String msg) {
        logImpl(Level.FINE, msg, null);
    }

    /**
     * Log a colored FINE message.
     *
     * @param msg   the message to log
     * @param color the color to use on the text
     */
    public static void fine(String msg, Color color) {
        logImpl(Level.FINE, msg, color);
    }

    /**
     * Log a FINER message.
     *
     * @param msg the message to log
     */
    public static void finer(String msg) {
        logImpl(Level.FINER, msg, null);
    }

    /**
     * Log a colored FINER message.
     *
     * @param msg   the message to log
     * @param color the color to use on the text
     */
    public static void finer(String msg, Color color) {
        logImpl(Level.FINER, msg, color);
    }

    /**
     * Log a FINEST message.
     *
     * @param msg the message to log
     */
    public static void finest(String msg) {
        logImpl(Level.FINEST, msg, null);
    }

    /**
     * Log a colored FINEST message.
     *
     * @param msg   the message to log
     * @param color the color to use on the text
     */
    public static void finest(String msg, Color color) {
        logImpl(Level.FINEST, msg, color);
    }

    /**
     * Log the time elapsed to run something like a method.
     * This is considered automatically to be of FINEST level.
     *
     * @param start  the time at which the counter started in NANOSECONDS
     * @param end    the time at which the counter ended in NANOSECONDS
     * @param target the target that was run
     */
    public static void logElapsedTime(long start, long end, String target) {
        // "class#method() took X UnitOfTimes"
        String msg = String.format("%s took %d %s",
                target,
                TimeUnit.MILLISECONDS.convert(end - start, TimeUnit.NANOSECONDS),
                TimeUnit.MILLISECONDS.toString());

        logImpl(Level.FINEST, msg, null);
    }

    /**
     * The implementation behind public log methods.
     * Log a message with a given level and color and take charge
     * of retrieving information on the caller class, method and line number.
     *
     * @param level the level of this message
     * @param msg   the message to log
     */
    private static void logImpl(Level level, String msg, Color color) {
        // Don't try to log message that won't be shown
        if (!manager.checkLevel(level)) {
            return;
        }

        // 0 = thread, 1 = this method, 2 = caller of this method, 3 = true caller
        StackTraceElement e = Thread.currentThread().getStackTrace()[3];

        // Don't log message if the class is not allowed
        if (!manager.checkName(e.getClassName())) {
            return;
        }

        // Create the log record
        LogRecord record = new LogRecord(level, msg);
        record.setParameters(new Object[]{e.getLineNumber(), color});
        record.setSourceClassName(e.getClassName().replace("fr.univ.engine.", ""));
        record.setSourceMethodName(e.getMethodName());
        logger.log(record);
    }
}
