package fr.univ.engine.logging;

import java.io.OutputStream;
import java.util.logging.*;

/**
 * Hide implementation and config from other classes.
 * Log level is set as INFO by default.
 *
 * "I mean, it's boring who would want to see this", Baptiste G. 2020
 */
class LoggerSingleton {
    private static final Logger logger;

    static {
        // Init logger
        logger = Logger.getLogger(LoggerSingleton.class.getName());
        logger.setLevel(Level.INFO);

        // Use custom handler and prevent default handlers
        logger.addHandler(new FlushedStreamHandler());
        logger.setUseParentHandlers(false);
    }

    public static Logger getLogger() {
        return logger;
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
