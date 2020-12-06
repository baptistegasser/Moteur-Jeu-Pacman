package fr.univ.pacman.component;

import fr.univ.engine.core.Component;
import fr.univ.engine.core.TransformComponent;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.logging.LoggingEngine;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.PhysicComponent;
import fr.univ.engine.physic.hitbox.HitBoxIntersecter;
import fr.univ.engine.render.RenderComponent;

import java.util.logging.Level;

/**
 * The component handling Pac-Man movement logic.
 */
public class PacManLogic extends Component {

    private boolean changeDir = false;
    private Vector wantedDirection;
    private double wantedRotation;
    private boolean canMove;

    /**
     * Pacman modes
     */
    public enum Mode {
        NORMAL,
        SUPER,
        RAINBOW,
        DEATH,
        SPAWN
    }

    /**
     * Current mode
     */
    private Mode currentMode = Mode.SPAWN;

    /**
     * Pacman goes up
     */
    public void up() {
        if (canMove) {
            this.wantedDirection = getComponent(PhysicComponent.class).getDirectionSpeed(new Vector(0, -1d));
            this.wantedRotation = 270;
            this.changeDir = true;
        }
    }

    /**
     * Pac man goes down
     */
    public void down() {
        if (canMove) {
            this.wantedDirection = getComponent(PhysicComponent.class).getDirectionSpeed(new Vector(0, 1d));
            this.wantedRotation = 90;
            this.changeDir = true;
        }
    }

    /**
     * Pacman goes left
     */
    public void left() {
        if (canMove) {
            this.wantedDirection = getComponent(PhysicComponent.class).getDirectionSpeed(new Vector(-1d, 0));
            this.wantedRotation = 180;
            this.changeDir = true;
        }
    }

    /**
     * Pacman goes right
     */
    public void right() {
        if (canMove) {
            this.wantedDirection = getComponent(PhysicComponent.class).getDirectionSpeed(new Vector(1d, 0));
            this.wantedRotation = 0;
            this.changeDir = true;
        }
    }

    /**
     * Pacman is stopped
     */
    public void stop() {
        this.wantedDirection = new Vector(0, 0);
        this.changeDir = true;
    }

    /**
     * Pacman hit something
     */
    public void hit() {
        LoggingEngine.log(Level.INFO, "Hit !");
    }

    /**
     * Allow pacman to move, or not
     * @param canMove If pacman can move
     */
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    /**
     * Set the current mode : spawning, super power, normal ...
     * @param currentMode The current mode
     */
    public void setCurrentMode(Mode currentMode) {
        this.currentMode = currentMode;

        if (currentMode == Mode.RAINBOW) {
            getComponent(RenderComponent.class).getTexture().setCurrentChannel("super");
        } else if (currentMode == Mode.SPAWN) {
            getComponent(RenderComponent.class).getTexture().setCurrentChannel("spawn");
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

    public boolean isDeath() {
        return currentMode == Mode.DEATH;
    }

    /**
     * Update the pacman status
     * Allow us to catch colisions, next positions ...
     */
    @Override
    public void fixedUpdate() {
        if (!changeDir) return;

        PhysicComponent physic = getComponent(PhysicComponent.class);
        TransformComponent transform = getComponent(TransformComponent.class);

        // Calculate new pos
        Point newPos = transform.position().copy();
        newPos.add(wantedDirection);

        Entity collisionEntity = getPhysics().canMoveTo(this.getEntity(), newPos);

        // Test if can move to new pos
        if (collisionEntity == null) {
            physic.setDirection(wantedDirection);
            transform.setRotation(wantedRotation);
            changeDir = false;
        } // If have collision, search an optimize displacement
        else {
            Vector collisionSize = HitBoxIntersecter.collisionSize(getComponent(PhysicComponent.class).getHitBox(), newPos,
                    collisionEntity.getComponent(PhysicComponent.class).getHitBox(),
                    collisionEntity.getComponent(TransformComponent.class).position());

            Vector realFinalDir = wantedDirection.getVectorForPasteEdge(collisionSize, this.getComponent(PhysicComponent.class).getSpeed());

            if (realFinalDir != null) {
                Point p2 = getComponent(TransformComponent.class).position().copy();
                p2.add(realFinalDir);
                if (getPhysics().canMoveTo(getEntity(), p2) == null) {
                    physic.setDirection(wantedDirection);
                    transform.setRotation(wantedRotation);
                    changeDir = false;
                }
            }
        }
    }
}
