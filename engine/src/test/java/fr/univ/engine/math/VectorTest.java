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
        double speed1 = 1.0;

        // Test without speed

        Vector v1 = new Vector(0, 1);
        Vector v2 = new Vector(0, 0.6);

        assertEquals(v1.getVectorForPasteEdge(v2, speed1), new Vector(0,0.4));

        Vector v3 = new Vector(-1, 10);
        Vector v4 = new Vector(0.4, 2);

        assertEquals(v3.getVectorForPasteEdge(v4, speed1), new Vector(-0.6,0));

        Vector v5 = new Vector(-1, 0);
        Vector v6 = new Vector(-1, 0);

        assertNull(v5.getVectorForPasteEdge(v6, speed1));

        Vector v7 = new Vector(-1, 0);
        Vector v8 = new Vector(1, 0);

        assertEquals(v7.getVectorForPasteEdge(v8, speed1), new Vector(0,0));

        Vector v9 = new Vector(-1, 1);
        Vector v10 = new Vector(-1, -1);

        assertEquals(v9.getVectorForPasteEdge(v10, speed1), new Vector(0,0));

        Vector v11 = new Vector(248.369, -921.3);
        Vector v12 = new Vector(158.475, 48.29);

        assertEquals(v11.getVectorForPasteEdge(v12, speed1), new Vector(89.894,0));

        // Test with speed

        Vector v13 = new Vector(0, 1);
        Vector v14 = new Vector(0.5, 1);

        assertEquals(v13.getVectorForPasteEdge(v14, speed1), new Vector(-0.5,1));

        double speed2 = 0.7;

        Vector v15 = new Vector(0.7, 0);
        Vector v16 = new Vector(0.7, 0.4);

        assertEquals(v15.getVectorForPasteEdge(v16, speed2), new Vector(0.7,-0.4));

        Vector v17 = new Vector(0.7, 0);
        Vector v18 = new Vector(0.7, 0.9);

        assertNull(v17.getVectorForPasteEdge(v18, speed1));

        Vector v19 = new Vector(0.7, 0);
        Vector v20 = new Vector(0.7, -0.4);

        assertEquals(v19.getVectorForPasteEdge(v20, speed2), new Vector(0.7,0.4));

        Vector v21 = new Vector(0.7, 0);
        Vector v22 = new Vector(0.7, -0.9);

        assertNull(v21.getVectorForPasteEdge(v22, speed1));

        Vector v23 = new Vector(0.7, 0);
        Vector v24 = new Vector(0.8, 0.4);

        assertEquals(v23.getVectorForPasteEdge(v24, speed2), new Vector(-0.1,0));

        Vector v25 = new Vector(0.8, 0);
        Vector v26 = new Vector(0, 0.4);

        assertNull(v25.getVectorForPasteEdge(v26, speed1));

        Vector v27 = new Vector(0.7, 0.4);
        Vector v28 = new Vector(0.7, 0.4);

        assertEquals(v27.getVectorForPasteEdge(v28, speed2), new Vector(0.7,-0.4));

        Vector v29 = new Vector(0.7, 0.4);
        Vector v30 = new Vector(0.7, -0.4);

        assertEquals(v29.getVectorForPasteEdge(v30, speed2), new Vector(0,0));

        Vector v31 = new Vector(0.7, -0.4);
        Vector v32 = new Vector(0.7, -0.2);

        assertEquals(v31.getVectorForPasteEdge(v32, speed2), new Vector(0,-0.2));
    }
}