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
        PacMan.getApp().startGame();
    }

    public void quitGame() {
        PacMan.getApp().quit();
    }

    public MenuView getMenuView() {
        return menuView;
    }
}
