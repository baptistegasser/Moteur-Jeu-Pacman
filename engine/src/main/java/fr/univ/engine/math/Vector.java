package fr.univ.engine.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

/**
 * Representation of a 2D Vector.
 */
public class Vector {
    private final BigDecimal x;
    private final BigDecimal y;
    /**
     * The magnitude (length) of this vector.
     */
    private final BigDecimal magnitude;

    public Vector(double x, double y) {
        this(BigDecimal.valueOf(x), BigDecimal.valueOf(y));
    }

    public Vector(BigDecimal x, BigDecimal y) {
        this.x = x;
        this.y = y;
        this.magnitude = x.pow(2).add(y.pow(2)).sqrt(MathContext.DECIMAL128);
    }

    /**
     * @return the x value of this vector.
     */
    public BigDecimal x() {
        return x;
    }

    /**
     * @return the x value of this vector cast as a double.
     */
    public double xValue() {
        return x.doubleValue();
    }

    /**
     * @return the y value of this vector.
     */
    public BigDecimal y() {
        return y;
    }

    /**
     * @return the y value of this vector cast as a double.
     */
    public double yValue() {
        return y.doubleValue();
    }

    /**
     * @return the magnitude (length) of this vector.
     */
    public BigDecimal magnitude() {
        return magnitude;
    }

    /**
     * @return the magnitude (length) of this vector cast as a double.
     */
    public double magnitudeValue() {
        return magnitude.doubleValue();
    }

    /**
     * @return the opposite vector to this instance.
     */
    public Vector reverse() {
        return new Vector(x.multiply(BigDecimal.valueOf(-1)), y.multiply(BigDecimal.valueOf(-1)));
    }

    /**
     * @return a unit vector of magnitude 1 going in the same direction as this vector.
     */
    public Vector getUnitVector() {
        return new Vector(x.divide(magnitude, MathContext.DECIMAL128), y.divide(magnitude, MathContext.DECIMAL128));
    }

    /**
     * Return a new vector with values multiplied.
     *
     * @param scale the multiplier.
     * @return a new multiplied vector.
     */
    public Vector multiplyBy(double scale) {
        return multiplyBy(BigDecimal.valueOf(scale));
    }

    /**
     * Return a new vector with values multiplied.
     *
     * @param scale the multiplier.
     * @return a new multiplied vector.
     */
    public Vector multiplyBy(BigDecimal scale) {
        return new Vector(x.multiply(scale), y.multiply(scale));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return x.compareTo(vector.x) == 0 && y.compareTo(vector.y) == 0;
    }

    /**
     * Test if a given vector is in the same direction
     *
     * @param v the vector to confront.
     * @return if they are in same direction
     */
    public boolean sameDirection(Vector v) {
        if (magnitude.doubleValue() == 0 || v.magnitude().doubleValue() == 0) {
            return false;
        }
        return getUnitVector().equals(v.getUnitVector());
    }

    /**
     * Return if two vector have a same orientation, including diagonal.
     * @param v the vector to confront
     * @return if they are in same orientation
     */
    public boolean sameOrientalDirection(Vector v) {
        return ((this.xValue() >= 0 && v.xValue() >= 0) ||
                (this.xValue() <= 0 && v.xValue() <= 0)) &&
                ((this.yValue() >= 0 && v.yValue() >= 0) ||
                (this.yValue() <= 0 && v.yValue() <= 0));
    }

    /**
     * Set the direction vector in function the size of the collision and a possible direction
     * We must have two x or two y value different and different than zero
     * And the calculation for x or y value depend if they are higher or lower than 0
     *
     * @param collisionSize The size of the collision
     * @return a vector whi can paste the edge, else null
     */
    public Vector getVectorForPasteEdge(Vector collisionSize, double speed) {
        if (this.xValue() > 0 && collisionSize.xValue() > 0
                && this.x().compareTo(collisionSize.x()) != 0)
            return new Vector(this.x().subtract(collisionSize.x()).doubleValue(), 0);
        else if (this.xValue() > 0 && collisionSize.xValue() < 0
                && this.x().compareTo(collisionSize.x()) != 0)
            return new Vector(this.x().add(collisionSize.x()).doubleValue(), 0);
        else if (this.xValue() < 0 && collisionSize.xValue() > 0
                && this.x().compareTo(collisionSize.x()) != 0)
            return new Vector(this.x().add(collisionSize.x()).doubleValue(), 0);
        else if (this.xValue() < 0 && collisionSize.xValue() < 0
                && this.x().compareTo(collisionSize.x()) != 0)
            return new Vector(this.x().subtract(collisionSize.x()).doubleValue(), 0);
        else if (this.yValue() > 0 && collisionSize.yValue() > 0
                && this.y().compareTo(collisionSize.y()) != 0)
            return new Vector(0, this.y().subtract(collisionSize.y()).doubleValue());
        else if (this.yValue() > 0 && collisionSize.yValue() < 0
                && this.y().compareTo(collisionSize.y()) != 0)
            return new Vector(0, this.y().add(collisionSize.y()).doubleValue());
        else if (this.yValue() < 0 && collisionSize.yValue() > 0
                && this.y().compareTo(collisionSize.y()) != 0)
            return new Vector(0, this.y().add(collisionSize.y()).doubleValue());
        else if (this.yValue() < 0 && collisionSize.yValue() < 0
                && this.y().compareTo(collisionSize.y()) != 0)
            return new Vector(0, this.y().subtract(collisionSize.y()).doubleValue());

            // If have a collision size (x and y != 0) and (x and y and x and -y different) and (x and y < speed)
            // So we can have a little diagonal displacement
        else if (collisionSize.xValue() != 0 && collisionSize.yValue() != 0 &&
                (collisionSize.xValue() != collisionSize.yValue() && collisionSize.xValue() != -collisionSize.yValue()) &&
                ((collisionSize.xValue() <= speed && -collisionSize.xValue() <= speed) &&
                        (collisionSize.yValue() <= speed && -collisionSize.yValue() <= speed))) {
            if (collisionSize.xValue() == speed || collisionSize.xValue() == -speed) {
                return new Vector(collisionSize.x(), collisionSize.y().multiply(BigDecimal.valueOf(-1)));
            } else if (collisionSize.yValue() == speed || collisionSize.yValue() == -speed) {
                return new Vector(collisionSize.x().multiply(BigDecimal.valueOf(-1)), collisionSize.y());
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return " X : " + x +
                ", Y : " + y;
    }
}
