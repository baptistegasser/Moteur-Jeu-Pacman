package fr.univ.engine.core;

import fr.univ.engine.render.RenderEngine;

/**
 * The core engine, charged to link all subsequent engines.
 */
public final class Core {
    /**
     * Should the engine's main loop quit ?
     */
    private static boolean quit = false;
    /**
     * Should the engine's main loop pause ?
     */
    private static boolean pause = false;

    /**
     * The render engine instance used to render.
     */
    private static RenderEngine render;

    /**
     * Initialize the engine.
     */
    public static void init() {
        render = new RenderEngine();
    }

    /**
     * @return the render engine instance.
     */
    public static RenderEngine render() {
        return render;
    }

    /**
     * Update the current scene of the game.
     *
     * @param scene the new scene
     */
    public static void setScene(Scene scene) {
        if (scene == null) {
            throw new CoreException("The scene is null", new NullPointerException());
        }

        render.setScene(scene);
    }

    /**
     * Start the game engine.
     */
    public static void start() {
        render.start();

        try {
            mainLoop();
        } catch (Throwable t) {
            throw new CoreException("Exception during main loop", t);
        }
    }

    /**
     * The main loop of the game, call other modules such as render and physic.
     *
     * @throws Exception something can fail in the loop or sub modules
     */
    private static void mainLoop() throws Exception {
        while (!quit) {
            while (!pause) {
                render.render();
                Thread.sleep(10);
            }
        }
    }

    /**
     * Quit the main loop, stop the engine.
     */
    public static void quit() {
        pause();
        quit = true;
    }

    /**
     * Pause the main loop.
     */
    public static void pause() {
        pause = true;
    }
}
