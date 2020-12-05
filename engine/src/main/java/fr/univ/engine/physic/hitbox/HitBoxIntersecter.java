package fr.univ.engine.physic.hitbox;

import fr.univ.engine.logging.LoggingEngine;
import fr.univ.engine.math.Point;

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
}
