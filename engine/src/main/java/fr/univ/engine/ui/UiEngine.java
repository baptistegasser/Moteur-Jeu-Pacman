package fr.univ.engine.ui;

import fr.univ.engine.render.JFXApp;

public class UiEngine {
    private UIRoot uiroot;

    public void display(UIElement element) {
        uiroot.display(element);
    }

    public void destroy(UIElement element) {
        element.destroy();
    }

    public void init() {
        this.uiroot = new UIRoot(JFXApp.stackPane);
    }
}
