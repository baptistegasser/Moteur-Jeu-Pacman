package fr.univ.pacman;

import fr.univ.engine.core.Core;
import fr.univ.engine.core.Scene;
import fr.univ.engine.render.WindowConfig;

/**
 * The entry point of the Pac-Man Game
 */
public class Main {
    public static void main(String[] args) {
        Core.init();

        WindowConfig cfg = Core.render().window;
        cfg.width = 1280;
        cfg.height = 720;
        cfg.title = "Pac-Man";
        cfg.showFPSCounter = true;

        Core.setScene(new Scene());
        Core.start();
    }
}
