package fr.univ.engine.ui;

import fr.univ.engine.render.JFXApp;

public class UiEngine {
    private UIRoot uiroot;

    public void clear() {
        uiroot.clear();
    }

    public void display(UIElement element) {
        uiroot.display(element);
    }

    public void destroy(UIElement element) {
        element.destroy();
    }

    /**
     * Handle one time config when being launched for first time.
     */
    public void start() {
        this.uiroot = new UIRoot(JFXApp.uiRoot);
    }

    /**
     * Initialize the current state of the ui.
     */
    public void init() {
        this.clear();
    }
}
