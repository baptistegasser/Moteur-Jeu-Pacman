package fr.univ.engine.physic.hitbox;

import fr.univ.engine.core.Scene;
import javafx.scene.shape.Rectangle;

/**
 * The HitBox for rectangle object
 */
public class SquareHitBox extends HitBox {

    public SquareHitBox(double posX, double posY, double wight) {
        // The coordinates of square are on top left corner so subtracted half of tile_size
        super(posX-(Scene.tile_size/2), posY-(Scene.tile_size/2), wight);
        shape = new Rectangle(this.posX,this.posY,wight,wight);
    }

    @Override
    public void updateShape() {
        shape = new Rectangle(posX,posY,wight,wight);
    }
}
