package fr.univ.pacman.gameplay;

import fr.univ.pacman.Game;
import fr.univ.pacman.ui.GameView;

public class GamePlay {

    /**
     * The instance of inventory
     */
    private static Inventory inventory;

    /**
     * The view of the controller
     */
    private GameView gameView;

    public GamePlay() {
        // Create inventory
        inventory = new Inventory(this);

        gameView = new GameView();
    }

    /**
     * Prepare gameView and start the game
     */
    public void start() {
        gameView.construct();
        Game.canStart = true;
    }

    public static Inventory getInventory() {
        return inventory;
    }

    public GameView getGameView() {
        return gameView;
    }
}
