package fr.univ.engine.math;

/**
 * Math functions not provided by java.
 */
public class Math {
    /**
     * Force a value to be inside a specific bound.
     *
     * @param min the min accepted value.
     * @param max the max accepted value.
     * @param value the actual value.
     * @return the clamped value.
     */
    public static double clamp(double min, double max, double value) {
        return java.lang.Math.max(min, java.lang.Math.min(value, max));
    }
}
