package fr.univ.engine.render;

import fr.univ.engine.math.Point;

/**
 * A render object is used by the {@link RenderEngine} to render an object.
 */
public class RenderObject {
    /**
     * The position at which to render the object.
     */
    private Point pos;
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

    private String name;

    public RenderObject(Point point) {
        pos = point;
        width = 1.0;
        height = 1.0;
        zIndex = 0;
        textureName = null;
    }

    public double x() {
        return pos.x;
    }

    public double y() {
        return pos.y;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
