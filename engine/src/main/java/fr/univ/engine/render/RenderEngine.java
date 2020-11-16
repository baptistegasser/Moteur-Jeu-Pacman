package fr.univ.engine.render;

import fr.univ.engine.render.texture.CachedResourcesLoader;
import javafx.application.Platform;
import javafx.scene.image.Image;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The engine module charged of supervising all rendering related action.
 */
public class RenderEngine {
    /**
     * The configuration of the windows.
     */
    public final WindowConfig window;

    /**
     * The area where the rendering is done.
     */
    private ViewPort viewPort;

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
            this.viewPort = new ViewPort(JFXApp.canvas);
            this.viewPort.clear();
        } catch (InterruptedException e) {
            throw new RenderException("Failed to wait for JFX app start", e);
        } catch (Throwable t) {
            throw new RenderException("Unknown error, failed to start JFX app", t);
        }
    }

    /**
     * Function used when we need to be ready to have graphic objects to be rendered in a faster way.
     * Typically when the engine start.
     *
     * @param renderObjets the list of objets to pre render
     */
    public void preRender(List<RenderObject> renderObjets) {
        textureLoader.precacheImages(renderObjets.parallelStream().map(o -> o.textureName).collect(Collectors.toList()));
    }

    /**
     * Display a list of {@code RenderEntity} on screen.
     *
     * @param entities the entities to render.
     */
    public void render(final List<RenderEntity> entities) {
        viewPort.clear();
        for (RenderEntity e : entities) {
            e.update();
            renderObject(e.getRenderObject());
        }
    }

    /**
     * Display a {@code RenderObject} on screen.
     *
     * @param o the object to render.
     */
    private void renderObject(RenderObject o) {
        Image texture = textureLoader.getTexture(o.textureName);
        viewPort.drawImage(texture, o.pos.x, o.pos.y, o.width, o.height);
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
