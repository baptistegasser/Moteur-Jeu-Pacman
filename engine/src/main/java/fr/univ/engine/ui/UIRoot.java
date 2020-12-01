package fr.univ.engine.ui;

import javafx.application.Platform;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Root of the UI display.
 */
public class UIRoot {
    /**
     * The true root used to display in JavaFX
     */
    private final StackPane root;
    /**
     * Map the UIElements displayed by another UIElement.
     */
    private final Map<UIElement, Stack<UIElement>> dependencies;

    public UIRoot(StackPane root) {
        this.root = root;
        this.dependencies = new HashMap<>();
    }

    /**
     * Clear all children of the root.
     */
    public void clear() {
        Platform.runLater(() -> root.getChildren().clear());
    }

    /**
     * Display a single element.
     *
     * @param uiElement the element to display;
     */
    public void display(UIElement uiElement) {
        uiElement.setRoot(this);
        dependencies.put(uiElement, new Stack<>());
        Platform.runLater(() -> root.getChildren().add(uiElement.layout));
    }

    /**
     * Display an element create by another one.
     *
     * @param parent the creator of the element.
     * @param uiElement the element to display.
     */
    public void display(UIElement parent, UIElement uiElement) {
        display(uiElement);
        Stack<UIElement> dependent = dependencies.getOrDefault(parent, new Stack<>());
        dependent.add(uiElement);
        dependencies.put(parent, dependent);
    }

    /**
     * Destroy an element and all the dependent elements displayed by him.
     *
     * @param uiElement the element to destroy.
     */
    public void destroy(UIElement uiElement) {
        Stack<UIElement> dependent = dependencies.get(uiElement);
        dependencies.put(uiElement, null);

        if (dependent != null) {
            dependent.forEach(UIElement::destroy);
        }

        Platform.runLater(() -> root.getChildren().remove(uiElement.layout));
    }
}
