package fr.univ.engine.math;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Representation of a point in 2D space.
 */
public class Point {
    private BigDecimal x;
    private BigDecimal y;

    public Point(double x, double y) {
        this(BigDecimal.valueOf(x), BigDecimal.valueOf(y));
    }

    public Point(BigDecimal x, BigDecimal y) {
        this.x = x;
        this.y = y;
    }

    public BigDecimal x() {
        return x;
    }

    public double xValue() {
        return x.doubleValue();
    }

    public BigDecimal y() {
        return y;
    }

    public double yValue() {
        return y.doubleValue();
    }

    /**
     * @return a deep copy of this point.
     */
    public Point copy() {
        return new Point(x, y);
    }

    /**
     * Add a vector to this point.
     *
     * @param vector the vector to add
     */
    public void add(Vector vector) {
        this.x = x.add(vector.x());
        this.y = y.add(vector.y());
    }

    /**
     * Set new position to this point
     *
     * @param point the new point
     */
    public void set(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public void setX(double x) {
        setX(BigDecimal.valueOf(x));
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public void incrementX() {
        incrementX(1.0);
    }

    public void incrementX(double val) {
        incrementX(BigDecimal.valueOf(val));
    }

    public void incrementX(BigDecimal val) {
        setX(x.add(val));
    }

    public void setY(double y) {
        setY(BigDecimal.valueOf(y));
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public void incrementY() {
        incrementY(1.0);
    }

    public void incrementY(double val) {
        incrementY(BigDecimal.valueOf(val));
    }

    public void incrementY(BigDecimal val) {
        setY(y.add(val));
    }

    /**
     * Calculate the distance between this point and another.
     *
     * @param p the other point.
     * @return the distance.
     */
    public double distance(Point p) {
        // √((x2-x1)² + (y2-y1)²)
        BigDecimal a = p.x.subtract(x).pow(2);
        BigDecimal b = p.y.subtract(y).pow(2);
        BigDecimal distance = a.add(b).sqrt(MathContext.DECIMAL128);
        return distance.doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x.compareTo(point.x) == 0 &&
                y.compareTo(point.y) == 0;
    }
}
