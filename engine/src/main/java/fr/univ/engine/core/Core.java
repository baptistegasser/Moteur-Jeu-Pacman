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

    private Scene scene;

    /**
     * The render engine instance used to render.
     */
    private final RenderEngine renderEngine;

    /**
     * Initialize the engine.
     */
    public Core() {
        this.renderEngine = new RenderEngine();
    }

    /**
     * @return the render engine instance.
     */
    public RenderEngine getRenderEngine() {
        return renderEngine;
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

        this.scene = scene;
    }

    /**
     * Start the game engine.
     */
    public void start() {
        renderEngine.start();

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
                // TODO handling of delta time, framerate for physics
                // TODO physic

                for (GameObject o : scene.getObjects()) {
                    renderEngine.renderObject(o.renderObject);
                }
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
