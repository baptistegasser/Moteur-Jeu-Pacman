package fr.univ.engine.physic.hitbox;

import fr.univ.engine.physic.PhysicException;

/**
 * Interface to implement by hit boxes classes to detect if they intersect
 * with another {@code HitBox} class.
 *
 * When adding a new HitBox implementation the interface should be updated to reflect
 * that other object can intersect with the new type.
 */
interface Intersect {
    /**
     * Default implementation for unknown HitBox implementation.
     * Will attempt to match one of the known method with the class of h2.
     *
     * @param h2 the {@code HitBox} to test
     * @return true if this instance intersect with h2
     * @throws PhysicException if no method signature match the type of h2
     */
    default boolean intersect(HitBox h2) {
        if (h2 instanceof SquareHitBox) {
            return intersect((SquareHitBox) h2);
        } else if (h2 instanceof CircleHitBox) {
            return intersect((CircleHitBox) h2);
        } else {
            String msg = String.format("Collision between %s and %s are not implemented !", getClass(), h2.getClass());
            throw new PhysicException(msg);
        }
    }

    /**
     * Test intersection with a {@code SquareHitBox} instance.
     *
     * @param h2 the {@code SquareHitBox} to test
     * @return true if this instance intersect with h2
     */
    boolean intersect(SquareHitBox h2);

    /**
     * Test intersection with a {@code CircleHitBox} instance.
     *
     * @param h2 the {@code CircleHitBox} to test
     * @return true if this instance intersect with h2
     */
    boolean intersect(CircleHitBox h2);
}
