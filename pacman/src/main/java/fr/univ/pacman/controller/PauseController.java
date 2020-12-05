package fr.univ.pacman.controller;

import fr.univ.engine.assets.AssetsLoader;
import fr.univ.engine.core.GameApplication;
import fr.univ.engine.ui.JFXController;
import javafx.fxml.FXML;

public class PauseController extends JFXController {
    @FXML
    private void openSettings() {
        display(AssetsLoader.loadView("Settings.fxml"));
    }

    @FXML
    private void stop() {
        getView().destroy();
        GameApplication.app().stop();
    }

    @FXML
    private void unpause() {
        getView().destroy();
        GameApplication.app().unpause();
    }
}
