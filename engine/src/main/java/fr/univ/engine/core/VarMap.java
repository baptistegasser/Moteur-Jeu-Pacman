package fr.univ.engine.core;

import java.util.HashMap;

/**
 * Map of variables.
 * This map a variable represented as a string to a value.
 * The target value can be any object.
 */
public class VarMap {
    /**
     * The actual map.
     */
    private final HashMap<String, Object> variables;

    public VarMap() {
        variables = new HashMap<>();
    }

    /**
     * Set a variable value.
     *
     * @param name the name of the variable.
     * @param value the value of the variable.
     */
    public void put(String name, Object value) {
        variables.put(name, value);
    }

    /**
     * Get a variable value.
     *
     * @param name the name of the variable.
     * @return the value or null if not found.
     */
    public Object get(String name) {
        return variables.get(name);
    }

    /**
     * Get a variable that is a int.
     *
     * @param name the name of the variable.
     * @return a int or null if not found.
     * @throws ClassCastException if the variable is not a int.
     */
    public int getInt(String name) {
        return getAs(int.class, name);
    }

    /**
     * Get a variable that is a double.
     *
     * @param name the name of the variable.
     * @return a double or null if not found.
     * @throws ClassCastException if the variable is not a double.
     */
    public double getDouble(String name) {
        return getAs(double.class, name);
    }

    /**
     * Get a variable that is a long.
     *
     * @param name the name of the variable.
     * @return a long or null if not found.
     * @throws ClassCastException if the variable is not a long.
     */
    public long getLong(String name) {
        return getAs(long.class, name);
    }

    /**
     * Get a variable that is a string.
     *
     * @param name the name of the variable.
     * @return a string or null if not found.
     * @throws ClassCastException if the variable is not a String.
     */
    public String getString(String name) {
        return getAs(String.class, name);
    }

    /**
     * Get a variable value as a certain class.
     *
     * @param clazz the class implementing the wanted type.
     * @param name the name of the variable.
     * @param <T> the type of the variable.
     * @return the value or null if not found.
     * @throws ClassCastException if the variable is not a subclass of clazz.
     */
    public <T> T getAs(Class<T> clazz, String name) {
        Object o = get(name);
        if (o == null) {
            return null;
        } else {
            return clazz.cast(o);
        }
    }
}
