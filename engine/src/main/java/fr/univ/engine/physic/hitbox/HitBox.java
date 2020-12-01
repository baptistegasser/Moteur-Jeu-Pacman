package fr.univ.engine.physic.hitbox;

import fr.univ.engine.math.Point;

/**
 * Represent the HitBox of object
 */
public abstract class HitBox {
    /**
     * Specify if an object is solid
     * An object can be solid such as a wall, or not, like a Gomme
     * Pacman collide with solid objects.
     */
    private boolean isSolid;

    public HitBox() {
        this.isSolid = false;
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