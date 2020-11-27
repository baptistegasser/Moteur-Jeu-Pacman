package fr.univ.pacman;

import fr.univ.engine.core.Core;
import fr.univ.engine.render.config.WindowConfig;
import fr.univ.engine.utils.CachedResourcesLoader;
import fr.univ.pacman.gameplay.GameMenu;
import fr.univ.pacman.map.Map;

/**
 * The entry point of the Pac-Man Game, setup and start the game.
 */
public class Game {
    /**
     * The core of the game
     */
    static Core core;

    /**
     * The windows configuration
     */
    WindowConfig cfg;

    /**
     * The resolver for resources
     */
    public static CachedResourcesLoader resolver;

    public static volatile boolean canStart = false;

    public static void main(String[] args) throws InterruptedException {

        Game game = new Game();

        game.lunchMenu();


        //TODO attente active, canStart mis a jour dans eventHandler du play button dans MenuView. Permet de lancé le jeu sur Thread principal
        while (true) {
            if (canStart) {
                startGame();
                break;
            }
            Thread.sleep(500);
        }
    }

    /**
     * Init and create all elements for the game
     */
    public Game() {
        resolver = new CachedResourcesLoader("assets/");

        core = new Core(); // Initialize the engine

        // Configure the windows
        cfg = core.getRenderEngine().window;
        cfg.width = 510;
        cfg.height = 620;
        cfg.title = "Pac-Man";
        cfg.allowResize = false;
        cfg.showFPSCounter = true;

        // Create a resolver pointing to the assets dir inside the resources dir
        core.getRenderEngine().setTextureLoader(resolver);
        core.getSoundEngine().setSoundLoader(resolver);



        core.setScene(new Map()); // Set the Pac-Man map

        core.init(); // Init the game

        //core.getSoundEngine().play("sounds/intro.wav");
    }

    /**
     * Create and lunch menu
     */
    private void lunchMenu() {
        GameMenu gameMenu = new GameMenu(resolver);
        gameMenu.getMenuView().construct();
    }

    /**
     * lunch the game
     */
    public static void startGame() {
        core.start();
    }
}
