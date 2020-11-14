package fr.univ.engine.core;

import fr.univ.engine.physic.PhysicEngine;
import fr.univ.engine.render.JFXApp;
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
     * The physic engine instance.
     */
    private final PhysicEngine physicEngine;

    /**
     * Initialize the engine.
     */
    public Core() {
        this.renderEngine = new RenderEngine();
        this.physicEngine = new PhysicEngine();
        JFXApp.getIsClosingProperty().addListener(o -> this.quit()); // listen for render app closing TODO is this bad?
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
        double targetFPS = 60d;
        double secondPerFrame = 1/targetFPS;

        double t = 0.0;
        // Fix the time step. Better than being relative to fps as you can change target fps without changing the physic speed
        double dt = 0.01;
        double accumulator = 0.0;

        while (!quit) {
            double currentTime = System.currentTimeMillis() / 1000d;

            int frames = 0;
            double startFrames = currentTime;
            double lastFrames = currentTime;

            while (!pause) {
                double newTime = System.currentTimeMillis() / 1000d;
                double elapsedTime = newTime - currentTime;
                currentTime = newTime;

                accumulator += elapsedTime; // accumulate

                while (accumulator >= dt) {
                    // Integrate a step of time dt
                    physicEngine.integrate(scene.getObjects(), t, dt);
                    // Decrease remaining time to integrate by dt
                    accumulator -= dt;
                    // Increase the time elapsed since engine start by dt
                    t += dt;
                }

                // Render a frame if enough time have elapsed
                if (currentTime - lastFrames >= secondPerFrame) {
                    lastFrames = currentTime;
                    renderEngine.render(scene.getRenderObjets());
                    frames += 1;
                }

                // Display FPS TODO move to render
                if (currentTime - startFrames >= 1) {
                    startFrames = currentTime;
                    System.out.println("FPS: " + frames);
                    frames =0;
                }
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
