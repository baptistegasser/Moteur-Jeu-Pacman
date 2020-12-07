package fr.univ.engine.ui;

import fr.univ.engine.render.JFXApp;

public class UiEngine {
    private UIRoot uiroot;

    /**
     * Clea the UIRoot
     */
    public void clear() {
        uiroot.clear();
    }

    /**
     * Display an UI element
     * @param element to display
     */
    public void display(UIElement element) {
        uiroot.display(element);
    }

    /**
     * Destroy an UI element
     * @param element to destroy
     */
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
