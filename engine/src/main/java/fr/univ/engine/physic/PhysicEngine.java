package fr.univ.engine.physic;

import java.util.ArrayList;
import java.util.List;

public class PhysicEngine {

    /**
     * Function call for object movement in mainLoop
     *
     * @param objects all Object in a game
     * @return only object that have moved
     */
    private List<PhysicEntity> move(List<PhysicEntity> objects) {
        List<PhysicEntity> returnObject = new ArrayList<>();

        for (PhysicEntity object : objects) {
            if (object.getPhysicObject().movement.x != 0 || object.getPhysicObject().movement.y != 0) {
                returnObject.add(object);
                // displacement for physic and render
                object.getPhysicObject().getPos().x += object.getPhysicObject().movement.x;
                object.getPhysicObject().getPos().y += object.getPhysicObject().movement.y;
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
        List<PhysicEntity> movedObjects = move(objects);
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
            if (target != object) {
                PhysicObject obj = object.getPhysicObject();
                PhysicObject tgt = target.getPhysicObject();

                if (obj.getHitBox().intersect(tgt.getHitBox())) {
                    //TODO fonction d'affichage utile à enlever au final
                    /*System.out.println("X : " + object.getPhysicObject().getHitBox().getPosX() + " Y : " + object.getPhysicObject().getHitBox().getPosY()
                            + " Wight : " + object.getPhysicObject().getHitBox().getWight());

                    System.out.println("X : " + target.getPhysicObject().getHitBox().getPosX() + " Y : " + target.getPhysicObject().getHitBox().getPosY()
                            + " Wight : " + target.getPhysicObject().getHitBox().getWight());*/

                    // Rollback pos if hit
                    if (tgt.getHitBox().isSolid()) {
                        obj.getPos().x -= obj.movement.x;
                        obj.getPos().y -= obj.movement.y;
                    }

                    object.onCollisionEnter(tgt);
                    target.onCollisionEnter(obj);
                }
            }
        }
    }
}
