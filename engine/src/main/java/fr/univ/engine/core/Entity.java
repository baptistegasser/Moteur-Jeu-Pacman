package fr.univ.engine.core;

import fr.univ.engine.core.math.Point;
import fr.univ.engine.core.math.Rect;

/**
 * Representation of an entity in the game engine.
 */
public class Entity {
    /**
     * It's position in space.
     */
    private Point pos;
    /**
     * The size of this entity
     */
    private Rect size;
    private int zIndex;

    public Entity() {
        this(new Point(0, 0), new Rect(0, 0));
    }

    public Entity(Point pos, Rect size) {
        this.pos = pos;
        this.size = size;
        this.zIndex = 0;
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public Point pos() {
        return pos;
    }

    public Rect size() {
        return size;
    }
}
