package fr.univ.engine.physic.hitbox;

import javafx.scene.shape.Rectangle;

/**
 * The HitBox for rectangle object
 */
public class SquareHitBox extends HitBox {

    public SquareHitBox(double posX, double posY, double wight) {
        super(posX, posY, wight);
        shape = new Rectangle(this.posX-(wight/2),this.posY-(wight/2),wight,wight);
    }

    @Override
    public void updateShape() {
        shape = new Rectangle(posX-(wight/2),posY-(wight/2),wight,wight);
    }
}
