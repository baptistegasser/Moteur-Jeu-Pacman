package fr.univ.engine.physic.hitbox;

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

    /**
     * Set if the object is solid.
     *
     * @param solid the new solidity.
     */
    public void setSolid(boolean solid) {
        isSolid = solid;
    }
}