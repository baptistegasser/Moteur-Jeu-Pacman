package fr.univ.engine.core;

import fr.univ.engine.core.math.Point;
import fr.univ.engine.core.math.Rect;

public class Entity {
    private Point pos;
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
