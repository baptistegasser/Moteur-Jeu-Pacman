package fr.univ.engine.physic;

import fr.univ.engine.math.Point;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class PhysicObject {

    public Shape shape;

    public PhysicObject(Point point) {
        shape = new Rectangle(0,0,0,0);
    }
}
