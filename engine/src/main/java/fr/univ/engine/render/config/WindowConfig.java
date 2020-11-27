package fr.univ.engine.render.config;

/**
 * Implementation independent window configuration class.
 */
public class WindowConfig {
    /**
     * The target width of the window.
     */
    public int width;
    /**
     * The target height of the window.
     */
    public int height;
    /**
     * The title of the window.
     */
    public String title;
    /**
     * Should the window show FPS ?
     */
    public boolean showFPSCounter;
    /**
     * Should the window be resizable ?
     */
    public boolean allowResize;

    public WindowConfig(int width, int height, String title, boolean showFPSCounter, boolean allowResize) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.showFPSCounter = showFPSCounter;
        this.allowResize = allowResize;
    }
}
