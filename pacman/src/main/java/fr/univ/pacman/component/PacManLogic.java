package fr.univ.pacman.component;

import fr.univ.engine.core.component.Component;
import fr.univ.engine.core.component.TransformComponent;
import fr.univ.engine.logging.LoggingEngine;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.component.PhysicComponent;

import java.util.logging.Level;

/**
 * The component handling Pac-Man movement logic.
 */
public class PacManLogic extends Component {
    private boolean changeDir = false;
    private Vector wantedDirection;
    private double wantedRotation;

    public void up() {
        this.wantedDirection = new Vector(0, -0.5d);
        this.wantedRotation = 270;
        this.changeDir = true;
    }

    public void down() {
        this.wantedDirection = new Vector(0, 0.5d);
        this.wantedRotation = 90;
        this.changeDir = true;
    }

    public void left() {
        this.wantedDirection = new Vector(-0.5d, 0);
        this.wantedRotation = 180;
        this.changeDir = true;
    }

    public void right() {
        this.wantedDirection = new Vector(0.5d, 0);
        this.wantedRotation = 0;
        this.changeDir = true;
    }

    public void stop() {
        this.wantedDirection = new Vector(0, 0);
        this.changeDir = true;
    }

    public void hit() {
        LoggingEngine.log(Level.INFO, "Hit !");
    }

    @Override
    public void fixedUpdate() {
        if (!changeDir) return;

        PhysicComponent physic = getComponent(PhysicComponent.class);
        TransformComponent transform = getComponent(TransformComponent.class);

        // Calculate new pos
        Point newPos = transform.position().copy();
        newPos.add(wantedDirection);
        // Test if can move to new pos
        if (getPhysics().canMoveTo(this.getEntity(), newPos)) {
            physic.setDirection(wantedDirection);
            transform.setRotation(wantedRotation);
            changeDir = false;
        }
    }
}
