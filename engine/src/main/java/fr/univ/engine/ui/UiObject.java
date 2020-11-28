package fr.univ.engine.ui;

import fr.univ.engine.utils.CachedResourcesLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public abstract class UiObject {
    /**
     * The principal pane
     */
    private StackPane mainPane;

    /**
     * The class used to load our resources.
     */
    protected final CachedResourcesLoader resolver = new CachedResourcesLoader("assets/");

    public UiObject() {
        this.mainPane = new StackPane();
    }

    /**
     * This function permit to construct and organize uiObject
     */
    public abstract void construct();

    /**
     * Add one element on the mainPane
     * @param element the element to add
     */
    public void addElement(Pane element) {
        mainPane.getChildren().add(element);
    }

    public StackPane getMainPane() {
        return mainPane;
    }
}
