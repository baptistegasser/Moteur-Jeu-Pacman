package fr.univ.engine.physic;

import javafx.scene.shape.Circle;

public class CircleHitBox extends HitBox {
    public CircleHitBox(double posX, double posY, double wight) {
        super(posX, posY, wight);
        shape = new Circle(posX,posY,wight);
    }

    @Override
    public void updateShape() {
        shape = new Circle(posX,posY,wight);
    }
}
