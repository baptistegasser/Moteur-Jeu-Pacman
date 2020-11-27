package fr.univ.engine.math;

/**
 * Representation of a point in 2D space.
 */
public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
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
        this.x += vector.x();
        this.y += vector.y();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 &&
                Double.compare(point.y, y) == 0;
    }
}
