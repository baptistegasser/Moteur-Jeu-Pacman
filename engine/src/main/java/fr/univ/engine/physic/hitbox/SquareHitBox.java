package fr.univ.engine.physic.hitbox;

import javafx.scene.shape.Rectangle;

/**
 * The HitBox for rectangle object
 */
public class SquareHitBox extends HitBox {
    private double width;

    public SquareHitBox(double width) {
        this.width = width;
    }

    public double width() {
        return width;
    }

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
