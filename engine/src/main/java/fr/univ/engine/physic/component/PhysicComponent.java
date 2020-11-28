package fr.univ.engine.physic.component;

import fr.univ.engine.core.component.Component;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.hitbox.HitBox;

/**
 * Physical component for entity with movement and hit boxes.
 */
public final class PhysicComponent implements Component {
    /**
     * The hit box of the entity.
     */
    private HitBox hitBox;
    /**
     * The direction of the entity.
     * Implicitly give the speed (magnitude of the vector).
     */
    private final Vector direction;

    public PhysicComponent(HitBox hitBox, Vector direction) {
        this.hitBox = hitBox;
        this.direction = direction;
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
}
