package fr.univ.pacman;

import fr.univ.engine.core.Core;
import fr.univ.engine.render.WindowConfig;
import fr.univ.engine.render.texture.CachedFolderResolver;
import fr.univ.pacman.map.Map;

import java.io.File;

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

        System.out.println(Main.class.getResource("/assets").getPath().replace("%20", " "));
        Core.render().setTextureResolver(new CachedFolderResolver(new File(Main.class.getResource("/assets").getPath().replace("%20", " "))));
        Core.setScene(new Map());

        Core.start();
    }
}
