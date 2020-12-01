package fr.univ.engine.ui;

/**
 * The controller of a FXML View.
 */
public abstract class JFXController {
    /**
     * The view controlled by this instance.
     */
    private JFXView view;

    public void setView(JFXView view) {
        this.view = view;
    }

    protected final JFXView getView() {
        return this.view;
    }
}
