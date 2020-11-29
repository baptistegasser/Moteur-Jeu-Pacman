package fr.univ.pacman.migration.component;

import fr.univ.engine.core.component.Component;
import fr.univ.engine.logging.LoggingEngine;
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
        LoggingEngine.log(Level.INFO, "Damned walls !");
    }

    public void hit() {
        LoggingEngine.log(Level.INFO, "Hit !");
    }

    @Override
    public void update() {
        if (nextDirection == Dir.NONE) return;

        PhysicComponent physic = getComponent(PhysicComponent.class);

        switch (nextDirection) {
            case UP:
                physic.direction().set(0, -0.5d);
                break;
            case DOWN:
                physic.direction().set(0, 0.5d);
                break;
            case LEFT:
                physic.direction().set(-0.5d, 0);
                break;
            case RIGHT:
                physic.direction().set(0.5d, 0);
                break;
        }

        nextDirection = Dir.NONE;
    }
}
