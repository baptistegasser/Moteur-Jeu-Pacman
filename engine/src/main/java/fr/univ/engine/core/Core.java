package fr.univ.engine.core;

import fr.univ.engine.render.RenderEngine;

/**
 * The core engine, charged to link all subsequent engines.
 */
public final class Core {
    /**
     * Should the engine's main loop quit ?
     */
    private boolean quit = false;
    /**
     * Should the engine's main loop pause ?
     */
    private boolean pause = false;

    /**
     * The render engine instance used to render.
     */
    private RenderEngine render;

    /**
     * Initialize the engine.
     */
    public Core() {
        this.render = new RenderEngine();
    }

    /**
     * @return the render engine instance.
     */
    public RenderEngine getRenderEngine() {
        return render;
    }

    /**
     * Update the current scene of the game.
     *
     * @param scene the new scene
     */
    public void setScene(Scene scene) {
        if (scene == null) {
            throw new CoreException("The scene is null", new NullPointerException());
        }

        render.setScene(scene);
    }

    /**
     * Start the game engine.
     */
    public void start() {
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
    private void mainLoop() throws Exception {
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
    public void quit() {
        pause();
        quit = true;
    }

    /**
     * Pause the main loop.
     */
    public void pause() {
        pause = true;
    }

    /**
     * Unpause the main loop.
     */
    public void unpause() {
        pause = false;
    }
}
