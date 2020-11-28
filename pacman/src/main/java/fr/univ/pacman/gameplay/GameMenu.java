package fr.univ.pacman.gameplay;

import fr.univ.pacman.migration.Rework;
import fr.univ.pacman.ui.MenuView;

public class GameMenu {
    Rework rework;
    /**
     * The view for this controller
     */
    private MenuView menuView;

    public GameMenu(Rework rework) {
        this.rework = rework;
        this.menuView = new MenuView(this);
    }

    /**
     * Prepare gameplay and start the game
     */
    public void startGame() {
        rework.startGame();
    }

    public void quitGame() {
        rework.quit();
    }

    public MenuView getMenuView() {
        return menuView;
    }
}
