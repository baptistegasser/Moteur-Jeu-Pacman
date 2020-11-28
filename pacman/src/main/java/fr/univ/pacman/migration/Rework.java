package fr.univ.pacman.migration;

import fr.univ.engine.core.config.Config;
import fr.univ.engine.core.GameApplication;
import fr.univ.pacman.gameplay.GameMenu;
import fr.univ.pacman.migration.component.PacManLogic;
import fr.univ.pacman.ui.MenuView;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.input.KeyCode;

import java.awt.*;

import static fr.univ.pacman.migration.Type.*;

public class Rework extends GameApplication {

    @Override
    protected void config(Config config) {
        config.title = "Pac-Man";
        config.version = "2.0";
        config.width = 510;
        config.height = 620;
        config.resizable = false;
        config.displayFPS = true;
    }

    @Override
    protected void startApplication() {
        GameMenu gameMenu = new GameMenu();
        MenuView menuView = new MenuView(gameMenu);
        uiEngine().draw(menuView);
        uiEngine().showWindow();
    }

    @Override
    protected void initGame() {
        setLevel(new Labyrinth());

        DoubleProperty score = new SimpleDoubleProperty();

        PacManLogic pacmanLogic = getLevel().getSingletonEntity(Type.PACMAN).getComponent(PacManLogic.class);
        IOEngine().on(KeyCode.UP, pacmanLogic::up);
        IOEngine().on(KeyCode.DOWN, pacmanLogic::down);
        IOEngine().on(KeyCode.LEFT, pacmanLogic::left);
        IOEngine().on(KeyCode.RIGHT, pacmanLogic::right);

        physicEngine().onCollision(PACMAN, WALL, (e1, e2) -> pacmanLogic.stop());
        physicEngine().onCollision(PACMAN, GHOST, (e1, e2) -> {
            soundEngine().play("pac_die.wav", false);
            pacmanLogic.hit();
        });
        physicEngine().onCollision(PACMAN, PAC, (e1, e2) -> {
            soundEngine().play("eating_pac.wav", false);
            score.set(score.get() + 10);
            getLevel().destroyEntity(e2);
        });
        physicEngine().onCollision(PACMAN, SUPER_PAC, (e1, e2) -> {
            soundEngine().play("eating_pac.wav", false);
            // todo scatter ghost ia
            getLevel().destroyEntity(e2);;
        });

        // IA ?
    }

    public static void main(String[] args) {
        launch(args);
    }
}
