package fr.univ.engine.core.component;

import fr.univ.engine.core.entity.Entity;

/**
 * Base component that should be implemented by any other entity's components.
 */
public abstract class Component {
    /**
     * The entity instance containing this component.
     */
    private Entity entity;

    /**
     * Method called once every frame.
     */
    public void update() {}

    /**
     * Set the entity instance containing this component.
     *
     * @param entity the new entity.
     */
    public final void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * Get a component from the entity that contain this component.
     *
     * @param componentClass the class implementing the type T.
     * @param <T> the type of the component.
     * @return the first component matching the class or null.
     */
    public final <T extends Component> T getComponent(Class<T> componentClass) {
        return entity.getComponent(componentClass);
    }
}
