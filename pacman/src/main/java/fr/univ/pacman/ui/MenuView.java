package fr.univ.pacman.ui;

import fr.univ.engine.render.JFXApp;
import fr.univ.engine.render.RenderEngine;
import fr.univ.engine.utils.CachedResourcesLoader;
import fr.univ.pacman.gameplay.GamePlay;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuView {
    private StackPane stackPane;

    private final CachedResourcesLoader resolver;

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

    public MenuView(CachedResourcesLoader resolver) {
        stackPane = JFXApp.stackPane;
        this.resolver = resolver;
        //Load font
        this.font = this.resolver.getFont("font/PressStart2P.ttf");

        // Create and configure Box
        boxScore = new HBox();
        boxScore.setPadding(new Insets(15,0,0,15));

        // Set score text
        scoreText = new Text("score 0");
        scoreText.setFont(font);
        scoreText.setStyle("-fx-font-size: 15;");
        scoreText.setFill(Color.RED);

        RenderEngine.runOnFXThread(() -> boxScore.getChildren().addAll(scoreText));
        RenderEngine.runOnFXThread(() -> stackPane.getChildren().addAll(boxScore));

        // Create and configure Box
        boxLife = new HBox();
        boxLife.setPadding(new Insets(15,0,0,380));
        boxLife.setStyle("-fx-spacing: 15;");

        //Set Life content
        for (int i = 0; i< GamePlay.getInventory().getLife();i++) {
            Image life = resolver.getImage("sprites/pacman.png");
            ImageView lifeImage = new ImageView(life);
            lifeImage.setFitHeight(16);
            lifeImage.setFitWidth(16);
            RenderEngine.runOnFXThread(() -> boxLife.getChildren().add(lifeImage));
        }
        RenderEngine.runOnFXThread(() -> stackPane.getChildren().addAll(boxLife));
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
        for (int i = 0; i< GamePlay.getInventory().getLife();i++) {
            Image life = resolver.getImage("sprites/pacman.png");
            ImageView lifeImage = new ImageView(life);
            lifeImage.setFitHeight(16);
            lifeImage.setFitWidth(16);
            RenderEngine.runOnFXThread(() -> boxLife.getChildren().add(lifeImage));
        }
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
}
