package fr.univ.engine.physic;

import fr.univ.engine.math.Function;
import fr.univ.engine.math.Point;

public class PhysicObject {
    /**
     * The position at which to physic the object.
     */
    public Point pos;

    private Function<Double> fixedUpdateImpl = o -> {};

    public PhysicObject(Point point) {
        pos = point;
    }

    public void fixedUpdate(double dt) {
        this.fixedUpdateImpl.apply(dt);
    }

    public void setFixedUpdateImpl(Function<Double> fixedUpdateImpl) {
        this.fixedUpdateImpl = fixedUpdateImpl;
    }
}
