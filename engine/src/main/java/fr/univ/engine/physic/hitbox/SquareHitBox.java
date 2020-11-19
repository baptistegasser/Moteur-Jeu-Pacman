package fr.univ.engine.physic.hitbox;

import javafx.scene.shape.Rectangle;

public class SquareHitBox extends HitBox {

    public SquareHitBox(double posX, double posY, double wight) {
        super(posX, posY, wight);
        shape = new Rectangle(posX,posY,wight,wight);
    }

    @Override
    public void updateShape() {
        shape = new Rectangle(posX,posY,wight,wight);
    }
}
