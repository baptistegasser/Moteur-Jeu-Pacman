package fr.univ.engine.core.component;

import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;

/**
 * Represent a transformation applied to an object.
 */
public final class TransformComponent extends Component {
    /**
     * The position of the object.
     */
    private Point position;
    /**
     * The rotation of this object.
     */
    private double rotation;
    /**
     * The scale of this object relative to it's original scale.
     */
    private Vector scale;

    public TransformComponent(Point position, double rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Point position() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public double rotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    /**
     * Calculate the distance between this position and the
     * position of another TransformComponent.
     *
     * @param c the other component.
     * @return the distance.
     */
    public double distance(TransformComponent c) {
        double x = position.x - c.position.x;
        double y = position.y - c.position.y;

        return Math.sqrt(x*x + y*y);
    }
}
