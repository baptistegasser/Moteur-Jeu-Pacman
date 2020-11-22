package fr.univ.engine.physic;

import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.hitbox.HitBox;
import fr.univ.engine.physic.hitbox.SquareHitBox;

public class PhysicObject {

    /**
     * The direction and velocity of the entity
     */
    public Vector direction;

    /**
     * The position of the physic object
     */
    private Point pos;

    /**
     * The hitbox of the physic object
     */
    private HitBox hitBox;

    /**
     * The name of the physic object
     */
    private String name;

    /**
     * Create a physic object at the given position
     * @param point The start position
     */
    public PhysicObject(Point point) {
        pos = point;
        direction = new Vector(0,0);
        hitBox = new SquareHitBox(0);
    }

    /**
     * Set the hitbox of the object
     * @param hitBox The hitbox of the oject
     */
    public void setHitBox(HitBox hitBox) {
        this.hitBox = hitBox;
        this.hitBox.setPosition(pos);
    }

    public HitBox getHitBox() {
        return hitBox;
    }

    /**
     * @return The current position of the object
     */
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
