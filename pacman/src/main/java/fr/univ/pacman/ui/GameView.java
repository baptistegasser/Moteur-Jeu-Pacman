package fr.univ.pacman.ui;

import fr.univ.engine.assets.AssetsLoader;
import fr.univ.engine.render.RenderEngine;
import fr.univ.engine.ui.UIElement;
import fr.univ.pacman.controller.GameController;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameView extends UIElement {
    /**
     * A controller
     */
    private final GameController controller;

    /**
     * The pane for the player score
     */
    private HBox boxScore;
    private Text scoreText;

    /**
     * The pane for the player score
     */
    private HBox boxLife;

    /**
     * Font custom
     */
    private Font font;

    /**
     * Prepare elements
     */
    public GameView(GameController gameController) {
        this.controller = gameController;

        //Load font
        this.font = AssetsLoader.loadFont("PressStart2P.ttf");

        this.construct();
    }

    /**
     * construct all element
     */
    public void construct() {
        // Create and configure Box
        boxScore = new HBox();
        boxScore.setPadding(new Insets(15,0,0,15));

        // Set score text
        scoreText = new Text("score 0");
        scoreText.setFont(font);
        scoreText.setStyle("-fx-font-size: 15;");
        scoreText.setFill(Color.RED);

        boxScore.getChildren().addAll(scoreText);

        // Create and configure Box
        boxLife = new HBox();
        boxLife.setPadding(new Insets(15,0,0,380));
        boxLife.setStyle("-fx-spacing: 15;");

        //Set Life content
        constructLifeView();

        layout.getChildren().addAll(boxScore, boxLife);
    }

    /**
     * Update the content of score box
     */
    private void updateScoreView() {
        RenderEngine.runOnFXThread(() -> boxScore.getChildren().clear());
        scoreText.setFill(Color.RED);
        scoreText.setFont(Font.font(30));
        RenderEngine.runOnFXThread(() -> boxScore.getChildren().addAll(scoreText));
    }

    /**
     * Update the content of life box
     */
    public void updateLifeView() {
        RenderEngine.runOnFXThread(() -> boxLife.getChildren().clear());
        constructLifeView();
    }

    /**
     * Update score text
     *
     * @param score the new score
     */
    public void setScoreText(int score) {
        scoreText = new Text("score "+score);
        scoreText.setFont(font);
        scoreText.setStyle("-fx-font-size: 15;");
        scoreText.setFill(Color.RED);
        this.updateScoreView();
    }

    /**
     * Add content to life box
     */
    public void constructLifeView() {
        for (int i = 0; i< controller.getInventory().getLife(); i++) {
            Image image = AssetsLoader.loadImage("sprites/pacman.png");
            ImageView lifeImage = new ImageView(image);
            lifeImage.setFitHeight(16);
            lifeImage.setFitWidth(16);
            RenderEngine.runOnFXThread(() -> boxLife.getChildren().add(lifeImage));
        }
    }
}
