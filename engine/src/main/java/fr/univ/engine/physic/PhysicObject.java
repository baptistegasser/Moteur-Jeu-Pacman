package fr.univ.engine.physic;

import fr.univ.engine.math.Point;

public class PhysicObject {
    /**
     * The position at which to physic the object.
     */
    public Point pos;
    /**
     * The direction of the object.
     */
    int dir;

    /**
     * The velocity of the object
     */
    double velocity;

    public PhysicObject(Point point) {
        pos = point;
        dir = 0;
        velocity = 0.75;
    }
}
