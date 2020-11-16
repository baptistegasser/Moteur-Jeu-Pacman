package fr.univ.engine.physic;

import com.sun.javafx.geom.Area;
import com.sun.javafx.geom.PathIterator;
import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class PhysicEngine {

    /**
     * Function call for object movement in mainLoop
     *
     * @param objects all Object in a game
     * @return only object that have moved
     */
    public List<GameObject> move(List<GameObject> objects) {
        List<GameObject> returnObject = new ArrayList<>();

        for (GameObject object : objects) {
            if (object.getPhysicObject().movement.x != 0 || object.getPhysicObject().movement.y != 0) {
                returnObject.add(object);
                object.getRenderObject().pos.x += object.getPhysicObject().movement.x;
                object.getRenderObject().pos.y += object.getPhysicObject().movement.y;

                object.getPhysicObject().shape = new Circle(object.getRenderObject().pos.x, object.getRenderObject().pos.y, object.getRenderObject().width/2);
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
    public void integrate(List<GameObject> objects, double t, double dt) {
        // Move all of object
        List<GameObject> movedObjects = move(objects);
        // Check for collision
        for (GameObject o : objects) {
            collision(movedObjects, o);
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
                //System.out.println(target.getRenderObject().textureName + " / " + object);
                if (((Path)Shape.intersect(object.getPhysicObject().shape, target.getPhysicObject().shape)).getElements().size() > 0) {
                    System.out.println("Collision entre "+object.getRenderObject().textureName +" et "+target.getRenderObject().textureName);
                    System.out.println(target.getPhysicObject().shape.getTypeSelector());
                    object.onTriggerEnter();
                    break;
                }
            }
        }
    }
}
