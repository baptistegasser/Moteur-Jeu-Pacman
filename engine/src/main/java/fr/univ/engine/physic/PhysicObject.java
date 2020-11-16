package fr.univ.engine.physic;

import fr.univ.engine.math.Point;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class PhysicObject {

    /**
     * The direction and velocity of the entity
     */
    public Point movement;

    public Shape shape;

    public PhysicObject() {
        movement = new Point(0,0);
        shape = new Rectangle(0,0,0,0);
    }
}
