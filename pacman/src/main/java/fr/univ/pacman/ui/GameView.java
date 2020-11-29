package fr.univ.pacman.ui;

import fr.univ.engine.assets.AssetsLoader;
import fr.univ.engine.render.RenderEngine;
import fr.univ.engine.render.texture.Texture;
import fr.univ.engine.ui.UiObject;
import fr.univ.pacman.gameplay.GameController;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameView extends UiObject {
    /**
     * A controller
     */
    private final GameController controller;

    /**
     * Pane with all element
     */
    private final StackPane elementPane;

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
        this.elementPane = new StackPane();

        //Load font
        this.font = AssetsLoader.loadFont("PressStart2P.ttf");

        this.construct();
    }

    /**
     * construct all element
     */
    @Override
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

        elementPane.getChildren().addAll(boxScore, boxLife);

        addElement(elementPane);
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
