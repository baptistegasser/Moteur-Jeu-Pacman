package fr.univ.pacman.entity;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;
import javafx.scene.shape.Circle;

/**
 * The class handling the logic of Pac-Man controlled by the player.
 */
public class PacMan extends GameObject {
    public PacMan(int posX, int posY) {
        renderObject.pos.x = posX;
        renderObject.pos.y = posY;
        renderObject.width = 16;
        renderObject.height = 16;
        renderObject.zIndex = 10;
        renderObject.textureName = "sprites/pacman.png";

        physicObject.movement = new Point(0.5,0);

        this.physicObject.shape = new Circle(this.renderObject.pos.x, this.renderObject.pos.y, this.renderObject.width/2);

       }

    @Override
    public void onTriggerEnter() {
        physicObject.movement.x = 0;
        physicObject.movement.y = 0;
    }
}
