package fr.univ.engine.physic.hitbox;

import fr.univ.engine.logging.LoggingEngine;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;

import java.math.BigDecimal;

/**
 * Static utility class to test if hit boxes intersects.
 */
public class HitBoxIntersecter {
    /**
     * Test the intersection between two generic hitbox.
     *
     * @param h1 the first hitbox.
     * @param p1 the position of the first hitbox.
     * @param h2 the second hitbox.
     * @param p2 the position of the second hitbox.
     * @return true if the two hitbox intersect.
     */
    public static boolean intersect(HitBox h1, Point p1, HitBox h2, Point p2) {
        if (h1 instanceof SquareHitBox && h2 instanceof SquareHitBox) {
            return intersect((SquareHitBox) h1, p1, (SquareHitBox) h2, p2);
        } else if (h1 instanceof CircleHitBox && h2 instanceof CircleHitBox) {
            return intersect((CircleHitBox) h1, p1, (CircleHitBox) h2, p2);
        }

        LoggingEngine.severe(String.format("Intersect method not implemented for (%s, %s)", h1, h2));
        return false;
    }

    /**
     * Test the intersection between two circle.
     *
     * @param h1 the first circle.
     * @param p1 the position of the first circle.
     * @param h2 the second circle.
     * @param p2 the position of the second circle.
     * @return true if the two hitbox intersect.
     */
    private static boolean intersect(CircleHitBox h1, Point p1, CircleHitBox h2, Point p2) {
        return p1.distance(p2) < h1.diameter() / 2 + h2.diameter() / 2;
    }

    /**
     * Test the intersection between two squares.
     *
     * @param h1 the first square.
     * @param p1 the position of the first square.
     * @param h2 the second square.
     * @param p2 the position of the second square.
     * @return true if the two hitbox intersect.
     */
    private static boolean intersect(SquareHitBox h1, Point p1, SquareHitBox h2, Point p2) {
        // Size between two center element
        BigDecimal diff = BigDecimal.valueOf((h1.size() + h2.size()) / 2);
        BigDecimal negativeDiff = diff.multiply(BigDecimal.valueOf(-1));

        return  p1.x().subtract(p2.x()).compareTo(diff) < 0 &&
                p1.x().subtract(p2.x()).compareTo(negativeDiff) > 0 &&
                p1.y().subtract(p2.y()).compareTo(diff) < 0 &&
                p1.y().subtract(p2.y()).compareTo(negativeDiff) > 0;
    }

    /**
     * Get the size of collision between two entity
     * @param h1 HitBox of the first entity
     * @param p1 Position of the first entity
     * @param h2 HitBox of the first entity
     * @param p2 Position of the first entity
     * @return A vector who represent the size of collision
     */
    public static Vector collisionSize(HitBox h1, Point p1, HitBox h2, Point p2) {
        BigDecimal diff = null;
        if (h1 instanceof SquareHitBox && h2 instanceof SquareHitBox) {
            diff = BigDecimal.valueOf((((SquareHitBox) h1).size() + ((SquareHitBox) h2).size()) / 2);
        } else if (h1 instanceof CircleHitBox && h2 instanceof CircleHitBox) {
            diff = BigDecimal.valueOf((((CircleHitBox) h1).diameter() + ((CircleHitBox) h2).diameter()) / 2);
        }

        if (diff == null) {
            LoggingEngine.severe(String.format("CollisionSize method not implemented for (%s, %s)", h1, h2));
            return new Vector(0,0);
        }

        BigDecimal negativeDiff = diff.multiply(BigDecimal.valueOf(-1));

        BigDecimal x = (p1.x().subtract(p2.x()));
        BigDecimal y = (p1.y().subtract(p2.y()));

        // If x > 0 add diff but if x < 0 add negativeDiff and if x == 0, add nothing
        if (x.doubleValue() > 0) x = x.add(negativeDiff);
        else if (x.doubleValue() < 0) x = x.add(diff);

        if (y.compareTo(BigDecimal.valueOf(0)) > 0) y = y.add(negativeDiff);
        else if (y.compareTo(BigDecimal.valueOf(0)) < 0) y = y.add(diff);

        return new Vector(x,y);
    }
}
