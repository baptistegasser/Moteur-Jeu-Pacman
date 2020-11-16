package fr.univ.pacman.item;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;
import javafx.scene.shape.Circle;

/**
 * The class handling the logic of Gomme.
 */
public class Gomme extends GameObject {
    public Gomme(int posX, int posY) {
        renderObject.pos = new Point(posX, posY);
        renderObject.width = 3;
        renderObject.height = 3;
        renderObject.zIndex = 5;
        renderObject.textureName = "item/gomme.png";

        this.physicObject.shape = new Circle(renderObject.pos.x,renderObject.pos.y,renderObject.width/2);
    }

}
