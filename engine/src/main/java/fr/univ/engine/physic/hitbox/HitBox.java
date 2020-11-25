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
     * Return the width of the hitbox
     */
    protected double size;

    /**
     * Specifiy if an object is solid
     * An object can be solid such as a wall, or not, like a Gomme
     * Pacman colide with solid objects.
     */
    private boolean isSolid = false;

    public HitBox() {
        this.position = new Point(0, 0);
        this.size = 0;
    }

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
     * @return the wight of the hitbox
     */
    public double getSize() {
        return size;
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