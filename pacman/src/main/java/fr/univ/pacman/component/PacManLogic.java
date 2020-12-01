package fr.univ.pacman.component;

import fr.univ.engine.core.component.Component;
import fr.univ.engine.core.component.TransformComponent;
import fr.univ.engine.logging.LoggingEngine;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.component.PhysicComponent;

import java.util.logging.Level;

public class PacManLogic extends Component {
    private static enum Dir {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        NONE
    }

    private Dir nextDirection = Dir.NONE;

    public void up() {
        this.nextDirection = Dir.UP;
    }

    public void down() {
        this.nextDirection = Dir.DOWN;
    }

    public void left() {
        this.nextDirection = Dir.LEFT;
    }

    public void right() {
        this.nextDirection = Dir.RIGHT;
    }

    public void stop() {
    }

    public void hit() {
        LoggingEngine.log(Level.INFO, "Hit !");
    }

    @Override
    public void update() {
        if (nextDirection == Dir.NONE) return;

        PhysicComponent physic = getComponent(PhysicComponent.class);
        Vector newDirection = new Vector(0, 0);
        double rotation = 0;

        switch (nextDirection) {
            case UP:
                newDirection.set(0, -0.5d);
                rotation = 270;
                break;
            case DOWN:
                newDirection.set(0, 0.5d);
                rotation = 90;
                break;
            case LEFT:
                newDirection.set(-0.5d, 0);
                rotation = 180;
                break;
            case RIGHT:
                newDirection.set(0.5d, 0);
                rotation = 0;
                break;
        }

        TransformComponent transform = getComponent(TransformComponent.class);
        Point newPos = transform.position().copy();
        newPos.add(newDirection);

        if (Physics().canMoveTo(this.getEntity(), newPos)) {
            physic.setDirection(newDirection);
            transform.setRotation(rotation);
            nextDirection = Dir.NONE;
        }
    }
}
