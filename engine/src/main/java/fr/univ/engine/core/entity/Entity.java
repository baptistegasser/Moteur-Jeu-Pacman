package fr.univ.engine.core.entity;

import fr.univ.engine.core.component.Component;

import java.util.List;

/**
 * Representation of anything in the game.
 */
public class Entity {
    /**
     * A type that allow to a game to identify what this entity represent.
     */
    private final Object type;
    /**
     * A list of components defining the behavior of this entity.
     */
    private final List<Component> components;

    public Entity(Object type, List<Component> components) {
        this.type = type;
        this.components = components;

        components.forEach(c -> c.setEntity(this));
    }

    /**
     * @return the type of this entity.
     */
    public Object type() {
        return type;
    }

    /**
     * Get all the components of this entity.
     *
     * @return the list of components.
     */
    public List<Component> getComponents() {
        return components;
    }

    /**
     * Get a component by it's class.
     *
     * @param componentClass the class implementing the type T.
     * @param <T> the type of the component.
     * @return the first component matching the class or null.
     */
    public <T extends Component> T getComponent(Class<T> componentClass) {
        final String name = componentClass.getName();
        for (Component c : components) {
            if (c.getClass().getName().equals(name)) {
                //noinspection unchecked
                return (T) c;
            }
        }
        return null;
    }

    /**
     * Check if this entity have a specific component.
     *
     * @param componentClass the tye of the component.
     * @return true if their is a non null component matching the wanted type.
     */
    public boolean hasComponent(Class<? extends Component> componentClass) {
        final String name = componentClass.getName();
        for (Component c : components) {
            if (c != null && c.getClass().getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
