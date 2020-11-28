package fr.univ.engine.core;

import fr.univ.engine.io.IOEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link VarMap} class.
 */
class VarMapTest {
    private static VarMap variables;

    @BeforeEach
    void setUp() {
        variables = new VarMap();
    }

    @Test
    void put() {
        assertNull(variables.get("test"));
        variables.put("test", Object.class);
        assertNotNull(variables.get("test"));
    }

    @Test
    void get() {
        assertNull(variables.get("test"));
        variables.put("test", Object.class);
        assertEquals(Object.class, variables.get("test"));
    }

    @Test
    void getInt() {
        assertNull(variables.getInt("myInt"));

        variables.put("myInt", 10);
        assertEquals(10, variables.getInt("myInt"));
        variables.put("object", new ArrayList<>());
        assertThrows(ClassCastException.class, () -> variables.getInt("object"));
    }

    @Test
    void getDouble() {
        assertNull(variables.getDouble("myDouble"));

        variables.put("myDouble", 10d);
        assertEquals(10d, variables.getDouble("myDouble"));
        variables.put("object", new ArrayList<>());
        assertThrows(ClassCastException.class, () -> variables.getDouble("object"));
    }

    @Test
    void getLong() {
        assertNull(variables.getLong("myLong"));

        variables.put("myLong", 10L);
        assertEquals(10L, variables.getLong("myLong"));
        variables.put("object", new ArrayList<>());
        assertThrows(ClassCastException.class, () -> variables.getLong("object"));
    }

    @Test
    void getString() {
        assertNull(variables.getString("myString"));

        variables.put("myString", "hello world");
        assertEquals("hello world", variables.getString("myString"));
        variables.put("object", new ArrayList<>());
        assertThrows(ClassCastException.class, () -> variables.getString("object"));
    }

    @Test
    void getAs() {
        // Check it does not fail if empty
        assertNull(variables.getAs(String.class, "anything"));

        List<String> list = new ArrayList<>();
        variables.put("myList", list);

        // Test to get with casting from lowest to highest parent class
        assertEquals(list, variables.getAs(ArrayList.class, "myList"));
        assertEquals(list, variables.getAs(List.class, "myList"));
        assertEquals(list, variables.getAs(Collection.class, "myList"));
        assertEquals(list, variables.getAs(Object.class, "myList"));
        assertThrows(ClassCastException.class, () -> variables.getAs(String.class, "myList"));
    }
}