package fr.univ.pacman.controller;

import fr.univ.engine.core.GameApplication;
import fr.univ.engine.ui.JFXController;
import javafx.fxml.FXML;

/**
 * Lose menu controller
 */
public class GameOverController extends JFXController {
    @FXML
    private void backToMenu() {
        GameApplication.app().stop();
        getView().destroy();
    }
}
