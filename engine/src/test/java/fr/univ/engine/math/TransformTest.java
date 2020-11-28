package fr.univ.engine.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Transform} class.
 */
class TransformTest {
    private Transform transform;

    @BeforeEach
    void setUp() {
        transform = new Transform(new Point(0, 0), 90);
    }

    @Test
    void getPosition() {
        Point position = transform.getPosition();
        assertEquals(0, position.x);
        assertEquals(0, position.y);
    }

    @Test
    void setPositionXY() {
        Point position = transform.getPosition();
        transform.setPosition(4, 5);
        assertSame(position, transform.getPosition());
        assertEquals(4, position.x);
        assertEquals(5, position.y);
    }

    @Test
    void setPositionPoint() {
        Point position = transform.getPosition();
        transform.setPosition(new Point(4, 5));
        assertSame(position, transform.getPosition());
        assertEquals(4, position.x);
        assertEquals(5, position.y);
    }

    @Test
    void getRotation() {
        assertEquals(90, transform.getRotation());
    }

    @Test
    void setRotation() {
        transform.setRotation(-180);
        assertEquals(-180, transform.getRotation());
    }
}