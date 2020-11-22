package fr.univ.engine.physic.hitbox;

import javafx.scene.shape.Circle;

/**
 * The HitBox for circle object
 */
public class CircleHitBox extends HitBox {
    private double diameter;

    public CircleHitBox(double diameter) {
        this.diameter = diameter;
    }

    public double diameter() {
        return diameter;
    }

    /**
     * @// FIXME: 22/11/2020 no implementation for collision with squares
     */
    @Override
    public boolean intersect(SquareHitBox h2) {
        return false;
    }

    @Override
    public boolean intersect(CircleHitBox h2) {
        double dx = x() - h2.x();
        double dy = y() - h2.y();
        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance < diameter/2 + h2.diameter/2;
    }
}
