package fr.univ.engine.render;

import fr.univ.engine.math.Point;

/**
 * A render object is used by the {@link RenderEngine} to render an object.
 */
public class RenderObject {
    /**
     * The position at which to render the object.
     */
    public Point pos;
    /**
     * The name of the texture to display.
     */
    public String textureName;
    /**
     * The width to render (might differ from the texture's original width).
     */
    public double width;
    /**
     * The height to render (might differ from the texture's original height).
     */
    public double height;
    /**
     * The position of the texture, allow to define which object should be rendered on top of another.
     */
    public int zIndex;

    public RenderObject() {
        pos = new Point(0,0);
        width = 1.0;
        height = 1.0;
        zIndex = 0;
        textureName = null;
    }
}
