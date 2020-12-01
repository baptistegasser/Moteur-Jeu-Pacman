package fr.univ.engine.core;

import fr.univ.engine.core.config.Config;
import fr.univ.engine.core.level.Level;
import fr.univ.engine.io.IOEngine;
import fr.univ.engine.physic.PhysicEngine;
import fr.univ.engine.render.RenderEngine;
import fr.univ.engine.sound.SoundEngine;
import fr.univ.engine.ui.UiEngine;

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
    private static GameApplication app;
    /**
     * The core engine instance running this game.
     */
    private Core core;
    /**
     * The global variables of this game.
     */
    private VarMap globalVars;

    /**
     * Method called to let the game set the configuration.
     * Note: attempting to access any engine at this step WILL fail.
     *
     * @param config the configuration to edit.
     */
    protected abstract void config(Config config);

    /**
     * Method called just before starting the game.
     */
    protected abstract void initGame();

    //*******************************//
    //*     attributes getters      *//
    //*******************************//

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
    public final Level getLevel() {
        return this.core.getLevel();
    }

    /**
     * @return the game's global vars.
     */
    public final VarMap globalVars() {
        return this.globalVars;
    }

    //*******************************//
    //*    app lifecycle methods    *//
    //*******************************//

    /**
     * Start the game.
     */
    public void play() {
        core.play();
    }

    /**
     * Pause the game.
     */
    public void pause() {
        core.pause();
    }

    /**
     * Unpause the game.
     */
    public void unpause() {
        core.unpause();
    }

    /**
     * Stop the current game (restart).
     */
    public void stop() {
        core.stop();
    }

    /**
     * Quit the game.
     */
    public void quit() {
        core.quit();
    }

    //*******************************//
    //*      engines getters        *//
    //*******************************//

    /**
     * @return the core engine instance.
     */
    protected final Core core() {
        return this.core;
    }

    /**
     * @return the physic engine of this game.
     */
    public final PhysicEngine physicEngine() {
        return core.physicEngine();
    }

    /**
     * @return the sound engine of this game.
     */
    public final SoundEngine soundEngine() {
        return core.soundEngine();
    }

    /**
     * @return the input/output engine of this game.
     */
    protected final IOEngine IOEngine() {
        return core.IOEngine();
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

    //*******************************//
    //*     app launch methods      *//
    //*******************************//

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
        if (app != null) {
            throw new CoreException("A GameApplication is already running, aborting launch");
        }

        app = newInstance(gameClass);
        app.launch$(args);
    }

    /**
     * The internal method called when {@link #launch} is called.
     *
     * @param args the arguments passed to the program.
     */
    private void launch$(String... args) {
        globalVars = new VarMap();
        core = new Core(args);
        core.start(this);
    }

    /**
     * @return the current game instance.
     */
    public static GameApplication app() {
        return app;
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
