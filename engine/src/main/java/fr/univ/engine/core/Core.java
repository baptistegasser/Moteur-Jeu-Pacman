package fr.univ.engine.core;

import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.core.entity.Level;
import fr.univ.engine.io.IOEngine;
import fr.univ.engine.logging.LoggingEngine;
import fr.univ.engine.physic.PhysicEngine;
import fr.univ.engine.physic.PhysicComponent;
import fr.univ.engine.render.JFXApp;
import fr.univ.engine.render.RenderEngine;
import fr.univ.engine.render.RenderComponent;
import fr.univ.engine.sound.SoundEngine;
import fr.univ.engine.time.TimeEngine;
import fr.univ.engine.ui.UiEngine;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.util.Arrays;

/**
 * The core engine, charged to link all subsequent engines.
 * Their can be only one instance at a time of the Core engine.
 */
public final class Core {
    /**
     * The possible value for the {@link #state} of this engine.
     */
    private enum State { INIT, PLAY, PAUSE, STOP, QUIT }

    /**
     * Store the current state of the engine.
     * The var is volatile as another thread might change it's value.
     */
    private volatile State state;

    /**
     * The configuration of the current game.
     */
    private final Config config;

    /**
     * The render engine instance used to render.
     */
    private final RenderEngine renderEngine;

    /**
     * The physic engine instance.
     */
    private final PhysicEngine physicEngine;

    /**
     * The time engine instance.
     */
    private final TimeEngine timeEngine;

    /**
     * The I/O engine instance
     */
    private final IOEngine ioEngine;

    /**
     * The sound engine instance
     */
    private final SoundEngine soundEngine;

    /**
     * The ui engine instance
     */
    private final UiEngine uiEngine;

    /**
     * The current game level.
     */
    private Level level;

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

        // Configure handling of unhandled exceptions
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            LoggingEngine.severe("Unhandled exception, the game will now crash.");
            close();
            e.printStackTrace();
            System.exit(1);
        });

        this.config = new Config();
        this.renderEngine = new RenderEngine(config);
        this.physicEngine = new PhysicEngine(this);
        this.timeEngine = new TimeEngine();
        this.ioEngine = new IOEngine();
        this.soundEngine = new SoundEngine();
        this.uiEngine = new UiEngine();
    }

    public void start(GameApplication app) {
        app.config(this.config);
        preInit();

        System.out.println("Starting " + config.title + " v" + config.version);
        while (state != State.QUIT) {
            this.init();
            state = State.INIT;
            app.initGame();

            while (state != State.STOP && state != State.QUIT) {
                loop();

            }
        }

        this.close();
    }

    /**
     * The first initialization done only once.
     */
    private void preInit() {
        try {
            renderEngine.start();
            JFXApp.showWindow();
            JFXApp.getIsClosingProperty().addListener(o -> this.quit()); // listen for render app closing
            ioEngine.start();
            uiEngine.start();
        } catch (InterruptedException e) {
            throw new CoreException("Failed to init windows", e);
        }
    }

    /**
     * Initialize the sub engines each time a game start.
     */
    private void init() {
        LoggingEngine.setLevel(java.util.logging.Level.INFO);
        LoggingEngine.setAutoColor(true);

        ioEngine.init();
        uiEngine.init();
        physicEngine.init();
        soundEngine.init();
    }

    /**
     * Close the app.
     */
    private void close() {
        System.out.println("Exiting " + config.title + " v" + config.version);
        Platform.exit();
    }

    private void loop() {
        double accumulator = 0.0;
        double currentTime = System.currentTimeMillis() / 1000d;

        int frames = 0;
        double startFrames = currentTime;
        double lastFrames = currentTime;

        while (state == State.PLAY) {
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

                // Update the components
                for (Entity e : level.getEntities()) {
                    for (Component c : e.getComponents()) {
                        c.update();
                    }
                }
            }

            // Display FPS TODO move to render
            if (currentTime - startFrames >= 1) {
                startFrames = currentTime;
                LoggingEngine.info("FPS: " + frames, Color.DARKCYAN);
                frames = 0;
            }
        }
    }

    /**
     * Update the physic and time engine.
     *
     * @param accumulator
     * @return
     */
    private double integrate(double accumulator) {
        while (accumulator >= dt) {
            // Integrate a step of time dt
            long s = System.nanoTime();
            physicEngine.integrate(level.getEntitiesWithComponent(PhysicComponent.class));
            LoggingEngine.logElapsedTime(s, System.nanoTime(), "PhysicEngine::integrate");

            timeEngine.update();

            // Update the components
            for (Entity e : level.getEntities()) {
                for (Component c : e.getComponents()) {
                    c.fixedUpdate();
                }
            }

            // Decrease remaining time to integrate by dt
            accumulator -= dt;
        }
        return accumulator;
    }

    void play() {
        this.state = State.PLAY;
    }

    void pause() {
        if (this.state == State.PLAY) {
            this.state = State.PAUSE;
        } else {
            LoggingEngine.warning("Attempting to pause while the game is not in play" + state);
        }
    }

    void unpause() {
        if (this.state == State.PAUSE) {
            this.state = State.PLAY;
        } else {
            LoggingEngine.warning("Attempting to unpause while the game is not paused" + state);
        }
    }

    void stop() {
        this.state = State.STOP;
    }

    void quit() {
        this.state = State.QUIT;
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
     * @return the time engine handled by this core instance.
     */
    TimeEngine timeEngine() {
        return this.timeEngine;
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
     * @return the ui engine handled by this core instance.
     */
    UiEngine uiEngine() {
        return uiEngine;
    }

    /**
     * @return the render engine handled by this core instance.
     */
    RenderEngine renderEngine() {
        return renderEngine;
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
