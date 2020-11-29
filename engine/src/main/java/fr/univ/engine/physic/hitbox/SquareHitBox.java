package fr.univ.engine.physic.hitbox;

/**
 * The HitBox for rectangle object
 */
public class SquareHitBox extends HitBox {

    public SquareHitBox(double size) {
        // Size is a width of square
        this.size = size;
    }

    /**
     * Handle collision between 2 square hitbox
     * @param h2 the {@code SquareHitBox} to test
     * @return Return true if there is a collision between the current object and h2
     */
    @Override
    public boolean intersect(SquareHitBox h2) {
        // Size between two center element
        double sizeBetweenElements = (size + h2.size)/2;

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
