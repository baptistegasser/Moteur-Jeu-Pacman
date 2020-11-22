package fr.univ.engine.physic.hitbox;

/**
 * The HitBox for rectangle object
 */
public class SquareHitBox extends HitBox {
    /**
     * Return the width of the hitbox
     */
    private double width;

    public SquareHitBox(double width) {
        this.width = width;
    }

    public double width() {
        return width;
    }

    /**
     * Handle colision betwen 2 square hitbox
     * @param h2 the {@code SquareHitBox} to test
     * @return Return true if there is a colision between the current object and h2
     */
    @Override
    public boolean intersect(SquareHitBox h2) {
        // Size between two center element
        double sizeBetweenElements = (width + h2.width)/2;

        return x() - h2.x() < sizeBetweenElements &&
                x() - h2.x() > -sizeBetweenElements &&
                y() - h2.y() < sizeBetweenElements &&
                y() - h2.y() > -sizeBetweenElements;
    }

    /**
     * @// FIXME: 22/11/2020 no implementation for collision with circles
     */
    @Override
    public boolean intersect(CircleHitBox h2) {
        return false;
    }
}
