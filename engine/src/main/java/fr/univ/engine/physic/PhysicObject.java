package fr.univ.engine.physic;

import fr.univ.engine.math.Point;

public class PhysicObject {
    /**
     * The position at which to physic the object.
     */
    public Point pos;

    public PhysicObject(Point point) {
        pos = point;
    }
}
