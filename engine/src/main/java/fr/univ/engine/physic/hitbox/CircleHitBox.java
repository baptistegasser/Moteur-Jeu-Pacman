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
}
