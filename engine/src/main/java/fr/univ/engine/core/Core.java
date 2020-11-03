package fr.univ.engine.core;

import fr.univ.engine.render.RenderEngine;

public final class Core {
    private static boolean quit = false;
    private static boolean pause = false;

    private static RenderEngine render;

    public static void init() {
        render = new RenderEngine();
    }

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
            }
        }
    }

    public static void quit() {
        pause();
        quit = true;
    }

    public static void pause() {
        pause = true;
    }
}
