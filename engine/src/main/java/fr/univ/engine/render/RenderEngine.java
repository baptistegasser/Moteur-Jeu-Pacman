package fr.univ.engine.render;

import fr.univ.engine.core.config.Config;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.render.renderer.Renderer;
import javafx.application.Platform;

import java.util.List;

/**
 * The engine module charged of supervising all rendering related action.
 */
public class RenderEngine {
    /**
     * The renderer used to render.
     */
    private Renderer<?> renderer;

    /**
     * Create a render engine with defaults values.
     *
     * @param config the config of the current game which
     *               also contain the window config.
     */
    public RenderEngine(Config config) {
        JFXApp.setConfig(config);
    }

    /**
     * Start the Rendering engine.
     */
    public void init() {
        try {
            JFXApp.startAndWaitUntilReady();
            this.renderer = JFXApp.getRenderer();
        } catch (InterruptedException e) {
            throw new RenderException("Failed to wait for JFX app start", e);
        } catch (Throwable t) {
            throw new RenderException("Unknown error, failed to start JFX app", t);
        }
    }

    /**
     * Display a list of {@code Entity} on screen.
     *
     * @param entities the entities to render.
     */
    public void render(List<Entity> entities) {
        // Render them
        renderer.render(entities);
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

    /**
     * hide the window.
     */
    public void hideWindow() {
        try {
            JFXApp.hideWindow();
        } catch (InterruptedException e) {
            throw new RenderException("Failed to hide the window", e);
        }
    }

    /**
     * Util function that assert a runnable is run on the JavaFX Thread.
     * Note: this should be used only for code that REALLY need the Thread as it had
     * overhead and so increase time to execute.
     *
     * @param r the runnable to run
     */
    public static void runOnFXThread(Runnable r) {
        if (Platform.isFxApplicationThread()) {
            r.run();
        } else {
            Platform.runLater(r);
        }
    }
}
