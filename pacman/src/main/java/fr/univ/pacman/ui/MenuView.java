package fr.univ.pacman.ui;

import fr.univ.engine.render.JFXApp;
import fr.univ.engine.render.RenderEngine;
import fr.univ.engine.utils.CachedResourcesLoader;
import fr.univ.pacman.gameplay.GameMenu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class MenuView {
    /**
     * The controller for this view
     */
    private GameMenu controller;

    /**
     * The resolver for resources
     */
    private final CachedResourcesLoader resolver;

    /**
     * Pane of JFXApp
     */
    private StackPane mainPane;
    /**
     * Pane with all element
     */
    private StackPane elementPane;

    /**
     * Box with principal element
     */
    private VBox boxElement;
    ImageView tileImage;

    /**
     * Prepare elements
     * @param resolver the resolver for resources
     * @param controller the controller for this view
     */
    public MenuView(CachedResourcesLoader resolver, GameMenu controller) {
        this.controller = controller;
        mainPane = JFXApp.stackPane;
        elementPane = new StackPane();
        this.resolver = resolver;
    }

    /**
     * construct all element
     */
    public void construct() {
        boxElement = new VBox();
        boxElement.setPadding(new Insets(100,0,0,100));
        boxElement.setSpacing(20);

        Image image = this.resolver.getImage("menu/pacmanTitle.png");
        tileImage = new ImageView(image);
        tileImage.setFitHeight(65);
        tileImage.setFitWidth(300);

        boxElement.getChildren().addAll(tileImage);

        Image imagePlay = this.resolver.getImage("menu/playButton.png");
        ImageView imageView = new ImageView(imagePlay);

        Button button = new Button();
        button.setGraphic(imageView);
        button.setStyle("-fx-background-color: transparent;");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                RenderEngine.runOnFXThread(() -> elementPane.getChildren().clear());
                controller.startGame();
            }
        });

        boxElement.getChildren().addAll(button);
        RenderEngine.runOnFXThread(() -> elementPane.getChildren().addAll(boxElement));
        RenderEngine.runOnFXThread(() -> mainPane.getChildren().addAll(elementPane));
    }

    public StackPane getMainPane() {
        return mainPane;
    }
}
