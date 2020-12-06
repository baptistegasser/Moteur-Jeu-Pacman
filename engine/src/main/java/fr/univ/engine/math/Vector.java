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
}
