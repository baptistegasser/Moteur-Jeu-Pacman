package fr.univ.engine.ui;

import fr.univ.engine.render.JFXApp;
import fr.univ.engine.render.RenderException;
import javafx.application.Platform;

public class UiEngine {

    /**
     * The actual view show on window
     */
    private UiObject uiObject;

    /**
     * Draw the uiObject element on the JFXApp mainPane
     *
     * @param uiObject The view to draw
     */
    public void draw(UiObject uiObject) {
        this.uiObject = uiObject;
        Platform.runLater(() -> JFXApp.stackPane.getChildren().add(uiObject.getMainPane()));
    }

    /**
     * Show the window.
     */
    public void showWindow() {
        try {
            JFXApp.showWindow();
        } catch (InterruptedException e) {
            throw new RenderException("Failed to show the window", e);
        }
    }

    public void clear() {
        Platform.runLater(() -> this.uiObject.getMainPane().getChildren().clear());
    }
}
