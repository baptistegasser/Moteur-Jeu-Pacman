package fr.univ.engine.math;

/**
 * Representation of a 2D Vector.
 */
public class Vector {
    private double x;
    private double y;
    /**
     * The magnitude (length) of this vector.
     */
    private double magnitude;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
        updateMagnitude();
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
        updateMagnitude();
    }

    public void setY(double y) {
        this.y = y;
        updateMagnitude();
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
        updateMagnitude();
    }

    /**
     * Recalculate and set the magnitude value of this vector.
     */
    private void updateMagnitude() {
        this.magnitude = Math.sqrt(x*x + y*y);
    }

    /**
     * @return the magnitude (length) of this vector.
     */
    public double magnitude() {
        return magnitude;
    }

    /**
     * @return the opposite vector to this instance.
     */
    public Vector reverse() {
        if (this.x() > 0 || this.x() < 0 && this.y() == 0) {
            return new Vector(-x, 0);
        } else if (this.x() == 0 && this.y() > 0 || this.y() < 0) {
            return new Vector(0, -y);
        }
        return new Vector(0,0);
    }

    /**
     * Return a new vector with values multiplied.
     *
     * @param scale the multiplier.
     * @return a new multiplied vector.
     */
    public Vector multiplyBy(double scale) {
        return new Vector(x*scale, y*scale);
    }

    /**
     * Return true if two vector are a same direction
     * @param v1 first vector
     * @param v2 second vector
     * @return if they are in same direction
     */
    public static boolean sameDirection(Vector v1, Vector v2) {
        return (v1.x > 0 && v2.x > 0 && v1.y == 0 && v2.y == 0)
                || (v1.x < 0 && v2.x < 0 && v1.y == 0 && v2.y == 0)
                || (v1.x == 0 && v2.x == 0 && v1.y > 0 && v2.y > 0)
                || (v1.x == 0 && v2.x == 0 && v1.y < 0 && v2.y < 0);
    }
}
