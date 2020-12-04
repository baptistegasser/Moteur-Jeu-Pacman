package fr.univ.pacman.controller;

import fr.univ.engine.ui.JFXController;
import fr.univ.pacman.PacMan;
import javafx.fxml.FXML;

public class MenuController extends JFXController {

    @FXML
    public void start() {
        PacMan.app().play();
        getView().destroy();
    }

    @FXML
    public void quit() {
        PacMan.app().quit();
    }
}
