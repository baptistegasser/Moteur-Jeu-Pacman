package fr.univ.engine.physic.hitbox;

/**
 * The HitBox for circle object
 */
public class CircleHitBox extends HitBox {

    public CircleHitBox(double size) {
        // Size is a diameter of circle
        this.size = size;
    }

    /**
     * @// FIXME: 22/11/2020 no implementation for collision with squares
     */
    @Override
    public boolean intersect(SquareHitBox h2) {
        return false;
    }

    /**
     * Handle collision between 2 circle hitbox
     * @param h2 the {@code CircleHitBox} to test
     * @return Return true if there is a collision between the current object and h2
     */
    @Override
    public boolean intersect(CircleHitBox h2) {
        double dx = x() - h2.x();
        double dy = y() - h2.y();
        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance < size/2 + h2.size/2;
    }
}
