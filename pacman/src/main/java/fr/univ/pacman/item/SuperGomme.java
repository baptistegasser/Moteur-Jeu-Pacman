package fr.univ.pacman.item;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.CircleHitBox;
import javafx.scene.shape.Circle;

/**
 * The class handling the logic of Super Gomme.
 */
public class SuperGomme extends GameObject {
    public SuperGomme(int posX, int posY) {
        renderObject.pos = new Point(posX, posY);
        renderObject.width = 10;
        renderObject.height = 10;
        renderObject.zIndex = 5;
        renderObject.textureName = "item/superGomme.png";

        this.physicObject.hitBox = new CircleHitBox(renderObject.pos.x,renderObject.pos.y,renderObject.width/2);
    }

}
