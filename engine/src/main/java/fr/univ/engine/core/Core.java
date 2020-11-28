package fr.univ.engine.core;

import fr.univ.engine.core.config.Config;
import fr.univ.engine.io.IOEngine;
import fr.univ.engine.logging.LoggingEngine;
import fr.univ.engine.physic.PhysicEngine;
import fr.univ.engine.physic.component.PhysicComponent;
import fr.univ.engine.render.JFXApp;
import fr.univ.engine.render.RenderEngine;
import fr.univ.engine.render.component.RenderComponent;
import fr.univ.engine.sound.SoundEngine;
import javafx.scene.paint.Color;

import java.util.Arrays;

/**
 * The core engine, charged to link all subsequent engines.
 * Their can be only one instance at a time of the Core engine.
 */
public final class Core {
    /**
     * The configuration of the current game.
     */
    private Config config;

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
     * The sound engine instance
     */
    private final SoundEngine soundEngine;

    /**
     * The current game level.
     */
    private Level level;

    /**
     * Should the engine's main loop quit ?
     */
    private boolean quit = false;
    /**
     * Should the engine's main loop pause ?
     */
    private boolean pause = false;

    /**
     * Time step between call to the physic engine.
     */
    private final double dt = 0.01;
    /**
     * Target time step between two frame render.
     */
    private final double SECOND_PER_FRAME = 1/60d;

    /**
     * Create a new instance of the core engine.
     *
     * @param args arguments passed from the commande line.
     */
    Core(String... args) {
        // TODO parse arguments
        System.out.println("Args: " + Arrays.toString(args));

        this.config = new Config();
        this.renderEngine = new RenderEngine(config);
        this.physicEngine = new PhysicEngine(this);
        this.ioEngine = new IOEngine();
        this.soundEngine = new SoundEngine();
    }

    /**
     * Initialize the sub engines.
     */
    void init() {
        LoggingEngine.setLevel(java.util.logging.Level.INFO);
        LoggingEngine.setAutoColor(true);

        renderEngine.init();
        ioEngine.start();

        JFXApp.getIsClosingProperty().addListener(o -> this.quit()); // listen for render app closing
    }

    /**
     * Start the game.
     */
    public void start() {
        System.out.println("Starting " + config.title + " v" + config.version);
        renderEngine.showWindow();
        loop();
    }

    private void loop() {
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
                accumulator = integrate(accumulator); // Integrate

                // Render a frame if enough time have elapsed
                if (currentTime - lastFrames >= SECOND_PER_FRAME) {
                    lastFrames = currentTime;
                    frames += 1;

                    long s = System.nanoTime();
                    ioEngine.nextFrame();
                    LoggingEngine.logElapsedTime(s, System.nanoTime(), "IOEngine::nextFrame");

                    s = System.nanoTime();
                    renderEngine.render(level.getEntitiesWithComponent(RenderComponent.class));
                    LoggingEngine.logElapsedTime(s, System.nanoTime(), "RenderEngine::render");
                }

                // Display FPS TODO move to render
                if (currentTime - startFrames >= 1) {
                    startFrames = currentTime;
                    LoggingEngine.info("FPS: " + frames, Color.DARKCYAN);
                    frames = 0;
                }
            }
        }
    }

    private double integrate(double accumulator) {
        while (accumulator >= dt) {
            // Integrate a step of time dt
            long s = System.nanoTime();
            physicEngine.integrate(level.getEntitiesWithComponent(PhysicComponent.class));
            LoggingEngine.logElapsedTime(s, System.nanoTime(), "PhysicEngine::integrate");

            // Decrease remaining time to integrate by dt
            accumulator -= dt;
        }
        return accumulator;
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
     * Quit the main loop, stop the engine.
     */
    public void quit() {
        pause();
        quit = true;
    }


    //*******************************//
    //*     getters and setters     *//
    //*******************************//

    /**
     * @return the physic engine handled by this core instance.
     */
    PhysicEngine physicEngine() {
        return this.physicEngine;
    }

    /**
     * @return the input/output engine handled by this core instance.
     */
    IOEngine IOEngine() {
        return this.ioEngine;
    }

    /**
     * @return the sound engine handled by this core instance.
     */
    SoundEngine soundEngine() {
        return this.soundEngine;
    }

    /**
     * @return the configuration of the current game.
     */
    public Config config() {
        return config;
    }

    /**
     * Set the current level.
     *
     * @param level the new level.
     */
    void setLevel(Level level) {
        this.level = level;
    }

    /**
     * Get the game level.
     */
    public Level getLevel() {
        return this.level;
    }
}
