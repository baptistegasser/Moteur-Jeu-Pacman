package fr.univ.engine.logging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link LoggingManager} class.
 */
class LoggingManagerTest {
    private LoggingManager manager;

    /**
     * Simple class to test difference in access between two classes.
     */
    private static class AnotherClass {}

    @BeforeEach
    void setUp() {
        manager = new LoggingManager();
    }

    @Test
    void checkLevel() {
        Arrays.asList(Level.SEVERE, Level.WARNING, Level.INFO).forEach(lvl -> assertTrue(manager.checkLevel(lvl)));
        Arrays.asList(Level.FINE, Level.FINER, Level.FINEST).forEach(lvl -> assertFalse(manager.checkLevel(lvl)));

        manager.setLevel(Level.SEVERE);
        Arrays.asList(Level.SEVERE).forEach(lvl -> assertTrue(manager.checkLevel(lvl)));
        Arrays.asList(Level.WARNING, Level.INFO, Level.FINE, Level.FINER, Level.FINEST).forEach(lvl -> assertFalse(manager.checkLevel(lvl)));
    }

    @Test
    void checkName() {
        manager.setExplicitMode(true);

        String dontExist = "dont.exist";
        assertFalse(manager.checkName(dontExist));
        String thisPackage = "fr.univ.engine.logging";
        assertFalse(manager.checkName(thisPackage));
        String thisClassName = "fr.univ.engine.logging.LoggingManagerTest";
        assertFalse(manager.checkName(thisClassName));

        // Don't care if it exist, just matching
        manager.allow(dontExist);
        assertTrue(manager.checkName(dontExist));

        // Check that allowing a package will allow subpackages/classes
        manager.allow(thisPackage);
        assertTrue(manager.checkName(thisPackage));
        assertTrue(manager.checkName(thisClassName));
    }

    @Test
    void checkClass() {
        // Set explicit, by default both class will fail checks
        manager.setExplicitMode(true);

        // Allow only one
        manager.allow(AnotherClass.class);
        assertTrue(manager.checkClass(AnotherClass.class));
        assertFalse(manager.checkClass(LoggingManager.class));

        // Allow the second one
        manager.allow(LoggingManager.class);
        assertTrue(manager.checkClass(LoggingManager.class));

        // Disallow both
        manager.disallow(AnotherClass.class, LoggingManager.class);
        assertFalse(manager.checkClass(AnotherClass.class));
        assertFalse(manager.checkClass(LoggingManager.class));

        // Allow package that superset both classes
        manager.allow("fr.univ.engine.logging");
        assertTrue(manager.checkClass(AnotherClass.class));
        assertTrue(manager.checkClass(LoggingManager.class));
    }

    @Test
    void explicitModeFalse() {
        manager.setExplicitMode(false);
        assertTrue(manager.checkClass(AnotherClass.class));
        assertTrue(manager.checkClass(LoggingManagerTest.class));
        assertTrue(manager.checkName("any.class.can.log.Main"));
    }

    @Test
    void explicitModeTrue() {
        manager.setExplicitMode(true);

        assertFalse(manager.checkClass(AnotherClass.class));
        manager.allow(AnotherClass.class);
        assertTrue(manager.checkClass(AnotherClass.class));

        assertFalse(manager.checkClass(LoggingManagerTest.class));
        manager.allow(LoggingManagerTest.class);
        assertTrue(manager.checkClass(LoggingManagerTest.class));

        assertFalse(manager.checkName("any.class.can.log.Main"));
        manager.allow("any.class.can.log.Main");
        assertTrue(manager.checkName("any.class.can.log.Main"));
    }

    @Test
    void assertDefaultLevelSet() {
        assertEquals(Level.INFO, manager.getLevel());
    }

    @Test
    void setAndGetLevel() {
        manager.setLevel(Level.FINEST);
        assertEquals(Level.FINEST, manager.getLevel());
    }


    @Test
    void singleNameOperation() {
        manager.setExplicitMode(true);

        manager.allow("FOO.package");
        assertTrue(manager.checkName("FOO.package"));

        manager.disallow("FOO.package");
        assertFalse(manager.checkName("FOO.package"));
    }

    @Test
    void singleClassOperation() {
        manager.setExplicitMode(true);

        manager.allow(AnotherClass.class);
        assertTrue(manager.checkClass(AnotherClass.class));

        manager.disallow(AnotherClass.class);
        assertFalse(manager.checkClass(AnotherClass.class));
    }

    @Test
    void multipleNamesOperation() {
        manager.setExplicitMode(true);

        manager.allow("FOO.package", "BAR.package");
        assertTrue(manager.checkName("FOO.package"));
        assertTrue(manager.checkName("BAR.package"));

        manager.disallow("FOO.package", "BAR.package");
        assertFalse(manager.checkName("FOO.package"));
        assertFalse(manager.checkName("BAR.package"));
    }

    @Test
    void multipleClassesOperation() {
        manager.setExplicitMode(true);

        manager.allow(AnotherClass.class, LoggingManagerTest.class);
        assertTrue(manager.checkClass(AnotherClass.class));
        assertTrue(manager.checkClass(LoggingManagerTest.class));

        manager.disallow(AnotherClass.class, LoggingManagerTest.class);
        assertFalse(manager.checkClass(AnotherClass.class));
        assertFalse(manager.checkClass(LoggingManagerTest.class));
    }
}