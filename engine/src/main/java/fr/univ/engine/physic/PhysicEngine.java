package fr.univ.engine.physic;

import fr.univ.engine.core.Core;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.collision.CollisionHandler;
import fr.univ.engine.physic.collision.CollisionHandlerWrapper;
import fr.univ.engine.physic.component.PhysicComponent;
import fr.univ.engine.physic.hitbox.HitBox;

import java.util.ArrayList;
import java.util.List;

/**
 * The engine in charge of handling physics in the game.
 */
public class PhysicEngine {
    /**
     * The core engine managing this instance.
     * Keep a reference to access the current level
     * for method requested by user.
     */
    private final Core core;

    /**
     * List of handler for different kinds of collision.
     */
    private final List<CollisionHandlerWrapper> collisionHandlers;

    public PhysicEngine(Core core) {
        this.core = core;
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
     * Fonction called at a fixed interval dt to update physic.
     *
     * @param objects the list of game objects int the game
     */
    public void integrate(List<Entity> objects) {
        // Move all of object
        List<Entity> movedObjects = move(objects);
        // Check for collision
        for (Entity o : movedObjects) {
            collision(objects, o);
        }
    }

    /**
     * Function call for object movement in mainLoop
     *
     * @param objects all Object in a game
     * @return only object that have moved
     */
    private List<Entity> move(List<Entity> objects) {
        List<Entity> returnObject = new ArrayList<>();

        for (Entity object : objects) {
            PhysicComponent component = object.getComponent(PhysicComponent.class);
            if (component.direction().magnitude() != 0) {
                returnObject.add(object);
                // displacement for physic and render
                object.transform().getPosition().add(component.direction());
            }
        }

        return returnObject;
    }

    /**
     * Function called to detect collision between 2 objects, and active onTriggerEnter
     *
     * @param objects the list of game objects int the game
     * @param object Object we want to test
     */
    private void collision(List<Entity> objects, Entity object) {
        for (Entity target : objects) {
            PhysicComponent targetPhysic = target.getComponent(PhysicComponent.class);
            if (targetPhysic.getHitBox() == null) continue;

            // Don't test on same object and on empty object
            if (target != object) {
                PhysicComponent objectPhysic = object.getComponent(PhysicComponent.class);

                if (objectPhysic.getHitBox().intersect(targetPhysic.getHitBox())) {
                    // Rollback pos if hit a solid object
                    if (targetPhysic.getHitBox().isSolid()) {
                        object.transform().getPosition().add(objectPhysic.direction().reverse());
                    }

                    for (CollisionHandler handler : getCollisionHandlers(object.type(), target.type())) {
                        handler.handleCollision(object, target);
                    }
                }
            }
        }
    }

    public boolean canMoveTo(Entity entity, Point target) {
        HitBox entityBox = entity.getComponent(PhysicComponent.class).getHitBox();
        Point oldPos = entity.transform().getPosition();
        entity.transform().setPosition(target);

        boolean intersect = false;
        List<Entity> entities = core.getLevel().getEntitiesWithComponent(PhysicComponent.class);
        for (Entity e : entities) {
            HitBox targetBox = e.getComponent(PhysicComponent.class).getHitBox();
            if (entity == e || targetBox.isSolid()) continue;

            if (targetBox.intersect(entityBox)) {
                intersect = true;
                break;
            }
        }

        entity.transform().setPosition(oldPos);
        return intersect;
    }
}
