package fr.univ.pacman;

import fr.univ.engine.core.Core;
import fr.univ.engine.sound.SoundEngine;
import fr.univ.engine.utils.CachedResourcesLoader;
import fr.univ.engine.render.WindowConfig;
import fr.univ.pacman.gameplay.Inventory;
import fr.univ.pacman.map.Map;
import fr.univ.pacman.ui.MenuView;

/**
 * The entry point of the Pac-Man Game, setup and start the game.
 */
public class GamePlay {
    Core core;
    WindowConfig cfg;

    private static Inventory inventory;

    private static MenuView menuView;

    public static void main(String[] args) {
        GamePlay gamePlay = new GamePlay();

        gamePlay.start();
    }

    /**
     * Init and create all elements for the game
     */
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
        core.getSoundEngine().setSoundLoader(resolver);

        // Create inventory
        inventory = new Inventory();

        core.setScene(new Map()); // Set the Pac-Man map

        core.init(); // Start the game

        // Create menu view
        menuView = new MenuView(resolver);

        core.getSoundEngine().play("sounds/intro.wav", false);

    }

    /**
     * Start the Game
     */
    private void start() {
        core.getSoundEngine().play("sounds/siren.mp3", true);
        core.start();
    }

    public static Inventory getInventory() {
        return inventory;
    }

    public static MenuView getMenuView() {
        return menuView;
    }
}
