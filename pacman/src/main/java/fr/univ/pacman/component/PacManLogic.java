package fr.univ.pacman.component;

import fr.univ.engine.assets.AssetsLoader;
import fr.univ.engine.core.Component;
import fr.univ.engine.core.TransformComponent;
import fr.univ.engine.logging.LoggingEngine;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.PhysicComponent;
import fr.univ.engine.render.RenderComponent;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * The component handling Pac-Man movement logic.
 */
public class PacManLogic extends Component {
    private boolean changeDir = false;
    private Vector wantedDirection;
    private double wantedRotation;
    private boolean canMove;

    public enum Mode {
        NORMAL,
        SUPER,
        RAINBOW
    }

    private Mode currentMode = Mode.NORMAL;

    public void up() {
        if (canMove) {
            this.wantedDirection = new Vector(0, -1d);
            this.wantedRotation = 270;
            this.changeDir = true;
        }
    }

    public void down() {
        if (canMove) {
            this.wantedDirection = new Vector(0, 1d);
            this.wantedRotation = 90;
            this.changeDir = true;
        }
    }

    public void left() {
        if (canMove) {
            this.wantedDirection = new Vector(-1d, 0);
            this.wantedRotation = 180;
            this.changeDir = true;
        }
    }

    public void right() {
        if (canMove) {
            this.wantedDirection = new Vector(1d, 0);
            this.wantedRotation = 0;
            this.changeDir = true;
        }
    }

    public void stop() {
        this.wantedDirection = new Vector(0, 0);
        this.changeDir = true;
    }

    public void hit() {
        LoggingEngine.log(Level.INFO, "Hit !");
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public void setCurrentMode(Mode currentMode) {
        this.currentMode = currentMode;

        if (currentMode == Mode.RAINBOW) {
            getComponent(RenderComponent.class).getTexture().setCurrentChannel("super");
        } else {
            getComponent(RenderComponent.class).getTexture().setCurrentChannel("walking");
        }
    }

    public boolean isInNormalMode() {
        return currentMode == Mode.NORMAL;
    }

    public boolean isInSuperMode() {
        return currentMode == Mode.SUPER;
    }

    public boolean isInRainbowMode() {
        return currentMode == Mode.RAINBOW;
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
            physic.setDirection(physic.getDirectionSpeed(wantedDirection));
            transform.setRotation(wantedRotation);
            changeDir = false;
        }
    }
}
