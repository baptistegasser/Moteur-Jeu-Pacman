package fr.univ.pacman.map;

import fr.univ.engine.core.Scene;
import fr.univ.pacman.entity.PacMan;

public class Map extends Scene {
    public Map() {
        super();
        add(new Background());
        add(new PacMan());
    }
}
