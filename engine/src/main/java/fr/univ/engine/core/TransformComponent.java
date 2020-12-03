package fr.univ.engine.core;

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
}
