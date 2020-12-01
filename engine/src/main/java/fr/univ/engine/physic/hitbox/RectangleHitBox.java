package fr.univ.engine.physic.hitbox;

/**
 * A rectangle hitbox implementation.
 */
public class RectangleHitBox extends HitBox {
    /**
     * The width of this rectangle.
     */
    private final double width;
    /**
     * The height of this rectangle.
     */
    private final double height;

    public RectangleHitBox(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double width() {
        return width;
    }

    public double height() {
        return height;
    }
}
