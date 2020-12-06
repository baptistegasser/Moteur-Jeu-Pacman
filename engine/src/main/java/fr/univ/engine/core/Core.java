package fr.univ.engine.core;

import fr.univ.engine.core.entity.Level;
import fr.univ.engine.io.IOEngine;
import fr.univ.engine.logging.LoggingEngine;
import fr.univ.engine.physic.PhysicComponent;
import fr.univ.engine.physic.PhysicEngine;
import fr.univ.engine.render.JFXApp;
import fr.univ.engine.render.RenderComponent;
import fr.univ.engine.render.RenderEngine;
import fr.univ.engine.sound.SoundEngine;
import fr.univ.engine.time.TimeEngine;
import fr.univ.engine.ui.UiEngine;
import javafx.application.Platform;
import javafx.scene.paint.Color;

/**
 * The core engine, charged to link all subsequent engines.
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
     * Delta time step between call to the physic engine.
     */
    private static final double DT = 0.01;
    /**
     * Target time step between two frame render.
     */
    private static final double SECOND_PER_FRAME = 1/60d;

    /**
     * Create a new instance of the core engine.
     *
     * @param args arguments passed from the commande line.
     */
    Core(String... args) {
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

    /**
     * Entry point of the core engine, start a game.
     *
     * @param app the game to run.
     */
    public void start(GameApplication app) {
        System.out.println("Starting " + config.title + " v" + config.version);

        app.config(this.config);
        preInit();

        while (state != State.QUIT) {
            // Init the game
            this.init();
            state = State.INIT;
            app.initGame();

            // Wait for the game to either start to play or quit the engine
            while (this.state != State.PLAY && this.state != State.QUIT) {
                try {
                    synchronized (this) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    throw new CoreException("Failed to wait for game start", e);
                }
            }
            // After waiting quit or call the game's startPlay() method
            if (state == State.QUIT) {
                break;
            } else {
                app.startPlay();
            }

            // While the game is playing or paused, run the main loop
            while (state != State.STOP && state != State.QUIT) {
                loop();
            }
        }

        this.close();
    }

    /**
     * The first initialization done only once to setup engine notably JavaFX
     * related configuration that should be called here.
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
     * Initialize the sub engines each time a new game is played.
     */
    private void init() {
        LoggingEngine.setLevel(java.util.logging.Level.INFO);
        LoggingEngine.setAutoColor(true);

        ioEngine.init();
        uiEngine.init();
        physicEngine.init();
        soundEngine.init();
        timeEngine.init();
    }

    /**
     * The main loop of the engine.
     * Charged of updating the different engines and components of
     * a running game at the right time.
     */
    private void loop() {
        double accumulator = 0.0; // The accumulated time to simulate
        double currentTime = System.currentTimeMillis() / 1000d; // The current time

        double startFrames = currentTime;   // The time at which we rendered the first frame
        double lastFrames = currentTime;    // Store the last time we rendered a frame
        int frames = 0;                     // The number of frames since startFrames

        // Loop while state is valid (no paused, stopped...)
        while (state == State.PLAY) {
            // Update the current time and elapsed time
            double newTime = System.currentTimeMillis() / 1000d;
            double elapsedTime = newTime - currentTime;
            currentTime = newTime;

            // Accumulate the elapsed time to simulate
            accumulator += elapsedTime;
            // While the elapsed time have not been simulated, simulate a step of time dt
            while (accumulator >= DT) {
                // Tell the physic engine to do it's job
                physicEngine.integrate(level.getEntitiesWithComponent(PhysicComponent.class));
                // Tell the time engine to update
                timeEngine.update();

                // Call the components fixed updates
                level.getEntities().forEach(e -> e.getComponents().forEach(Component::fixedUpdate));

                // Decrease remaining time to integrate by dt
                accumulator -= DT;
            }

            // Render a frame if enough time have elapsed
            if (currentTime - lastFrames >= SECOND_PER_FRAME) {
                lastFrames = currentTime;
                frames += 1;

                // Notify the io engine that a frame elapsed
                ioEngine.nextFrame();
                // Render the elapsed frame
                renderEngine.render(level.getEntitiesWithComponent(RenderComponent.class));

                // Call the components updates
                level.getEntities().forEach(e -> e.getComponents().forEach(Component::update));
            }

            // If one second elapsed, display FPS
            if (currentTime - startFrames >= 1) {
                LoggingEngine.info("FPS: " + frames, Color.DARKCYAN);
                startFrames = currentTime;
                frames = 0;
            }
        }
    }

    /**
     * Close the app.
     */
    private void close() {
        System.out.println("Exiting " + config.title + " v" + config.version);
        Platform.exit();
    }

    //*******************************//
    //*   change the engine state   *//
    //*******************************//

    /**
     * Update the current state.
     *
     * @param state the new state.
     */
    private void setState(State state) {
        this.state = state;

        synchronized (this) {
            this.notifyAll();
        }
    }

    /**
     * Ask the game to start playing.
     * Needed to be called after init for the game to truly start.
     */
    void play() {
        setState(State.PLAY);
    }

    /**
     * Ask to put the game in pause.
     */
    void pause() {
        if (this.state == State.PLAY) {
            setState(State.PAUSE);
            timeEngine.pause();
            soundEngine.pause();
        } else {
            LoggingEngine.warning("Attempting to pause while the game is not in play" + state);
        }
    }

    /**
     * Ask to unpause mode en go back to playing.
     */
    void unpause() {
        if (this.state == State.PAUSE) {
            setState(State.PLAY);
            timeEngine.unpause();
            soundEngine.unpause();
        } else {
            LoggingEngine.warning("Attempting to unpause while the game is not paused" + state);
        }
    }

    /**
     * Ask to stop the current play and start a new one.
     */
    void stop() {
        setState(State.STOP);
    }

    /**
     * Ask to quit the application.
     */
    void quit() {
        setState(State.QUIT);
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
     * Set the current level.
     *
     * @param level the new level.
     */
    void setLevel(Level level) {
        this.level = level;
    }

    /**
     * @return the current game level.
     */
    public Level getLevel() {
        return this.level;
    }
}
