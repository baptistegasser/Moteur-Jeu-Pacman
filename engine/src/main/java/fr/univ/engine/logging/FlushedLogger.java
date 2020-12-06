package fr.univ.engine.logging;

import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * A simple logger that force flushing messages.
 * Flushing each message increment cost but reduce time
 * waited before seing a message.
 * Log level is set as INFO by default.
 *
 * "I mean, it's boring who would want to see this", Baptiste G. 2020
 */
class FlushedLogger extends Logger {
    /**
     * Keep a reference to the Logger handler
     */
    private final FlushedStreamHandler handler;

    public FlushedLogger() {
        super(FlushedLogger.class.getName(), null);

        // Use custom handler and prevent default handlers
        handler = new FlushedStreamHandler();
        addHandler(handler);
        setUseParentHandlers(false);

        // Init logger
        setLevel(Level.INFO);
    }

    @Override
    public void setLevel(Level newLevel) throws SecurityException {
        super.setLevel(newLevel);
        handler.setLevel(newLevel);
    }

    /**
     * @param autoColor true if we want to use auto coloration.
     * @see ColorFormatter#setAutoColor(boolean)
     */
    public void setAutoColor(boolean autoColor) {
        ((ColorFormatter) handler.getFormatter()).setAutoColor(autoColor);
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
        }

        @Override
        public synchronized void publish(LogRecord record) {
            super.publish(record);
            flush(); // Force the flush on publish
        }
    }
}
