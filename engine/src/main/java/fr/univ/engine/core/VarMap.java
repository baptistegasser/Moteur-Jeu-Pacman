package fr.univ.engine.core;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;

import java.util.HashMap;

/**
 * Map of variables.
 * A variable is represented as a string and it's value can be anything.
 */
public class VarMap {
    /**
     * The map storing the variables.
     */
    private final HashMap<String, ObjectProperty<Object>> variables;

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
        ObjectProperty<Object> property = variables.get(name);

        if (property == null) {
            property = new SimpleObjectProperty<>();
        }

        property.set(value);
        variables.put(name, property);
    }

    /**
     * Get a variable value.
     *
     * @param name the name of the variable.
     * @return the value or null if not found.
     */
    public Object get(String name) {
        ObjectProperty<Object> object = variables.get(name);
        return object == null ? null : object.get();
    }

    /**
     * Get a variable that is a int.
     *
     * @param name the name of the variable.
     * @return a int or null if not found.
     * @throws ClassCastException if the variable is not a int.
     */
    public Integer getInt(String name) {
        return getAs(Integer.class, name);
    }

    /**
     * Get a variable that is a double.
     *
     * @param name the name of the variable.
     * @return a double or null if not found.
     * @throws ClassCastException if the variable is not a double.
     */
    public Double getDouble(String name) {
        return getAs(Double.class, name);
    }

    /**
     * Get a variable that is a long.
     *
     * @param name the name of the variable.
     * @return a long or null if not found.
     * @throws ClassCastException if the variable is not a long.
     */
    public Long getLong(String name) {
        return getAs(Long.class, name);
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

    /**
     * Add a listener to call when a var change value.
     *
     * @param name the name of the variable to watch.
     * @param listener the change listener.
     */
    public void addListener(String name, ChangeListener<Object> listener) {
        variables.get(name).addListener(listener);
    }
}
