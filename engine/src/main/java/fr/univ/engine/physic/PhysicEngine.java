package fr.univ.engine.physic;

import fr.univ.engine.core.Core;
import fr.univ.engine.core.TransformComponent;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.hitbox.HitBox;
import fr.univ.engine.physic.hitbox.HitBoxIntersecter;

import java.util.ArrayList;
import java.util.List;

/**
 * The engine in charge of handling physics in the game.
 */
public class PhysicEngine {
    /**
     * The core engine managing this instance.
     * Keep a reference to access the current level for method requested by user.
     */
    private final Core core;

    /**
     * List of handler for different kinds of collision.
     */
    private List<CollisionHandlerWrapper> collisionHandlers;

    public PhysicEngine(Core core) {
        this.core = core;
    }

    /**
     * Initialize the engine values before a new game.
     */
    public void init() {
        this.collisionHandlers = new ArrayList<>();
    }

    /**
     * Add a new handler for collisions of a given types of entities.
     *
     * @param type1 the type of the first entity.
     * @param type2 the type of the second entity.
     * @param handler the handler.
     */
    public void onCollision(Object type1, Object type2, CollisionHandler handler) {
        this.collisionHandlers.add(new CollisionHandlerWrapper(type1, type2, handler));
    }

    /**
     * Return all handler capable of handle collision between two given entity types.
     *
     * @param t1 the type of the first entity.
     * @param t2 the type of the second entity.
     * @return a list of capable handlers, might be empty if none match the types.
     */
    private List<CollisionHandler> getCollisionHandlers(Object t1, Object t2) {
        List<CollisionHandler> handlers = new ArrayList<>();
        for (CollisionHandlerWrapper wrapper : collisionHandlers) {
            if (wrapper.canHandle(t1, t2)) {
                handlers.add(wrapper.getHandler());
            }
        }
        return handlers;
    }

    /**
     * Update the physic state of a given list of entities.
     *
     * @param entities the list of entities to update.
     */
    public void integrate(List<Entity> entities) {
        // Move all of object
        List<Entity> movedEntities = updatePositions(entities);

        // Check for collision
        for (Entity e : movedEntities) {
            checkCollisions(entities, e);
        }
    }

    /**
     * Update the position of a given list of entities.
     *
     * @param entities the list of entities to move.
     * @return the list of entities that moved.
     */
    private List<Entity> updatePositions(List<Entity> entities) {
        List<Entity> movedEntities = new ArrayList<>();

        // Move object based on their direction
        for (Entity entity : entities) {
            boolean moved = updatePosition(entity);
            if (moved) {
                movedEntities.add(entity);
            }
        }

        return movedEntities;
    }

    /**
     * Update the position of a single entity.
     *
     * @param entity the entity to move.
     * @return true if the entity moved, false otherwise.
     */
    private boolean updatePosition(Entity entity) {
        Vector direction = entity.getComponent(PhysicComponent.class).direction();
        if (direction.magnitudeValue() != 0) {
            entity.getComponent(TransformComponent.class).position().add(direction);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if there is collisions an entity and a list of entities to test.
     *
     * @param entities the list of entities.
     * @param entity the entity to test.
     */
    private void checkCollisions(List<Entity> entities, Entity entity) {
        final HitBox h1 = entity.getComponent(PhysicComponent.class).getHitBox();
        final Point p1 = entity.getComponent(TransformComponent.class).position();
        // Ignore if entity have no hitbox
        if (h1 == null) {
            return;
        }

        for (Entity e2 : entities) {
            HitBox h2 = e2.getComponent(PhysicComponent.class).getHitBox();
            Point p2 = e2.getComponent(TransformComponent.class).position();

            // Ignore if e2 is the tested entity or have no hitbox
            if (entity == e2 || h2 == null) {
                continue;
            }

            if (HitBoxIntersecter.intersect(h1, p1, h2, p2)) {
                // Rollback pos if hit a solid entity
                if (h2.isSolid()) {
                    p1.add(entity.getComponent(PhysicComponent.class).direction().reverse());
                }

                // Call the handlers
                for (CollisionHandler handler : getCollisionHandlers(entity.type(), e2.type())) {
                    handler.handleCollision(entity, e2);
                }
                // Call the handlers is set in other side
                for (CollisionHandler handler : getCollisionHandlers(e2.type(), entity.type())) {
                    handler.handleCollision(e2, entity);
                }
            }
        }
    }

    /**
     * Test if an entity can move to the specified position.
     *
     * @param e the entity.
     * @param targetPos the destination.
     * @return true if the entity can move without colliding with a solid entity.
     */
    public boolean canMoveTo(Entity e, Point targetPos) {
        HitBox hb = e.getComponent(PhysicComponent.class).getHitBox();

        // Entity without hitbox never collide
        if (hb == null) {
            return true;
        }

        List<Entity> entities = core.getLevel().getEntitiesWithComponent(PhysicComponent.class);
        for (Entity e1 : entities) {
            HitBox hb1 = e1.getComponent(PhysicComponent.class).getHitBox();

            // Ignore if self, target have no hitbox or is not solid
            if (e1 == e || hb1 == null || !hb1.isSolid()) {
                continue;
            }

            if (HitBoxIntersecter.intersect(hb, targetPos, hb1, e1.getComponent(TransformComponent.class).position())) {
                return false;
            }
        }

        return true;
    }
}
