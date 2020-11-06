package fr.univ.pacman;

import fr.univ.engine.core.Core;
import fr.univ.engine.render.WindowConfig;
import fr.univ.engine.render.texture.CachedResourcesResolver;
import fr.univ.pacman.map.Map;

/**
 * The entry point of the Pac-Man Game
 */
public class Main {
    public static void main(String[] args) {
        Core.init();

        WindowConfig cfg = Core.render().window;
        cfg.width = 510;
        cfg.height = 620;
        cfg.title = "Pac-Man";
        cfg.allowResize = false;
        cfg.showFPSCounter = true;

        Core.render().setTextureResolver(new CachedResourcesResolver("assets/"));
        Core.setScene(new Map());

        Core.start();
    }
}
