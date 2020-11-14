package fr.univ.engine.physic;

import fr.univ.engine.core.GameObject;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

import java.util.List;

public class PhysicEngine {

    /**
     * Fonction called at a fixed interval dt to update physic.
     *
     * @param objects the list of game objects int the game
     * @param t the time elapsed since engine started
     * @param dt the time-step elapsed between updates
     */
    public void integrate(List<GameObject> objects, double t, double dt) {
        for (GameObject o : objects) {
            o.fixedUpdate(dt);
            collision(objects, o);
        }
    }

    /**
     * Function called to detect collision between 2 objects, and active onTriggerEnter
     *
     * @param objects the list of game objects int the game
     * @param object Object we want to test
     */
    public void collision(List<GameObject> objects, GameObject object) {
        for (GameObject target : objects) {
            if (target != object) {
                if (((Path) Shape.intersect(object.getPhysicObject().shape, target.getPhysicObject().shape)).getElements().size() > 0) {
                    //System.out.println("Collision entre "+object.getRenderObject().textureName +" et "+target.getRenderObject().textureName);
                    object.onTriggerEnter();
                    break;
                }
            }
        }
    }
}
