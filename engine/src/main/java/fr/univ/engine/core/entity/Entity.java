package fr.univ.engine.core.entity;

import fr.univ.engine.core.component.Component;
import fr.univ.engine.math.Transform;

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
     * The transform applied on this object.
     */
    private final Transform transform;
    /**
     * A list of components defining the behavior of this entity.
     */
    private final List<Component> components;

    public Entity(Object type, Transform transform, List<Component> components) {
        this.type = type;
        this.transform = transform;
        this.components = components;
    }

    /**
     * @return the type of this entity.
     */
    public Object type() {
        return type;
    }

    /**
     * @return the transform applied on this object.
     */
    public Transform transform() {
        return transform;
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
