package fr.univ.pacman;

import fr.univ.engine.core.Core;
import fr.univ.engine.utils.CachedResourcesLoader;

/**
 * The entry point of the Pac-Man Game, setup and start the game.
 */
public class Game {
    /**
     * The core of the game
     */
    static Core core;


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
        /*
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
        core.getSoundEngine().setSoundLoader(resolver);

        // TODO remove in the engine rework: dirty hack to find pacman
        Map map = new Map();
        PacMan pacMan = null;
        for (Object object : map.objects()) {
            if (object instanceof PacMan) {
                pacMan = (PacMan) object;
            }
        }

        IOEngine ioEngine = core.getIOEngine();
        ioEngine.on(KeyCode.UP, pacMan::up);
        ioEngine.on(KeyCode.DOWN, pacMan::down);
        ioEngine.on(KeyCode.LEFT, pacMan::left);
        ioEngine.on(KeyCode.RIGHT, pacMan::right);

        core.setScene(map); // Set the Pac-Man map

        core.init(); // Init the game

        //core.getSoundEngine().play("sounds/intro.wav");*/
    }

    /**
     * Create and lunch menu
     */
    private void lunchMenu() {
        /*GameMenu gameMenu = new GameMenu();
        gameMenu.getMenuView().construct();*/
    }

    /**
     * lunch the game
     */
    public static void startGame() {
        core.start();
    }
}
