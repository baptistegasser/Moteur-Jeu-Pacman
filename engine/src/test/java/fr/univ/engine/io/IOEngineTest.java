package fr.univ.engine.io;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link IOEngine} class.
 */
class IOEngineTest {
    private static KeyEvent Z_PRESSED;
    private static KeyEvent S_RELEASED;
    private static IOEngine ioEngine;

    @BeforeAll
    static void beforeAll() {
        ioEngine = new IOEngine();
        ioEngine.init();
        Z_PRESSED = new KeyEvent(null, null, null, null, null, KeyCode.Z, false, false, false, false);
        S_RELEASED = new KeyEvent(null, null, null, null, null, KeyCode.S, false, false, false, false);
    }

    @Test
    void nextFrame() {
        assertDoesNotThrow(ioEngine::nextFrame);
    }

    @Test
    void onKeyPressed() {
        AtomicBoolean pressed = new AtomicBoolean(false);
        assertDoesNotThrow(() -> ioEngine.onKeyPressed(KeyCode.Z, () -> pressed.set(true)));
        ioEngine.notifyKeyPressed(Z_PRESSED);
        assertFalse(pressed.get());
        ioEngine.nextFrame();
        assertTrue(pressed.get());
    }

    @Test
    void onKeyReleased() {
        AtomicBoolean released = new AtomicBoolean(false);
        assertDoesNotThrow(() -> ioEngine.onKeyReleased(KeyCode.S, () -> released.set(true)));
        ioEngine.notifyKeyReleased(S_RELEASED);
        assertFalse(released.get());
        ioEngine.nextFrame();
        assertTrue(released.get());
    }
}