package fr.univ.engine.ui;

import javafx.scene.Parent;

/**
 * A view loaded from fxml.
 */
public class JFXView extends UIElement {
    /**
     * The controller of this view, can be null.
     */
    private JFXController controller;

    public JFXView(Parent jfxRoot) {
        layout.getChildren().setAll(jfxRoot);
    }

    public void setController(JFXController controller) {
        this.controller = controller;
        this.controller.setView(this);
    }

    public JFXController controller() {
        return controller;
    }
}
