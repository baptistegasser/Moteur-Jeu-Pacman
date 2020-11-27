package fr.univ.pacman.gameplay;

import fr.univ.engine.utils.CachedResourcesLoader;
import fr.univ.pacman.ui.MenuView;

public class GameMenu {
    /**
     * The view for this controller
     */
    private MenuView menuView;

    /**
     * The resolver for resources
     */
    private CachedResourcesLoader resolver;

    public GameMenu(CachedResourcesLoader resolver) {
        this.resolver = resolver;
        this.menuView = new MenuView(resolver, this);
    }

    /**
     * Prepare gameplay and start the game
     */
    public void startGame() {
        GamePlay gamePlay = new GamePlay(resolver);
        gamePlay.start();
    }

    public MenuView getMenuView() {
        return menuView;
    }
}
