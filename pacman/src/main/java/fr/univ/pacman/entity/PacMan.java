package fr.univ.pacman.entity;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;

/**
 * The class handling the logic of Pac-Man controlled by the player.
 */
public class PacMan extends MobileEntity {
    public PacMan() {
        renderObject.pos.x = 0;
        renderObject.pos.y = 32;
        renderObject.width = 16;
        renderObject.height = 16;
        renderObject.zIndex = 10;
        renderObject.textureName = "sprites/pacman.png";

        movement = new Point(0.75,0.75);
    }
}
