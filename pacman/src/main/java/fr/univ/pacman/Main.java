package fr.univ.pacman;

import fr.univ.engine.core.Core;
import fr.univ.engine.render.WindowConfig;
import fr.univ.engine.render.texture.CachedResourcesResolver;
import fr.univ.pacman.map.Map;

/**
 * The entry point of the Pac-Man Game, setup and start the game.
 */
public class Main {
    public static void main(String[] args) {
        Core.init(); // Initialize the engine

        // Configure the windows
        WindowConfig cfg = Core.render().window;
        cfg.width = 510;
        cfg.height = 620;
        cfg.title = "Pac-Man";
        cfg.allowResize = false;
        cfg.showFPSCounter = true;

        // Create a resolver pointing to the assets dir inside the resources dir
        CachedResourcesResolver resolver = new CachedResourcesResolver("assets/");
        Core.render().setTextureResolver(resolver);

        Core.setScene(new Map()); // Set the Pac-Man map

        Core.start(); // Start the game
    }
}
