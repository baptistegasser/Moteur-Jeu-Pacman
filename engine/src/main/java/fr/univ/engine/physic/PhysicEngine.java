package fr.univ.engine.physic;

import fr.univ.engine.core.Core;
import fr.univ.engine.core.component.TransformComponent;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.collision.CollisionHandler;
import fr.univ.engine.physic.collision.CollisionHandlerWrapper;
import fr.univ.engine.physic.component.PhysicComponent;
import fr.univ.engine.physic.hitbox.HitBox;
import fr.univ.engine.physic.hitbox.SquareHitBox;

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
    private static Core core;

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
                object.getComponent(TransformComponent.class).position().add(component.direction());
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
                        object.getComponent(TransformComponent.class).position().add(objectPhysic.direction().reverse());
                    }

                    for (CollisionHandler handler : getCollisionHandlers(object.type(), target.type())) {
                        handler.handleCollision(object, target);
                    }
                }
            }
        }
    }

    public static boolean canMoveTo(PhysicComponent component, Point target) {
        HitBox hbox = component.getHitBox();
        Point oldPos = hbox.getPosition();
        hbox.setPosition(target);

        boolean canMove = true;
        List<Entity> entities = core.getLevel().getEntitiesWithComponent(PhysicComponent.class);
        for (Entity e : entities) {
            PhysicComponent c = e.getComponent(PhysicComponent.class);
            HitBox tbox = c.getHitBox();
            if (c != component && tbox != null && tbox.isSolid()) {
                if (hbox.intersect(tbox)) {
                    canMove = false;
                    break;
                }
            }
        }

        hbox.setPosition(oldPos);
        return canMove;
    }

    /**
     * Test if an entity can move to the specified position.
     *
     * @param e the entity.
     * @param targetPos the destination.
     * @return true if the entity can move without colliding with a solid entity.
     */
    public boolean canMoveTo(Entity e, Point targetPos) {
        HitBox entityHb = e.getComponent(PhysicComponent.class).getHitBox();

        // Entity without hitbox never collide
        if (entityHb == null) {
            return true;
        }

        // SquareHitBox for faster detection
        SquareHitBox hb = new SquareHitBox(entityHb.approximateSize());
        hb.setPosition(targetPos);

        List<Entity> entities = core.getLevel().getEntitiesWithComponent(PhysicComponent.class);
        for (Entity e1 : entities) {
            if (e1 == e) continue;
            HitBox hb1 = e.getComponent(PhysicComponent.class).getHitBox();
            if (hb1 == null || !hb1.isSolid()) continue;

            if (hb.intersect(hb1)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Calculate the distance between two entities.
     *
     * @param e1 first entity.
     * @param e2 second entity.
     * @return the distance between e1 and e2.
     */
    public double distanceTo(Entity e1, Entity e2) {
        Point p1 = e1.getComponent(TransformComponent.class).position();
        Point p2 = e2.getComponent(TransformComponent.class).position();

        double x = p1.x - p2.x;
        double y = p1.y - p2.y;
        double distance = Math.sqrt(x*x + y*y);
        return Math.abs(distance);
    }
}
