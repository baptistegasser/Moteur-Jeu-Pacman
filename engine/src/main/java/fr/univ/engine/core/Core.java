package fr.univ.engine.core;

import fr.univ.engine.io.IOEngine;
import fr.univ.engine.logging.LoggingEngine;
import fr.univ.engine.physic.PhysicEngine;
import fr.univ.engine.render.JFXApp;
import fr.univ.engine.render.RenderEngine;
import javafx.scene.paint.Color;

import java.util.logging.Level;

/**
 * The core engine, charged to link all subsequent engines.
 * Their can be only one instance at a time of the Core engine.
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
     * The singleton instance of the core engine.
     */
    private static Core instance;

    /**
     * The current game scene.
     */
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
     * The I/O engine instance
     */
    private final IOEngine ioEngine;

    /**
     * Initialize the engine.
     */
    public Core() {
        if (instance != null) {
            throw new IllegalStateException("Core Engine already instantiated");
        } else {
            instance = this;
        }

        this.renderEngine = new RenderEngine();
        this.physicEngine = new PhysicEngine();
        this.ioEngine = IOEngine.getInstance();
        JFXApp.getIsClosingProperty().addListener(o -> this.quit()); // listen for render app closing
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
        LoggingEngine.setLevel(Level.INFO);
        LoggingEngine.enableLogging(Core.class);
        LoggingEngine.setAutoColor(true);

        renderEngine.start();
        renderEngine.preRender(scene.objects());

        ioEngine.start();

        try {
            mainLoop();
        } catch (Throwable t) {
            throw new CoreException("Exception during main loop", t);
        }
    }

    /**
     * The main loop of the game, call other modules such as render and physic.
     */
    private void mainLoop() {
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
                    long s = System.nanoTime();
                    physicEngine.integrate(scene.objects(), t, dt);
                    LoggingEngine.logElapsedTime(s, System.nanoTime(), "PhysicEngine::integrate");

                    // Decrease remaining time to integrate by dt
                    accumulator -= dt;
                    // Increase the time elapsed since engine start by dt
                    t += dt;
                }

                // Render a frame if enough time have elapsed
                if (currentTime - lastFrames >= secondPerFrame) {
                    lastFrames = currentTime;
                    frames += 1;

                    long s = System.nanoTime();
                    ioEngine.nextFrame();
                    LoggingEngine.logElapsedTime(s, System.nanoTime(), "IOEngine::nextFrame");

                    s = System.nanoTime();
                    renderEngine.render(scene.objects());
                    LoggingEngine.logElapsedTime(s, System.nanoTime(), "RenderEngine::render");
                }

                // Display FPS TODO move to render
                if (currentTime - startFrames >= 1) {
                    startFrames = currentTime;
                    LoggingEngine.log(Level.INFO, "FPS: " + frames, Color.DARKCYAN);
                    frames = 0;
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

    /**
     * @return the running instance of the core engine, might be null before init
     */
    public static Core getInstance() {
        return instance;
    }

    /**
     * @return the current game scene.
     */
    public Scene getScene() {
        return scene;
    }
}
