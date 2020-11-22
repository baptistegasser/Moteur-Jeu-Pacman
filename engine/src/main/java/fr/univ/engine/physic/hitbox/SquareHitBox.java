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

    @Override
    public boolean intersect(SquareHitBox h2) {
        // Size between two center element
        double sizeBetweenElements = (wight + h2.wight)/2;

        return posX - h2.posX < sizeBetweenElements &&
                posX - h2.posX > -sizeBetweenElements &&
                posY - h2.posY < sizeBetweenElements &&
                posY - h2.posY > -sizeBetweenElements;
    }

    /**
     * @// FIXME: 22/11/2020 no implementation for collision with circles
     */
    @Override
    public boolean intersect(CircleHitBox h2) {
        return false;
    }
}
