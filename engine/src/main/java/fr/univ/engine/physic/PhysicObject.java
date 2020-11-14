package fr.univ.engine.physic;

import fr.univ.engine.math.Function;
import fr.univ.engine.math.Point;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class PhysicObject {
    /**
     * The position at which to physic the object.
     */
    public Point pos;

    private Function<Double> fixedUpdateImpl = o -> {};

    public Shape shape;

    public PhysicObject(Point point) {
        pos = point;
        shape = new Rectangle(0,0,0,0);
    }

    public void fixedUpdate(double dt) {
        this.fixedUpdateImpl.apply(dt);
    }

    public void setFixedUpdateImpl(Function<Double> fixedUpdateImpl) {
        this.fixedUpdateImpl = fixedUpdateImpl;
    }
}
