package fr.univ.engine.ui;

import fr.univ.engine.render.JFXApp;
import fr.univ.engine.utils.CachedResourcesLoader;
import javafx.scene.layout.StackPane;

public class UiObject {

    /**
     * The principal pane
     */
    private StackPane mainPane;

    /**
     * The resolver for resources
     */
    private final CachedResourcesLoader resolver;

    public UiObject(CachedResourcesLoader resolver) {
        this.mainPane = JFXApp.stackPane;
        this.resolver = resolver;
    }
}
