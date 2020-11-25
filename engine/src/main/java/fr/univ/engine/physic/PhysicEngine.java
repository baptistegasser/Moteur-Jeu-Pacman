package fr.univ.engine.physic;

import fr.univ.engine.core.Core;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.hitbox.HitBox;

import java.util.ArrayList;
import java.util.List;

public class PhysicEngine {

    /**
     * Function call for object movement in mainLoop
     *
     * @param objects all Object in a game
     * @param t the time elapsed since engine started
     * @param dt the time-step elapsed between updates
     * @return only object that have moved
     */
    private List<PhysicEntity> move(List<PhysicEntity> objects, double t, double dt) {
        List<PhysicEntity> returnObject = new ArrayList<>();

        for (PhysicEntity object : objects) {
            object.fixedUpdate(t, dt);
            if (object.getPhysicObject().direction.magnitude() != 0) {
                returnObject.add(object);
                // displacement for physic and render
                object.getPhysicObject().getPos().add(object.getPhysicObject().direction);
            }
        }

        return returnObject;
    }

    /**
     * Fonction called at a fixed interval dt to update physic.
     *
     * @param objects the list of game objects int the game
     * @param t the time elapsed since engine started
     * @param dt the time-step elapsed between updates
     */
    public void integrate(List<PhysicEntity> objects, double t, double dt) {
        // Move all of object
        List<PhysicEntity> movedObjects = move(objects, t, dt);
        // Check for collision
        for (PhysicEntity o : movedObjects) {
            collision(objects, o);
        }
    }

    /**
     * Function called to detect collision between 2 objects, and active onTriggerEnter
     *
     * @param objects the list of game objects int the game
     * @param object Object we want to test
     */
    private void collision(List<PhysicEntity> objects, PhysicEntity object) {
        for (PhysicEntity target : objects) {
            // Don't test on same object and on empty object
            if (target != object && target.getPhysicObject().getHitBox().getSize() > 0) {
                PhysicObject obj = object.getPhysicObject();
                PhysicObject tgt = target.getPhysicObject();

                if (obj.getHitBox().intersect(tgt.getHitBox())) {
                    // Rollback pos if hit a solid object
                    if (tgt.getHitBox().isSolid()) {
                        obj.getPos().add(obj.direction.reverse());
                    }

                    object.onCollisionEnter(tgt);
                    target.onCollisionEnter(obj);
                }
            }
        }
    }

    public static boolean isThereSolidCollision(PhysicObject o, Point target) {
        HitBox h = o.getHitBox();
        Point oldPos = h.getPosition();
        h.setPosition(target);

        boolean intersect = false;
        List<PhysicEntity> entities = Core.getInstance().getScene().objects();
        for (PhysicEntity e : entities) {
            if (e.getPhysicObject() == o || !e.getPhysicObject().getHitBox().isSolid()) continue;

            if (e.getPhysicObject().getHitBox().intersect(h)) {
                intersect = true;
                break;
            }
        }

        h.setPosition(oldPos);
        return intersect;
    }
}
