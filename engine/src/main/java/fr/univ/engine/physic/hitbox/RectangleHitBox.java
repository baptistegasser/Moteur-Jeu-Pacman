package fr.univ.engine.physic.hitbox;

import fr.univ.engine.math.Point;

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

    @Override
    public double getSize() {
        return width * height;
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
        double sizeBetweenElements = (getSize() + h2.getSize()) / 2;

        return x() - h2.x() < sizeBetweenElements &&
                x() - h2.x() > -sizeBetweenElements &&
                y() - h2.y() < sizeBetweenElements &&
                y() - h2.y() > -sizeBetweenElements;
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
