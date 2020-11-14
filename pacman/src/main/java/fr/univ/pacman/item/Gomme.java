package fr.univ.pacman.item;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;

/**
 * The class handling the logic of Gomme.
 */
public class Gomme extends GameObject {
    public Gomme(int posX, int posY) {
        renderObject.pos = new Point(posX, posY);
        renderObject.width = 3;
        renderObject.height = 3;
        renderObject.zIndex = -10;
        renderObject.textureName = "item/gomme.png";
    }

    @Override
    public void update() {

    }
}
