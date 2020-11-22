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
     * Add a vector to this point.
     *
     * @param vector the vector to add
     */
    public void add(Vector vector) {
        this.x = vector.x();
        this.y = vector.y();
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
