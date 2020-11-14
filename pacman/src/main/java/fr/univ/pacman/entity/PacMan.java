package fr.univ.pacman.entity;

import fr.univ.engine.math.Point;

/**
 * The class handling the logic of Pac-Man controlled by the player.
 */
public class PacMan extends MobileEntity {
    public PacMan(int posX, int posY) {
        renderObject.pos.x = posX;
        renderObject.pos.y = posY;
        renderObject.width = 16;
        renderObject.height = 16;
        renderObject.zIndex = 10;
        renderObject.textureName = "sprites/pacman.png";

        movement = new Point(0.5,0);
    }

    @Override
    public void onTriggerEnter() {
        movement.x = 0;
        movement.y = 0;
    }
}
