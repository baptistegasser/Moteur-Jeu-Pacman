package fr.univ.pacman.map;

import fr.univ.engine.core.Entity;
import fr.univ.engine.core.math.Point;
import fr.univ.engine.core.math.Rect;
import fr.univ.engine.render.texture.Textured;

/**
 * The background of the Pac-Man game, draw the labyrinth on screen.
 */
public class Background extends Entity implements Textured {
    public Background() {
        super(new Point(0, 0), new Rect(448, 496));
        setZIndex(-100);
    }

    @Override
    public String getTextureName() {
        return "map/map.png";
    }
}
