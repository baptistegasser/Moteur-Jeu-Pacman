package fr.univ.engine.core.debug;

import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

/**
 * Offer logging methods and utilities for the game engine's modules.
 * <p>
 * We can't force a class to log, a class should check herself if logging
 * is enabled for her with {@link #canLog(Class)} and then log accordingly only if needed.
 * <p>
 * However a class will be prevented to log if its not part of the declared {@link #targetsClasses} classes.
 */
public final class EngineLogger {
    /**
     * Time unit in which the display should be done.
     * Default to milliseconds.
     */
    private static TimeUnit unit = TimeUnit.MILLISECONDS;
    /**
     * The logger charged of logging messages.
     *
     * @see ColorFormatter for the log formatting
     */
    private static final Logger logger;

    /**
     * The list of class that can/should log.
     * Stored as string to allow easier reflexion.
     */
    private static final HashSet<String> targetsClasses = new HashSet<>();

    static {
        // Init logger
        logger = Logger.getLogger(EngineLogger.class.getName());
        logger.setLevel(Level.FINEST);

        // Create a handler for the output formatting and prevent default handlers
        Handler handler = new StreamHandler(System.out, new ColorFormatter());
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
    }

    /**
     * Set the log level specifying which message levels will be logged.
     *
     * @param newLevel the new value for the log level (may be null)
     */
    public static void setLevel(Level newLevel) {
        logger.setLevel(newLevel);
    }

    /**
     * Change the time unit used to display time values.
     *
     * @param unit the new time unit
     * @see #logElapsedTime(long, long, Class, String) as it use it to format elapsed time from nanoseconds value
     */
    public static void setTimeUnit(TimeUnit unit) {
        EngineLogger.unit = unit;
    }

    /**
     * Test if the calling class can log.
     *
     * @return true if logging enabled
     */
    public static boolean canLog() {
        String callerClass = Thread.currentThread().getStackTrace()[2].getClassName();
        return targetsClasses.contains(callerClass);
    }

    /**
     * Test if a class is able to log.
     *
     * @param c the class to test
     * @return true if this class should log
     */
    public static boolean canLog(Class<?> c) {
        return targetsClasses.parallelStream().anyMatch(c.getName()::equals);
    }

    /**
     * Enable the possibility for a class to log messages.
     *
     * @param c the target class
     */
    public static void enableLogging(Class<?> c) {
        targetsClasses.add(c.getName());
    }


    /**
     * Disable the possibility for a class to log messages.
     *
     * @param c the target class
     */
    public static void disableLogging(Class<?> c) {
        targetsClasses.remove(c.getName());
    }

    /**
     * Log a simple message with a level.
     *
     * @param level the level of logging
     * @param msg   the message to log
     */
    public static void log(Level level, final String msg) {
        //if (!Thread.currentThread().getStackTrace()[2].getClassName())

        logp(level, msg, getCallerInfos(), null);
    }

    /**
     * Log a simple message with a level and color.
     *
     * @param level the level of logging
     * @param msg   the message to log
     * @param color the color to display the text in
     */
    public static void log(Level level, final String msg, Color color) {
        logp(level, msg, getCallerInfos(), color);
    }

    /**
     * Log the time elapsed to run a given method.
     *
     * @param start        the time at which the counter started in NANOSECONDS
     * @param end          the time at which the counter ended in NANOSECONDS
     * @param targetClass  the class of the method that was called
     * @param targetMethod the name of the method that was called
     */
    public static void logElapsedTime(long start, long end, Class<?> targetClass, String targetMethod) {
        // "class#method() took X UnitOfTimes"
        String msg = String.format("%s#%s() took %d %s",
                targetClass.getName().replace("fr.univ.engine.", ""),
                targetMethod,
                unit.convert(end - start, TimeUnit.NANOSECONDS),
                unit.toString());

        String[] infos = getCallerInfos();
        Color color = ColorFormatter.generateColor(infos[0], infos[1], targetClass.getName(), targetMethod);

        logp(Level.FINEST, msg, getCallerInfos(), color);
    }

    /**
     * Get the information that we want logged.
     * - 0: the name of the class who contain the method that called to log a message
     * - 1: the name of the method that called to log a message
     * - 2: the line that called to log a message
     *
     * @return a string array containing the relevant informations
     * @apiNote This method should be called by the first method called by the original caller
     * as going further will result in the wrong StackTraceElement being chosen.
     */
    private static String[] getCallerInfos() {
        String[] infos = new String[3];
        // 0 = thread, 1 = this method, 2 = caller of this method, 3 = true caller
        StackTraceElement s = Thread.currentThread().getStackTrace()[3];

        infos[0] = s.getClassName().replace("fr.univ.engine.", "");
        infos[1] = s.getMethodName();
        infos[2] = String.valueOf(s.getLineNumber());

        return infos;
    }

    /**
     * Log a message with it's different infos and a color.
     *
     * @param level the level of logging
     * @param msg   the message to log
     * @param infos the infos of this log
     * @param color the color of the message, null will let the default terminal color
     * @see #getCallerInfos() for the content of the infos array
     */
    private static void logp(Level level, String msg, String[] infos, Color color) {
        LogRecord record = new LogRecord(level, msg);

        record.setSourceClassName(infos[0]);
        record.setSourceMethodName(infos[1]);

        record.setParameters(new Object[]{infos[2], color});

        logger.log(record);
    }
}