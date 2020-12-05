package fr.univ.engine.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Point} class.
 */
class PointTest {

    @Test
    void copy() {
        Point a = new Point(0, 0);
        Point b = a.copy();

        assertEquals(a, b);
        assertNotSame(a, b);
    }

    @Test
    void add() {
        Point a = new Point(7, 3);
        a.add(new Vector(-2, 5));
        assertEquals(5, a.xValue());
        assertEquals(8, a.yValue());
    }

    @Test
    void set() {
        Point a = new Point(7, 3);
        a.set(new Point(4, 5));
        assertEquals(4, a.xValue());
        assertEquals(5, a.yValue());
    }

    @Test
    void testEquals() {
        Point a = new Point(0, 1);
        Point b = new Point(0, 2);

        assertFalse(a.equals(b) && b.equals(a));
        b.setY(1);
        assertTrue(a.equals(b) && b.equals(a));
    }
}