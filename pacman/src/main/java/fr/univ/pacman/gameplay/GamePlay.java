package fr.univ.pacman.gameplay;

import fr.univ.engine.core.Core;
import fr.univ.engine.utils.CachedResourcesLoader;
import fr.univ.engine.render.WindowConfig;
import fr.univ.pacman.map.Map;

public class GamePlay {
    Core core;
    WindowConfig cfg;

    public GamePlay() {
        core = new Core(); // Initialize the engine

        // Configure the windows
        cfg = core.getRenderEngine().window;
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
