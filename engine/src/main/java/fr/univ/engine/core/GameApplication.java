package fr.univ.engine.core;

import fr.univ.engine.core.config.Config;
import fr.univ.engine.core.level.Level;
import fr.univ.engine.io.IOEngine;
import fr.univ.engine.physic.PhysicEngine;
import fr.univ.engine.render.JFXApp;
import fr.univ.engine.render.RenderEngine;
import fr.univ.engine.sound.SoundEngine;
import fr.univ.engine.ui.UiEngine;
import javafx.application.Platform;

/**
 * The base class that a game should implement to use the engine.
 * <p>
 * Note: At any given point, only one instance is allowed to run.
 * <p>
 * CREDITS: the {@link #launch(String[])} mechanism to auto-load the
 * calling class extending {@code GameApplication} are heavily based
 * on the {@link javafx.application.Application#launch(String...)} implementation.
 */
public abstract class GameApplication {
    /**
     * The current instance.
     */
    private static GameApplication instance;
    /**
     * The core engine instance running this game.
     */
    private Core core;
    /**
     * The global variables of this game.
     */
    private VarMap globalVars;

    /**
     * Should the application's main loop quit ?
     */
    private boolean quit = false;

    /**
     * When the game can start
     */
    private boolean startGame = false;

    /**
     * The internal method called when {@link #launch} is called.
     *
     * @param args the arguments passed to the program.
     */
    private void launch$(String... args) {
        globalVars = new VarMap();
        core = new Core(args);
        config(core.config());
        core.init();
        initApplication();
        startApplication();
    }

    /**
     * This function is called when application is lunch
     */
    private void initApplication() {
        JFXApp.getIsClosingProperty().addListener(o -> this.quit());
        drawApplication();
    }

    /**
     * This function permit to draw the content of the application
     */
    protected abstract void drawApplication();


    /**
     * Start the loop of application
     */
    private void startApplication() {
        while (!quit) {
            if (startGame) {
                System.out.println("Start game");
                lunchGame();
                startGame = false;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Platform.exit();
    }

    /**
     * Method called to let the game set the configuration.
     * Note: attempting to access any engine at this step WILL fail.
     *
     * @param config the configuration to edit.
     */
    protected abstract void config(Config config);

    public void startGame() {
        startGame = true;
    }

    /**
     * This function init the game with custom configuration and start core
     */
    private void lunchGame() {
        initGame();
        core.start();
    }
    /**
     * Method called just before starting the game.
     */
    protected abstract void initGame();

    public void quit() {
        quit = true;
    }

    /**
     * Set the game level.
     *
     * @param level the new level.
     */
    protected final void setLevel(Level level) {
        this.core.setLevel(level);
    }

    /**
     * @return the game level.
     */
    protected final Level getLevel() {
        return this.core.getLevel();
    }

    /**
     * @return the game's global vars.
     */
    protected final VarMap globalVars() {
        return this.globalVars;
    }

    /**
     * @return the core engine instance.
     */
    protected final Core core() {
        return this.core;
    }

    /**
     * @return the physic engine of this game.
     */
    protected final PhysicEngine physicEngine() {
        return core.physicEngine();
    }

    /**
     * @return the input/output engine of this game.
     */
    protected final IOEngine IOEngine() {
        return core.IOEngine();
    }

    /**
     * @return the sound engine of this game.
     */
    protected final SoundEngine soundEngine() {
        return core.soundEngine();
    }

    /**
     * @return the ui engine of this game.
     */
    protected final UiEngine uiEngine() {
        return core.uiEngine();
    }

    /**
     * @return the render engine of this game.
     */
    protected final RenderEngine renderEngine() {
        return core.renderEngine();
    }

    /**
     * Method called to launch a new game.
     *
     * @param args arguments to configure the engine/game
     */
    public static void launch(String... args) {
        launch(getCallingClass(), args);
    }

    /**
     * Method called to launch a new game with a specific class.
     *
     * @param args arguments to configure the engine/game
     */
    public static void launch(Class<? extends GameApplication> gameClass, String... args) {
        if (instance != null) {
            throw new CoreException("A GameApplication is already running, aborting launch");
        }

        instance = newInstance(gameClass);
        instance.launch$(args);
    }

    /**
     * @return the current game instance.
     */
    public static GameApplication getInstance() {
        return instance;
    }

    /**
     * Create a new instance of the class that called {@link #launch(String[])}.
     * If the class have no default, empty constructor, this will fail.
     *
     * @return the new instance of a GameApplication
     */
    private static GameApplication newInstance(Class<? extends GameApplication> gameClass) {
        try {
            return gameClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new CoreException("No default constructor found for class " + gameClass.getName(), e);
        }
    }

    /**
     * Retrieve the class that called the {@link #launch(String[])} method.
     * CREDITS: taken and modified from the JavaFX implementation of {@link  javafx.application.Application#launch(String...)}.
     *
     * @return the calling class
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Class<? extends GameApplication> getCallingClass() {
        // Figure out the right class to call
        StackTraceElement[] cause = Thread.currentThread().getStackTrace();

        boolean foundThisMethod = false;
        String callingClassName = null;
        for (StackTraceElement se : cause) {
            // Skip entries until we get to the entry for this class
            String className = se.getClassName();
            String methodName = se.getMethodName();
            if (foundThisMethod) {
                callingClassName = className;
                break;
            } else if (GameApplication.class.getName().equals(className) && "launch".equals(methodName)) {
                foundThisMethod = true;
            }
        }

        if (callingClassName == null) {
            throw new RuntimeException("Error: unable to find GameApplication class");
        }

        try {
            Class theClass = Class.forName(callingClassName, false, Thread.currentThread().getContextClassLoader());
            if (GameApplication.class.isAssignableFrom(theClass)) {
                return theClass;
            } else {
                throw new CoreException("Error: " + theClass + " is not a subclass of engine.core.GameApplication");
            }
        } catch (Exception ex) {
            throw new CoreException("Failed to find the calling class", ex);
        }
    }
}
