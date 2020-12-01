package fr.univ.engine.physic.hitbox;

import fr.univ.engine.logging.LoggingEngine;
import fr.univ.engine.math.Point;

public class HitBoxIntersecter {
    public static boolean intersect(HitBox h1, Point p1, HitBox h2, Point p2) {
        if (h1 instanceof RectangleHitBox && h2 instanceof RectangleHitBox) {
            return intersect((RectangleHitBox) h1, p1, (RectangleHitBox) h2, p2);
        } else if (h1 instanceof CircleHitBox && h2 instanceof CircleHitBox) {
            return intersect((CircleHitBox) h1, p1, (CircleHitBox) h2, p2);
        }

        LoggingEngine.severe(String.format("Intersect method not implemented for (%s, %s)", h1, h2));
        return false;
    }

    private static boolean intersect(CircleHitBox h1, Point p1, CircleHitBox h2, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance < h1.diameter() / 2 + h2.diameter() / 2;
    }

    private static boolean intersect(RectangleHitBox h1, Point p1, RectangleHitBox h2, Point p2) {
        // Size between two center element
        double diffW = (h1.width() + h2.width()) / 2;
        double diffH = (h1.height() + h2.height()) / 2;

        return  p1.x - p2.x < diffW &&
                p1.x - p2.x > -diffW &&
                p1.y - p2.y < diffH &&
                p1.y - p2.y > -diffH;
    }
}
