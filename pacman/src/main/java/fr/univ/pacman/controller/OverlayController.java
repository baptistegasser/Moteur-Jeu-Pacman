package fr.univ.pacman.controller;

import fr.univ.engine.assets.AssetsLoader;
import fr.univ.engine.core.GameApplication;
import fr.univ.engine.physic.PhysicComponent;
import fr.univ.engine.time.FutureTask;
import fr.univ.engine.ui.JFXController;
import fr.univ.pacman.component.ai.GhostAIComponent;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * Overlay display controller.
 */
public class OverlayController extends JFXController implements Initializable {
    @FXML
    private Label score;
    @FXML
    private HBox lives;
    @FXML
    private VBox ready;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameApplication.app().globalVars().addListener("score", ($, $$, s) -> Platform.runLater(() -> score.setText("SCORE " + s)));
        GameApplication.app().globalVars().addListener("lives", ($, $$, lifeCount) -> Platform.runLater(() -> displayLives(((int) lifeCount))));

        blink();

        FutureTask futureTask = new FutureTask(4, TimeUnit.SECONDS, "DELETEREADY", () -> {
            Platform.runLater(() -> ready.getChildren().clear());
        });
        GameApplication.app().timeEngine().schedule(futureTask);

        score.setText("SCORE " + GameApplication.app().globalVars().getInt("score"));
        displayLives(GameApplication.app().globalVars().getInt("lives"));
    }

    private void displayLives(int count) {
        lives.getChildren().clear();
        Image image = AssetsLoader.loadImage("sprites/pacman.png");
        for (int i = 0; i < count; i++) {
            ImageView lifeImage = new ImageView(image);
            lifeImage.setFitHeight(16);
            lifeImage.setFitWidth(16);
            lives.getChildren().add(lifeImage);
        }
    }

    private void blink () {
        FadeTransition textTransition = new FadeTransition(Duration.seconds(0.4), ready);
        textTransition.setAutoReverse(true);
        textTransition.setFromValue(0);
        textTransition.setToValue(1);
        textTransition.setCycleCount(Transition.INDEFINITE);
        textTransition.play();
    }
}
