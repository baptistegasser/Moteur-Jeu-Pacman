package fr.univ.engine.physic;

import fr.univ.engine.math.Point;

public class PhysicObject {

    /**
     * The direction and velocity of the entity
     */
    public Point movement;

    private Point pos;

    public HitBox hitBox;

    public PhysicObject(Point point) {
        movement = new Point(0,0);
        pos = point;
        hitBox = new CircleHitBox(0,0,0);
    }

    public Point getPos() {
        return pos;
    }
}
