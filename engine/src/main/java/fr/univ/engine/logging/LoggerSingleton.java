package fr.univ.engine.logging;

import java.io.OutputStream;
import java.util.logging.*;

/**
 * Singleton logger used by the logging engine.
 * Hide implementation and config from other classes.
 * Log level is set as INFO by default.
 *
 * "I mean, it's boring who would want to see this", Baptiste G. 2020
 */
class LoggerSingleton extends Logger {
    /**
     * The singleton instance.
     */
    private static LoggerSingleton instance;
    /**
     * Keep a reference to the Logger handler
     */
    private final FlushedStreamHandler handler;

    private LoggerSingleton() {
        super(LoggerSingleton.class.getName(), null);
        // Init logger
        setLevel(Level.INFO);

        // Use custom handler and prevent default handlers
        handler = new FlushedStreamHandler();
        addHandler(handler);
        setUseParentHandlers(false);
    }

    /**
     * @see ColorFormatter#setAutoColor(boolean)
     */
    public void setAutoColor(boolean autoColor) {
        ((ColorFormatter) handler.getFormatter()).setAutoColor(autoColor);
    }

    /**
     * @return this singleton instance.
     */
    public static LoggerSingleton getInstance() {
        if (instance == null) {
            instance = new LoggerSingleton();
        }
        return instance;
    }

    /**
     * A modified {@code StreamHandler} that:
     * - use our custom formatter {@link ColorFormatter}
     * - force flushing after a record is published.
     */
    private static final class FlushedStreamHandler extends StreamHandler {
        public FlushedStreamHandler() {
            this(System.out);
        }

        public FlushedStreamHandler(OutputStream out) {
            super(out, new ColorFormatter());
            this.setLevel(Level.ALL);
        }

        @Override
        public synchronized void publish(LogRecord record) {
            super.publish(record);
            flush(); // Force the flush on publish
        }
    }

}
