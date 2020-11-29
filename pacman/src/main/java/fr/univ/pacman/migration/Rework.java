package fr.univ.pacman.migration;

import fr.univ.engine.assets.AssetsLoader;
import fr.univ.engine.core.Level;
import fr.univ.engine.core.config.Config;
import fr.univ.engine.core.GameApplication;
import fr.univ.engine.core.level.TextLevelLoader;
import fr.univ.pacman.gameplay.GameMenu;
import fr.univ.pacman.gameplay.GameController;
import fr.univ.pacman.migration.component.PacManLogic;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.input.KeyCode;

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
    protected void drawApplication() {
        GameMenu gameMenu = new GameMenu();
        uiEngine().draw(gameMenu.getMenuView());
        uiEngine().showWindow();
    }

    @Override
    protected void initGame() {
        uiEngine().clear();
        GameController gameController = new GameController();
        uiEngine().draw(gameController.getGameView());


        Level lvl = new TextLevelLoader().load(AssetsLoader.getLevel("map.txt"), new GameFactory());
        setLevel(lvl);

        DoubleProperty score = new SimpleDoubleProperty();

        PacManLogic pacmanLogic = getLevel().getSingletonEntity(Type.PACMAN).getComponent(PacManLogic.class);
        IOEngine().onKeyPressed(KeyCode.UP, pacmanLogic::up);
        IOEngine().onKeyPressed(KeyCode.DOWN, pacmanLogic::down);
        IOEngine().onKeyPressed(KeyCode.LEFT, pacmanLogic::left);
        IOEngine().onKeyPressed(KeyCode.RIGHT, pacmanLogic::right);

        physicEngine().onCollision(PACMAN, WALL, (e1, e2) -> pacmanLogic.stop());
        physicEngine().onCollision(PACMAN, GHOST, (e1, e2) -> {
            soundEngine().play("pac_die.wav");
            pacmanLogic.hit();
        });
        physicEngine().onCollision(PACMAN, PAC, (e1, e2) -> {
            soundEngine().play("eating_pac.wav");
            score.set(score.get() + 10);
            getLevel().destroyEntity(e2);
        });
        physicEngine().onCollision(PACMAN, SUPER_PAC, (e1, e2) -> {
            soundEngine().play("eating_pac.wav");
            // todo scatter ghost ia
            getLevel().destroyEntity(e2);;
        });

        soundEngine().playLoop("eating_pac.wav", 0.3);

        // IA ?
    }

    public static void main(String[] args) {
        launch(args);
    }
}
