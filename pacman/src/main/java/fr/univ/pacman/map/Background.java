package fr.univ.pacman.map;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;
import fr.univ.engine.render.texture.Texture;
import fr.univ.pacman.gameplay.GamePlay;

/**
 * The background of the Pac-Man game, draw the labyrinth on screen.
 */
public class Background extends GameObject {
    public Background() {
        super(0, 0);

        Texture texture = new Texture(Map.BACKGROUND_WIGHT, Map.BACKGROUND_HEIGHT, GamePlay.resolver.getImage("map/map.png"));
        texture.setZIndex(-10);
        setTexture(texture);
    }
}
