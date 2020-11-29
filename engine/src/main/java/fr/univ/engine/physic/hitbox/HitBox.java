package fr.univ.engine.physic.hitbox;

import fr.univ.engine.math.Point;

/**
 * Represent the HitBox of object
 */
public abstract class HitBox implements Intersect {
    /**
     * The position of the HitBox, set to be the center.
     */
    private Point position;

    /**
     * Specify if an object is solid
     * An object can be solid such as a wall, or not, like a Gomme
     * Pacman collide with solid objects.
     */
    private boolean isSolid;

    public HitBox() {
        this.position = new Point(0, 0);
        this.isSolid = false;
    }

    /**
     * Get the size of this hitbox, implementation to discretion of sub class.
     *
     * @return the wight of the hitbox.
     */
    public abstract double getSize();

    /**
     * @return The X position of the hitbox
     */
    public double x() {
        return position.x;
    }
    /**
     * @return The Y position of the hitbox
     */
    public double y() {
        return position.y;
    }

    /**
     * @param position Set the position of the hitbox
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * @return The position of the hitbox
     */
    public Point getPosition() {
        return position;
    }

    /**
     * @return If the position is solid
     */
    public boolean isSolid() {
        return isSolid;
    }

    public void setSolid(boolean solid) {
        isSolid = solid;
    }
}