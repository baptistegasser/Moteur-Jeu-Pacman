package fr.univ.engine.core.component;

import fr.univ.engine.core.GameApplication;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.physic.PhysicEngine;
import fr.univ.engine.sound.SoundEngine;

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
     * Method called on every integration of the physic engine.
     */
    public void fixedUpdate() {}

    /**
     * Set the entity instance containing this component.
     *
     * @param entity the new entity.
     */
    public final void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * Get the entity instance containing this component.
     *
     * @return the entity instance.
     */
    public final Entity getEntity() {
        return this.entity;
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

    /**
     * Easy accessor to the sound engine for components.
     *
     * @return the current sound engine of the running app.
     */
    protected final SoundEngine Sounds() {
        return GameApplication.app().soundEngine();
    }

    /**
     * Easy accessor to the physic engine for components.
     *
     * @return the current physic engine of the running app.
     */
    protected final PhysicEngine Physics() {
        return GameApplication.app().physicEngine();
    }
}
