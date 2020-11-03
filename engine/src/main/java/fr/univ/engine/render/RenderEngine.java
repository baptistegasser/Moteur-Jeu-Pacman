package fr.univ.engine.render;

import fr.univ.engine.core.CoreException;
import fr.univ.engine.core.Scene;

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
     * The last time the render function was called.
     */
    private static long lastCall = -1;
    /**
     * The rendered frame count.
     */
    private static int frame = 0;

    /**
     * Create a render engine with defaults values.
     */
    public RenderEngine() {
        this.window = new WindowConfig(200, 200, "Game Engine", false);
    }

    /**
     * Start the Rendering engine.
     */
    public void start() {
        try {
            JFXApp.setWindowConfig(window);
            JFXApp.startApp();
            JFXApp.showWindow();
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
            showFPS();
        }
    }

    /**
     * Show the Frame Per Second.
     * TODO not display in terminal
     */
    private void showFPS() {
        if (lastCall == -1 ) {
            lastCall = System.currentTimeMillis();
        }
        frame++;

        if (System.currentTimeMillis() - lastCall >= 1000) {
            System.out.println(frame);
            frame = 0;
            lastCall += 1000;
        }
    }
}
