package fr.univ.pacman.entity;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;

public abstract class MobileEntity extends GameObject {
    /**
     * The direction and velocity of the entity
     */
    Point movement;

    public MobileEntity() {
        movement = new Point(0,0);
    }

    @Override
    public void update() {
        this.point.x += this.movement.x;
        this.point.y += this.movement.y;
    }
}
