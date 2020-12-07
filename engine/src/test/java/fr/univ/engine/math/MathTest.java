package fr.univ.engine.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Math} class.
 */
class MathTest {
    @Test
    void clamp() {
        assertEquals(1, Math.clamp(1, 100, -420));
        assertEquals(10, Math.clamp(1, 10, 420));
        assertEquals(4, Math.clamp(1, 10, 4));
    }
}