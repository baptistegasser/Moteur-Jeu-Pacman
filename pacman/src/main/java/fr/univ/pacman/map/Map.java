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

    public Map() {
        super();
        add(new Background());

        LoadMap.loadMap(this, MapModel.matriceModel, TILE_SIZE);

        add(new PacMan(0, 32));
        //add(new PacMan(64, 80));

        // Space between two gomme is 16 pixels
        /*add(new Gomme(72, 32));
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
        add(new Gomme(-24, 112));*/

        add(new Ghost(104,80,"orange"));
        //add(new Ghost(0,32,"red"));
        add(new Ghost(0,-32,"blue"));
        add(new Ghost(200,-224,"pink"));

        // Construction des murs
        //add(new Wall(-8,16));
        //add(new Wall(8,16));


        //add(new Wall(-8,48));
        //add(new Wall(56,48));
        //add(new Wall(88,48));

        //add(new Wall(-8,64));
        //add(new Wall(-8,80));
        //add(new Wall(8,80));
        //add(new Wall(0,16));
        //add(new Wall(-16,32));
        //add(new Wall(16,32));
    }
}
