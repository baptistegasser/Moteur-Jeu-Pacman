package fr.univ.engine.render;

import fr.univ.engine.render.renderer.Renderer;
import fr.univ.engine.utils.CachedResourcesLoader;
import javafx.application.Platform;

import java.util.List;

/**
 * The engine module charged of supervising all rendering related action.
 */
public class RenderEngine {
    /**
     * The configuration of the windows.
     */
    public final WindowConfig window;

    /**
     * The renderer used to render.
     */
    private Renderer<?> renderer;

    /**
     * The texture loader used by the render engine.
     */
    private CachedResourcesLoader textureLoader;

    /**
     * Create a render engine with defaults values.
     */
    public RenderEngine() {
        this.window = new WindowConfig(200, 200, "Game Engine", false, true);
    }

    /**
     * Start the Rendering engine.
     */
    public void start() {
        try {
            JFXApp.setWindowConfig(window);
            JFXApp.startAndWaitUntilReady();
            JFXApp.showWindow();
            this.renderer = JFXApp.getRenderer();
        } catch (InterruptedException e) {
            throw new RenderException("Failed to wait for JFX app start", e);
        } catch (Throwable t) {
            throw new RenderException("Unknown error, failed to start JFX app", t);
        }
    }

    /**
     * Display a list of {@code RenderEntity} on screen.
     *
     * @param entities the entities to render.
     */
    public void render(List<RenderEntity> entities) {
        // Sort the entities
        entities.sort(new RenderEntityComparator());
        // Update them
        entities.forEach(RenderEntity::update);
        // Render them
        renderer.render(entities);
    }

    /**
     * Set the texture loader to be used by the engine to load textures.
     *
     * @param loader the loader to use
     */
    public void setTextureLoader(CachedResourcesLoader loader) {
        this.textureLoader = loader;
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
