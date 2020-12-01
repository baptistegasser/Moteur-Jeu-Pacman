package fr.univ.engine.ui;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * A simple element that can be displayed.
 */
public class UIElement {
    /**
     * The root containing this element.
     */
    private UIRoot root;
    /**
     * The layout of this element.
     */
    protected final Pane layout;

    public UIElement() {
        layout = new StackPane();
    }

    public void setRoot(UIRoot root) {
        this.root = root;
    }

    public final void setPosition(double x, double y) {
        layout.setLayoutX(x);
        layout.setLayoutY(y);
    }
    
    public final void display(UIElement element) {
        root.display(this, element);
    }

    public final void destroy() {
        root.destroy(this);
    }
}
