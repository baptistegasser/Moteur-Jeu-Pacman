package fr.univ.engine.core;

/**
 * The class charged of storing the config of a game.
 */
public final class Config {
    /**
     * The title of fix game, also the window title.
     */
    public String title = "Game Engine";
    /**
     * The version of this game.
     */
    public String version = "2.0";
    /**
     * The width of this window.
     */
    public double width = 1280;
    /**
     * The height of this window.
     */
    public double height = 720;
    /**
     * Should the fps be displayed in some way.
     */
    public boolean displayFPS = false;
    /**
     * Should the window be resizable.
     */
    public boolean resizable = true;
}
