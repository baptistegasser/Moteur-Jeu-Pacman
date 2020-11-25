package fr.univ.engine.logging;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;

/**
 * The logging manager is a class that allows to define which messages
 * should be logged. It allow the logging engine to determine if the message
 * should be logged based on its sender and log level.
 *
 * Implementation consist in allowing all, except explicitly blocked packages.
 */
public class LoggingManager {
    /**
     * The current logging level.
     * Log with a lower level don't even deserve to be logged
     * as they won't be saved nor displayed.
     */
    private Level level;

    /**
     * Define if in explicit mode.
     * <ul>
     *     <li><p>True. all classes and packages must be explicitly allowed to log
     *     via the {@link #allow(String)} method.</p></li>
     *     <li><p>False. all classes from any packages are allowed to log.</p></li>
     * </ul>
     */
    private boolean explicitMode;
    /**
     * Store classes and packages names that are explicitly allowed to log messages.
     */
    private final ArrayList<String> explicitlyAllowed = new ArrayList<>();
    /**
     * A set that stores the same values as {@link #explicitlyAllowed} but allow for
     * fast {@link HashSet#contains(Object)} calls to have unique value in the list.
     */
    private final HashSet<String> explicitlyAllowedUnique = new HashSet<>();


    /**
     * Init the loggingManager.
     */
    public LoggingManager() {
        level = Level.INFO; // Output INFO messages by default
        explicitMode = false; // Default allow everybody
    }

    /**
     * Check if a level is valid <-> if it's superior to the current level.
     * If a level is superior or equal to the current logging level it can
     * be displayed/saved, else, no.
     *
     * @param lvl the level to test
     * @return true if lvl is superior or equal to the current logging level
     */
    public boolean checkLevel(Level lvl) {
        return level.intValue() <= lvl.intValue();
    }

    /**
     * Check if a class or package have the right to log.
     *
     * @param name the class or package full name
     * @return true if the class or package might log, false otherwise
     */
    public boolean checkName(String name) {
        // Check global allowance
        if (!explicitMode) {
            return true;
        }

        for (String pkg : explicitlyAllowed) {
            if (name.startsWith(pkg)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a class have the right to log.
     *
     * @param clazz the class
     * @return true if the class might log, false otherwise
     */
    public boolean checkClass(Class<?> clazz) {
        return checkName(clazz.getName());
    }

    /**
     * Set the {@link #explicitMode} value.
     *
     * @param explicitMode the new value
     */
    public void setExplicitMode(boolean explicitMode) {
        this.explicitMode = explicitMode;
    }

    /**
     * Allow a class or package to log.
     *
     * @param name the class or package full name
     */
    public void allow(String name) {
        if (!explicitlyAllowedUnique.contains(name)) {
            explicitlyAllowed.add(name);
            explicitlyAllowedUnique.add(name);
        }
    }

    /**
     * Allow multiple classes or packages to log.
     *
     * @param names the classes or packages full names
     */
    public void allow(String... names) {
        for (String name : names) {
            allow(name);
        }
    }

    /**
     * Allow a class to log.
     *
     * @param clazz the class
     */
    public void allow(Class<?> clazz) {
        allow(clazz.getName());
    }

    /**
     * Allow multiple classes to log.
     *
     * @param classes the classes
     */
    public void allow(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            allow(clazz);
        }
    }

    /**
     * Prevent a class or package from further logging.
     *
     * @param name the class or package full name
     */
    public void disallow(String name) {
        if (explicitlyAllowedUnique.contains(name)) {
            explicitlyAllowed.remove(name);
            explicitlyAllowedUnique.remove(name);
        }
    }

    /**
     * Prevent multiple classes or packages from further logging.
     *
     * @param names the classes or packages full names
     */
    public void disallow(String... names) {
        for (String name : names) {
            disallow(name);
        }
    }

    /**
     * Prevent a class from further logging.
     *
     * @param clazz the class
     */
    public void disallow(Class<?> clazz) {
        disallow(clazz.getName());
    }

    /**
     * Prevent multiple classes from further logging.
     *
     * @param classes the classes
     */
    public void disallow(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            disallow(clazz.getName());
        }
    }

    /**
     * @return the current logging level.
     */
    Level getLevel() {
        return level;
    }

    /**
     * Set the logging level.
     *
     * @param level the new level
     */
    void setLevel(Level level) {
        this.level = level;
    }
}
