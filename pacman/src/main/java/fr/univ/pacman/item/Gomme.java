package fr.univ.pacman.item;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.hitbox.SquareHitBox;

/**
 * The class handling the logic of Gomme.
 */
public class Gomme extends GameObject {
    public Gomme(int x, int y) {
        super(x, y);
        renderObject.width = 3;
        renderObject.height = 3;
        renderObject.zIndex = 5;
        renderObject.textureName = "item/gomme.png";

        this.physicObject.setHitBox(new SquareHitBox(renderObject.width));
    }
}
