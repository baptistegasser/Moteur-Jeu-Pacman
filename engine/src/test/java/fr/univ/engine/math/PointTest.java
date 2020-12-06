package fr.univ.engine.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Point} class.
 */
class PointTest {
    private Point point;

    @BeforeEach
    void setUp() {
        point = new Point(1, 2);
    }

    @Test
    void constructor() {
        assertEquals(new Point(1, 2), new Point(BigDecimal.valueOf(1), BigDecimal.valueOf(2)));
    }

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

    @Test
    void x() {
        assertEquals(BigDecimal.valueOf(1.0), point.x());
    }

    @Test
    void xValue() {
        assertEquals(1, point.xValue());
    }

    @Test
    void y() {
        assertEquals(BigDecimal.valueOf(2.0), point.y());
    }

    @Test
    void yValue() {
        assertEquals(2, point.yValue());
    }

    @Test
    void setX() {
        point.setX(10);
        assertEquals(10, point.xValue());
    }

    @Test
    void setXBigDecimal() {
        point.setX(BigDecimal.valueOf(10));
        assertEquals(10, point.xValue());
    }

    @Test
    void incrementX() {
        point.incrementX();
        assertEquals(2, point.xValue());
    }

    @Test
    void incrementXDouble() {
        point.incrementX(2);
        assertEquals(3, point.xValue());
    }

    @Test
    void incrementXBigDecimal() {
        point.incrementX(BigDecimal.valueOf(2));
        assertEquals(3, point.xValue());
    }

    @Test
    void setY() {
        point.setY(10);
        assertEquals(10, point.yValue());
    }

    @Test
    void setYBigDecimal() {
        point.setY(BigDecimal.valueOf(10));
        assertEquals(10, point.yValue());
    }

    @Test
    void incrementY() {
        point.incrementY();
        assertEquals(3, point.yValue());
    }

    @Test
    void incrementYDouble() {
        point.incrementY(2);
        assertEquals(4, point.yValue());
    }

    @Test
    void incrementYBigDecimal() {
        point.incrementY(BigDecimal.valueOf(2));
        assertEquals(4, point.yValue());
    }

    @Test
    void distance() {
        Point a = new Point(0, 1);
        Point b = new Point(10, 1);
        assertEquals(10, a.distance(b));
    }
}