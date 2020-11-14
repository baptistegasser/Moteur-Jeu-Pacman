package fr.univ.pacman.item;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;

/**
 * The class handling the logic of Super Gomme.
 */
public class SuperGomme extends GameObject {
    public SuperGomme(int posX, int posY) {
        renderObject.pos = new Point(posX, posY);
        renderObject.width = 10;
        renderObject.height = 10;
        renderObject.zIndex = -10;
        renderObject.textureName = "item/superGomme.png";
    }

}
