package fr.univ.engine.physic;

/**
 * A Physic entity is an entity that can be subject to physics.
 * It provide informations in the form of a {@link PhysicObject} that contain necessary
 * elements to update the instance physics.
 */
public interface PhysicEntity {
    /**
     * @return the PhysicObject associated to this entity
     */
    PhysicObject getPhysicObject();

    /**
     * Fixed update that are called every time the {@link PhysicEngine#integrate}
     * method is called, allow to have frame independent operations.
     * The default implementation do nothing.
     *
     * @param t the elapsed time since the engine started
     * @param dt the delta time between calls to {@link PhysicEngine#integrate}
     */
    default void fixedUpdate(double t, double dt) {
    }
}
