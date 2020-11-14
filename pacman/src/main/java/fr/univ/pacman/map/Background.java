package fr.univ.pacman.map;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;

/**
 * The background of the Pac-Man game, draw the labyrinth on screen.
 */
public class Background extends GameObject {
    public Background() {
        renderObject.pos = new Point(0, 0);
        renderObject.width = 448;
        renderObject.height = 496;
        renderObject.zIndex = -10;
        renderObject.textureName = "map/map.png";
    }

    @Override
    public void update() {

    }
}
