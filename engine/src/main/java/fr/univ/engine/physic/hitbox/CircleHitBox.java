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

    /**
     * Handle collision between a rectangle and a circle.
     *
     * @param h2 the {@code RectangleHitBox} to test.
     * @return Return true if there is a collision between the current object and h2.
     * @implNote the implementation is made by the {@link RectangleHitBox#intersect(CircleHitBox)} to reduce code duplication.
     */
    @Override
    public boolean intersect(RectangleHitBox h2) {
        return h2.intersect(this);
    }

    /**
     * Handle collision between 2 circle hitbox.
     *
     * @param h2 the {@code CircleHitBox} to test.
     * @return Return true if there is a collision between the current object and h2.
     */
    @Override
    public boolean intersect(CircleHitBox h2) {
        double dx = x() - h2.x();
        double dy = y() - h2.y();
        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance < diameter / 2 + h2.diameter / 2;
    }
}
