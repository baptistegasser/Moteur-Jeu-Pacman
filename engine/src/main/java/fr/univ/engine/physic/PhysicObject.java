package fr.univ.engine.physic;

import fr.univ.engine.math.Point;
import fr.univ.engine.physic.hitbox.CircleHitBox;
import fr.univ.engine.physic.hitbox.HitBox;
import fr.univ.engine.physic.hitbox.SquareHitBox;

public class PhysicObject {

    /**
     * The direction and velocity of the entity
     */
    public Point movement;

    private Point pos;

    private HitBox hitBox;

    private String name;

    public PhysicObject(Point point) {
        pos = point;
        movement = new Point(0,0);
        hitBox = new SquareHitBox(0);
    }

    public void setHitBox(HitBox hitBox) {
        this.hitBox = hitBox;
        this.hitBox.setPosition(pos);
    }

    public HitBox getHitBox() {
        return hitBox;
    }

    public Point getPos() {
        return pos;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
