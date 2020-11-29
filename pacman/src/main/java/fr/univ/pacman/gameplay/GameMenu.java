package fr.univ.pacman.gameplay;

import fr.univ.pacman.migration.Rework;
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
        Rework.getInstance().startGame();
    }

    public void quitGame() {
        Rework.getInstance().quit();
    }

    public MenuView getMenuView() {
        return menuView;
    }
}
