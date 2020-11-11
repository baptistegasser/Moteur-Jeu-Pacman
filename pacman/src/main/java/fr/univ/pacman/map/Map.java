package fr.univ.pacman.map;

import fr.univ.engine.core.Scene;
import fr.univ.pacman.entity.PacMan;
import fr.univ.pacman.item.Gomme;
import fr.univ.pacman.item.SuperGomme;

/**
 * The Pac-Man game scene.
 * Define the different elements (walls, ghosts, player, etc)
 */
public class Map extends Scene {
    public Map() {
        super();
        add(new Background());
        add(new PacMan());

        // Space between two gomme is 16 pixels
        add(new Gomme(72, 32));
        add(new Gomme(72, 48));
        add(new Gomme(72, 64));
        add(new SuperGomme(72, 80));
        add(new Gomme(56, 80));
        add(new Gomme(40, 80));
        add(new Gomme(24, 80));
        add(new Gomme(24, 96));
        add(new Gomme(24, 112));
        add(new Gomme(24, 128));
        add(new Gomme(40, 128));
        add(new Gomme(56, 128));
        add(new Gomme(8, 128));
        add(new Gomme(-8, 128));
        add(new Gomme(-24, 128));
        add(new Gomme(-24, 80));
        add(new Gomme(-24, 96));
        add(new Gomme(-24, 112));
    }
}
