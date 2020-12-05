package fr.univ.engine.physic;

import fr.univ.engine.core.Component;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.hitbox.HitBox;

/**
 * Physical component for entity with movement and hit boxes.
 */
public final class PhysicComponent extends Component {
    /**
     * The hit box of the entity.
     */
    private HitBox hitBox;
    /**
     * The direction of the entity.
     * Implicitly give the speed (magnitude of the vector).
     */
    private Vector direction;

    /**
     * The speed of the entity
     */
    private double speed;

    public PhysicComponent(HitBox hitBox, Vector direction, double speed) {
        this.hitBox = hitBox;
        this.direction = direction;
        this.speed = speed;
    }

    /**
     * Get the hit box of this entity.
     *
     * @return the hitbox implementation.
     */
    public HitBox getHitBox() {
        return this.hitBox;
    }

    /**
     * Set a new hit box for the entity.
     *
     * @param hitBox the new hit box.
     */
    public void setHitBox(HitBox hitBox) {
        this.hitBox = hitBox;
    }

    /**
     * Get the direction of the entity.
     *
     * @return the direction of this object as a vector.
     */
    public Vector direction() {
        return this.direction;
    }

    /**
     * Set the direction of the entity.
     *
     * @param newDirection the new direction of the entity.
     */
    public void setDirection(Vector newDirection) {
        this.direction = newDirection;
    }

    /**
     * Get the direction with the speed
     * @param vector the direction vector
     * @return the vector with speed
     */
    public Vector getDirectionSpeed(Vector vector) {
        if (vector.x()>0) return new Vector(speed, 0);
        else if (vector.x()<0) return new Vector(-speed, 0);
        else if (vector.y()>0) return new Vector(0, speed);
        else if (vector.y()<0) return new Vector(0, -speed);
        else return new Vector(0,0);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
