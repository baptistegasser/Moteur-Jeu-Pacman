package fr.univ.engine.render;

import fr.univ.engine.core.CoreException;
import fr.univ.engine.core.Entity;
import fr.univ.engine.core.Scene;
import fr.univ.engine.render.texture.TextureLoader;
import fr.univ.engine.render.texture.Textured;
import javafx.scene.image.Image;

/**
 * The engine module charged of supervising all rendering related action.
 */
public class RenderEngine {
    /**
     * The game scene to render
     */
    private Scene scene;
    /**
     * The configuration of the windows.
     */
    public WindowConfig window;

    /**
     * The camera used to display content.
     */
    public Camera camera;

    /**
     * The texture loader used by the render engine.
     */
    private TextureLoader textureLoader;

    /**
     * The last time the render function was called.
     */
    private static long lastCall = System.currentTimeMillis();
    /**
     * The rendered frame count.
     */
    private static int frame = 0;

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
            JFXApp.startApp();
            JFXApp.showWindow();
            this.camera = new Camera(JFXApp.canvas);
            this.camera.clear();
        } catch (InterruptedException e) {
            throw new CoreException("Failed to wait for JFX app start", e);
        } catch (Throwable t) {
            throw new CoreException("Unknown error, failed to start JFX app", t);
        }
    }

    /**
     * Update the displayed scene.
     *
     * @param scene the new scene
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * The main function of this engine module, render the current scene on screen.
     */
    public void render() {
        if (window.showFPSCounter) {
            frame++;

            long current = System.currentTimeMillis();
            if ((current - lastCall) >= 1000) {
                System.out.println("FPS: " + frame);
                frame = 0;
                lastCall = current;
            }
        }
        for (Entity e : scene.getEntities()) {
            if (e instanceof Textured) {
                Image texture = textureLoader.getTexture(((Textured) e).getTextureName());
                camera.renderEntity(e, texture);
            }
        }
    }

    public void setTextureResolver(TextureLoader resolver) {
        this.textureLoader = resolver;
    }
}
