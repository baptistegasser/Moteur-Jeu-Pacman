package fr.univ.engine.logging;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the {@link DisplayColorTest} class defining valid display colors.
 */
class DisplayColorTest {
    /**
     * Test getting a color with an object.
     */
    @Test
    void getObject() {
        assertEquals(DisplayColor.get(42), DisplayColor.get("Aa"));
        assertEquals(DisplayColor.get(19), DisplayColor.get("Aaa")); // Slight difference in the value but hashcode change
    }

    /**
     * Test that for the same hash we get the same result.
     */
    @Test
    void getObjectSameHash() {
        assertEquals(DisplayColor.get("BB"), DisplayColor.get("BB")); // Same object
        assertEquals(DisplayColor.get("Aa"), DisplayColor.get("BB")); // Same hash
    }
}