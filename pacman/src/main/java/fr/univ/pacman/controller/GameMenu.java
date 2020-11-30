package fr.univ.pacman.controller;

import fr.univ.pacman.PacMan;
import fr.univ.pacman.ui.MenuView;

public class GameMenu {
    /**
     * The view for this controller
     */
    private MenuView menuView;

    public GameMenu() {
        this.menuView = new MenuView(this);
    }

    /**
     * Prepare gameplay and start the game
     */
    public void startGame() {
        PacMan.app().startGame();
    }

    public void quitGame() {
        PacMan.app().quit();
    }

    public MenuView getMenuView() {
        return menuView;
    }
}
