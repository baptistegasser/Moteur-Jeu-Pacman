package fr.univ.pacman;

import fr.univ.engine.core.Core;
import fr.univ.engine.render.WindowConfig;
import fr.univ.engine.render.CachedResourcesLoader;
import fr.univ.pacman.map.Map;

/**
 * The entry point of the Pac-Man Game, setup and start the game.
 */
public class Main {
    public static void main(String[] args) {
        Core core = new Core(); // Initialize the engine

        // Configure the windows
        WindowConfig cfg = core.getRenderEngine().window;
        cfg.width = 510;
        cfg.height = 620;
        cfg.title = "Pac-Man";
        cfg.allowResize = false;
        cfg.showFPSCounter = true;

        // Create a resolver pointing to the assets dir inside the resources dir
        CachedResourcesLoader resolver = new CachedResourcesLoader("assets/");
        core.getRenderEngine().setTextureLoader(resolver);

        core.setScene(new Map()); // Set the Pac-Man map

        core.start(); // Start the game
    }
}
