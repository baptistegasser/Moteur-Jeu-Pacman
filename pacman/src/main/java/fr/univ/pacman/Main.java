package fr.univ.pacman;

import fr.univ.engine.core.Core;
import fr.univ.engine.core.GameObject;
import fr.univ.engine.render.WindowConfig;
import fr.univ.engine.render.CachedResourcesLoader;
import fr.univ.pacman.gameplay.GamePlay;
import fr.univ.pacman.map.Map;

/**
 * The entry point of the Pac-Man Game, setup and start the game.
 */
public class Main {
    public static void main(String[] args) {
        GamePlay gamePlay = new GamePlay();
    }
}
