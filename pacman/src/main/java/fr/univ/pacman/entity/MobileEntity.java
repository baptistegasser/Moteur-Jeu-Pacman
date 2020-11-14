package fr.univ.pacman.entity;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;
import javafx.scene.shape.Circle;

public abstract class MobileEntity extends GameObject {
    /**
     * The direction and velocity of the entity
     */
    Point movement;

    public MobileEntity() {
        movement = new Point(0,0);
        this.physicObject.shape = new Circle(point.x, point.y, renderObject.width/2);
    }

    @Override
    public boolean update() {
        return true;
    }

    @Override
    public void fixedUpdate(double dt) {
        this.point.x += this.movement.x;
        this.point.y += this.movement.y;

        this.physicObject.shape = new Circle(point.x, point.y, renderObject.width/2);
    }
}
