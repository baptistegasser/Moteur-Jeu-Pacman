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

    public BigDecimal y() {
        return y;
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
        this.x = x.add(point.x);
        this.y = y.add(point.y);
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
