package fr.univ.engine.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Vector} class.
 */
class VectorTest {
    private Vector vector;

    @BeforeEach
    void setUp() {
        vector = new Vector(0, 3);
    }

    @Test
    void x() {
        assertEquals(0, vector.x());
    }

    @Test
    void y() {
        assertEquals(3, vector.y());
    }

    @Test
    void magnitude() {
        assertEquals(3, vector.magnitude());
    }

    @Test
    void reverse() {
        Vector reverse = vector.reverse();

        assertNotSame(reverse, vector);
        assertEquals(-0.0, reverse.x());
        assertEquals(-3.0, reverse.y());
    }
}