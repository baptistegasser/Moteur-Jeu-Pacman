package fr.univ.engine.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Vector} class.
 */
class VectorTest {
    private Vector vector;

    @BeforeEach
    void setUp() {
        vector = new Vector(1, 10);
    }

    @Test
    void constructors() {
        Vector doubleVector = new Vector(1, 2);
        Vector bigDecimalVector = new Vector(BigDecimal.valueOf(1), BigDecimal.valueOf(2));
        assertEquals(doubleVector, bigDecimalVector);
    }

    @Test
    void x() {
        assertEquals(BigDecimal.valueOf(1d), vector.x());
    }

    @Test
    void xValue() {
        assertEquals(1d, vector.xValue());
    }

    @Test
    void y() {
        assertEquals(BigDecimal.valueOf(10d), vector.y());
    }

    @Test
    void yValue() {
        assertEquals(10d, vector.yValue());
    }

    @Test
    void magnitude() {
        Vector v = new Vector(0, 10);
        assertEquals(BigDecimal.valueOf(10.0), v.magnitude());
    }

    @Test
    void magnitudeValue() {
        assertEquals(10.04987562112089, vector.magnitudeValue());
    }

    @Test
    void reverse() {
        Vector reverse = vector.reverse();

        assertNotSame(reverse, vector);
        assertEquals(-1d, reverse.xValue());
        assertEquals(-10d, reverse.yValue());
    }

    @Test
    void multiplyBy() {
        Vector v2 = new Vector(5d, 50d);
        assertEquals(v2, vector.multiplyBy(5));
        assertEquals(v2, vector.multiplyBy(BigDecimal.valueOf(5)));
    }

    @Test
    void sameOrientalDirection() {
        Vector v1 = new Vector(0,-3);
        Vector v2 = new Vector(2,0);

        assertFalse(vector.sameOrientalDirection(v1));
        assertTrue(vector.sameOrientalDirection(v2));

        Vector v3 = new Vector(1,2);
        Vector v4 = new Vector(2,3);
        Vector v5 = new Vector(-2,1);
        Vector v6 = new Vector(-2,-1);
        Vector v7 = new Vector(-1,-4);

        assertTrue(v3.sameOrientalDirection(v4));
        assertFalse(v3.sameOrientalDirection(v5));
        assertFalse(v5.sameOrientalDirection(v6));
        assertTrue(v6.sameOrientalDirection(v7));
    }

    @Test
    void getUnitVector() {
        Vector v = new Vector(0, 10);
        Vector unit = new Vector(0, 1);
        assertEquals(unit, v.getUnitVector());
    }

    @Test
    void sameDirection() {
        Vector v1 = new Vector(2, 3);
        Vector v2 = new Vector(16, 24);
        Vector v3 = new Vector(0, 0);
        assertFalse(v1.sameDirection(v3));
        assertTrue(v1.sameDirection(v2));
    }

    @Test
    void getVectorForPasteEdge() {
    }
}