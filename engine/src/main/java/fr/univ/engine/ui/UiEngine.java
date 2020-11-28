package fr.univ.engine.ui;

import fr.univ.engine.render.JFXApp;
import fr.univ.engine.render.RenderException;
import javafx.application.Platform;

public class UiEngine {

    /**
     * Draw the uiObject element on the JFXApp mainPane
     *
     * @param uiObject The view to draw
     */
    public void draw(UiObject uiObject) {
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
}
