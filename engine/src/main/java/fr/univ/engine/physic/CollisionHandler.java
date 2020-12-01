package fr.univ.engine.physic;

import fr.univ.engine.core.entity.Entity;

/**
 * Functional interface to handle collision between entities.
 */
@FunctionalInterface
public interface CollisionHandler {
    /**
     * Handle collision between two entities.
     *
     * @param e1 the first entity.
     * @param e2 the second entity.
     */
    void handleCollision(Entity e1, Entity e2);
}
