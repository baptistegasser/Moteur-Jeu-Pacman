package fr.univ.pacman.map;

import fr.univ.engine.core.Scene;
import fr.univ.pacman.entity.PacMan;

/**
 * The Pac-Man game scene.
 * Define the different elements (walls, ghosts, player, etc)
 */
public class Map extends Scene {
    public Map() {
        super();
        add(new Background());
        add(new PacMan());
    }
}
