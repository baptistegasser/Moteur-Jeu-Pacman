package fr.univ.pacman.item;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.hitbox.CircleHitBox;
import fr.univ.pacman.map.Map;

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

        this.physicObject.hitBox = new CircleHitBox(renderObject.pos.x + Map.BACKGROUND_WIGHT,renderObject.pos.y + Map.BACKGROUND_HEIGHT,renderObject.width/2);
    }

}
