package fr.univ.engine.physic.hitbox;

/**
 * A circle hitbox implementation.
 */
public class CircleHitBox extends HitBox {
    /**
     * The diameter of this hitbox.
     */
    private final double diameter;

    public CircleHitBox(double diameter) {
        this.diameter = diameter;
    }

    public double diameter() {
        return diameter;
    }
}
