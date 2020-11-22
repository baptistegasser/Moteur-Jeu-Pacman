package fr.univ.engine.physic.hitbox;

import fr.univ.engine.math.Point;
import javafx.scene.shape.Shape;

/**
 * Represent the HitBox of object
 */
public abstract class HitBox implements Intersect {
    /**
     * The position of the HitBox, set to be the center.
     */
    private Point position;

    private boolean isSolid = false;

    public HitBox() {
        this.position = new Point(0, 0);
    }

    public double x() {
        return position.x;
    }

    public double y() {
        return position.y;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public void setSolid(boolean solid) {
        isSolid = solid;
    }
}
