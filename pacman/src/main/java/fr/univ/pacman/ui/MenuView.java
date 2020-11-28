package fr.univ.pacman.ui;

import fr.univ.engine.render.JFXApp;
import fr.univ.engine.ui.UiObject;
import fr.univ.pacman.gameplay.GameMenu;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class MenuView extends UiObject {
    /**
     * The controller for this view
     */
    private GameMenu controller;

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
     * @param controller the controller for this view
     */
    public MenuView(GameMenu controller) {
        this.controller = controller;
        elementPane = new StackPane();
        construct();
    }

    /**
     * construct all element
     */
    @Override
    public void construct() {
        elementPane.setStyle("-fx-background-color: Black;");

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
                System.out.println("Play");
                controller.startGame();
            }
        });

        Image imageQuit = this.resolver.getImage("menu/quitButton.png");
        ImageView imageViewquit = new ImageView(imageQuit);

        Button buttonQuit = new Button();
        buttonQuit.setGraphic(imageViewquit);
        buttonQuit.setStyle("-fx-background-color: transparent;");

        buttonQuit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Quit");
                controller.quitGame();

            }
        });

        boxElement.getChildren().addAll(button);
        boxElement.getChildren().addAll(buttonQuit);
        elementPane.getChildren().addAll(boxElement);

        addElement(elementPane);
    }
}
