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

    /**
     * Handle collision between 2 rectangle hitbox.
     *
     * @param h2 the {@code RectangleHitBox} to test.
     * @return Return true if there is a collision between the current object and h2.
     */
    @Override
    public boolean intersect(RectangleHitBox h2) {
        // Size between two center element
        double diffW = (width + h2.width) / 2;
        double diffH = (height + h2.height) / 2;

        return  x() - h2.x() < diffW &&
                x() - h2.x() > -diffW &&
                y() - h2.y() < diffH &&
                y() - h2.y() > -diffH;
    }

    /**
     * Handle collision between a circle and a rectangle .
     *
     * @param h2 the {@code CircleHitBox} to test.
     * @return Return true if there is a collision between the current object and h2.
     * @// FIXME: 29/11/2020 not implemented better thing to do
     */
    @Override
    public boolean intersect(CircleHitBox h2) {
        return false;
    }
}
