package fr.univ.pacman.controller;

import fr.univ.pacman.ui.GameView;

public class GameController {

    /**
     * The instance of inventory
     */
    private Inventory inventory;

    /**
     * The view of the controller
     */
    private GameView gameView;

    public GameController() {
        // Create inventory
        inventory = new Inventory(this);

        gameView = new GameView(this);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public GameView getGameView() {
        return gameView;
    }
}
