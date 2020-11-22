package fr.univ.engine.physic.hitbox;

import javafx.scene.shape.Circle;

/**
 * The HitBox for circle object
 */
public class CircleHitBox extends HitBox {
    public CircleHitBox(double posX, double posY, double wight) {
        super(posX, posY, wight);
        shape = new Circle(posX,posY,wight/2);
    }

    @Override
    public void updateShape() {
        shape = new Circle(posX,posY,wight/2);
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
        double dx = posX - h2.posX;
        double dy = posY - h2.posY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance < wight/2 + wight/2;
    }
}
