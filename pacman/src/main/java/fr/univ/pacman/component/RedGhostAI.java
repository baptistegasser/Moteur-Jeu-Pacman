package fr.univ.pacman.component;

import fr.univ.engine.core.component.Component;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.component.PhysicComponent;

/**
 * The red ghost is always chasing the playing
 */
public class RedGhostAI extends Component {
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

    @Override
    public void update() {
        if (nextDirection == Dir.NONE) return;

        PhysicComponent physic = getComponent(PhysicComponent.class);
        Vector newDirection = new Vector(0, 0);
        double rotation = 0;
    }
}
