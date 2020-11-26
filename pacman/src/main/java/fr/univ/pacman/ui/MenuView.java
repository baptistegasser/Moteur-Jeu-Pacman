package fr.univ.pacman.ui;

import fr.univ.engine.render.JFXApp;
import fr.univ.engine.render.RenderEngine;
import fr.univ.engine.utils.CachedResourcesLoader;
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
    }

    /**
     * Update the content of score pane
     */
    private void updateScoreView() {
        RenderEngine.runOnFXThread(() -> boxScore.getChildren().clear());
        scoreText.setFill(Color.RED);
        scoreText.setFont(Font.font(30));
        RenderEngine.runOnFXThread(() -> boxScore.getChildren().addAll(scoreText));
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
