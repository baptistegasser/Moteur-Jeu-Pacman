package fr.univ.pacman.ui;

import fr.univ.engine.assets.AssetsLoader;
import fr.univ.engine.ui.UiObject;
import fr.univ.pacman.gameplay.GameMenu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class MenuView extends UiObject {
    /**
     * The controller for this view
     */
    private final GameMenu controller;

    /**
     * Pane with all element
     */
    private final StackPane elementPane;

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

        //Config box element
        VBox boxElement = new VBox();
        boxElement.setPadding(new Insets(-150,0,0,0));
        boxElement.setSpacing(20);
        boxElement.setAlignment(Pos.CENTER);

        // Add title image
        Image image = AssetsLoader.loadImage("menu/pacmanTitle.png");
        ImageView titleImage = new ImageView(image);
        titleImage.setFitHeight(65);
        titleImage.setFitWidth(300);

        //Add play button
        Image imagePlay = AssetsLoader.loadImage("menu/playButton.png");
        ImageView imageViewPlay = new ImageView(imagePlay);

        Button buttonPlay = new Button();
        buttonPlay.setGraphic(imageViewPlay);
        buttonPlay.setStyle("-fx-background-color: transparent;");

        buttonPlay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Play");
                controller.startGame();
            }
        });

        //Add quit button
        Image imageQuit = AssetsLoader.loadImage("menu/quitButton.png");
        ImageView imageViewQuit = new ImageView(imageQuit);

        Button buttonQuit = new Button();
        buttonQuit.setGraphic(imageViewQuit);
        buttonQuit.setStyle("-fx-background-color: transparent;");

        buttonQuit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Quit");
                controller.quitGame();

            }
        });

        boxElement.getChildren().addAll(titleImage, buttonPlay, buttonQuit);
        elementPane.getChildren().addAll(boxElement);

        addElement(elementPane);
    }
}
