package fr.univ.engine.physic.hitbox;

/**
 * A square hitbox implementation.
 */
public class SquareHitBox extends HitBox {
    /**
     * The size of this square.
     */
    private final double size;

    public SquareHitBox(double size) {
        this.size = size;
    }

    public double size() {
        return size;
    }
}
