package fr.univ.engine.math;

/**
 * Position and rotation of an object.
 */
public class Transform {
    private final Point position;
    private double rotation;

    public Transform(Point position, double rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        setPosition(position.x, position.y);
    }

    public void setPosition(double x, double y) {
        this.position.x = x;
        this.position.y = y;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}
