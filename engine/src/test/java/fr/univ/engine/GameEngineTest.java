package fr.univ.engine;

import fr.univ.engine.core.GameApplication;
import fr.univ.engine.core.config.Config;
import org.junit.jupiter.api.BeforeAll;

/**
 * Class to easily test a feature of the engine that require
 * the engine to be running or the window to be shown.
 */
public class GameEngineTest extends GameApplication {

    @BeforeAll
    static void beforeAll() {
        launch();
    }

    @Override
    protected void config(Config config) {
        config.width = 0;
        config.height = 0;
    }

    @Override
    protected void initGame() {
        quit(); // instant quit
        renderEngine().hideWindow();
    }
}
