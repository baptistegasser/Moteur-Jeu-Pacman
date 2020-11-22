package fr.univ.pacman.item;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.hitbox.SquareHitBox;

/**
 * The class handling the logic of Super Gomme.
 */
public class SuperGomme extends GameObject {
    public SuperGomme(int x, int y) {
        super(x, y);
        renderObject.width = 10;
        renderObject.height = 10;
        renderObject.zIndex = 5;
        renderObject.textureName = "item/superGomme.png";

        this.physicObject.setHitBox(new SquareHitBox(renderObject.width));
    }

}
