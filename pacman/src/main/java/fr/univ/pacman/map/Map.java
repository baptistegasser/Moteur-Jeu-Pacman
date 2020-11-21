package fr.univ.pacman.map;

import fr.univ.engine.core.Scene;
import fr.univ.pacman.entity.Ghost;
import fr.univ.pacman.entity.PacMan;
import fr.univ.pacman.item.Gomme;
import fr.univ.pacman.item.SuperGomme;

/**
 * The Pac-Man game scene.
 * Define the different elements (walls, ghosts, player, etc)
 */
public class Map extends Scene {

    public static final int TILE_SIZE = 16;
    public static final int BACKGROUND_WIGHT = 448;
    public static final int BACKGROUND_HEIGHT = 496;

    public Map() {
        super();
        add(new Background());

        LoadMap.loadMap(this, MapModel.matriceModel, TILE_SIZE);

        add(new PacMan(0, 32));

        // Space between two gomme is 16 pixels
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

        add(new Ghost(104,80,"orange"));
        add(new Ghost(-120,48,"red"));
        add(new Ghost(0,-32,"blue"));
        add(new Ghost(200,-224,"pink"));
    }
}
