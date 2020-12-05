package fr.univ.pacman.controller;

import fr.univ.engine.core.GameApplication;
import fr.univ.engine.ui.JFXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController extends JFXController implements Initializable {
    @FXML
    private Slider globalVolumeSlider;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        globalVolumeSlider.setValue(GameApplication.app().soundEngine().getGlobalVolume());
    }

    @FXML
    private void exit() {
        GameApplication.app().soundEngine().setGlobalVolume(globalVolumeSlider.getValue());
        getView().destroy();
    }
}
