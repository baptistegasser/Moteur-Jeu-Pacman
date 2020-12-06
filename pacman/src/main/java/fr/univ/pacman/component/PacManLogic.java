package fr.univ.pacman.component;

import fr.univ.engine.assets.AssetsLoader;
import fr.univ.engine.core.Component;
import fr.univ.engine.core.TransformComponent;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.logging.LoggingEngine;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.PhysicComponent;
import fr.univ.engine.physic.hitbox.HitBoxIntersecter;
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

    public static final double PACMANSPEED = 0.6;

    public enum Mode {
        NORMAL,
        SUPER,
        RAINBOW,
        DEATH,
        SPAWN
    }

    private Mode currentMode = Mode.SPAWN;

    public void up() {
        if (canMove) {
            this.wantedDirection = getComponent(PhysicComponent.class).getDirectionSpeed(new Vector(0, -1d));
            this.wantedRotation = 270;
            this.changeDir = true;
        }
    }

    public void down() {
        if (canMove) {
            this.wantedDirection = getComponent(PhysicComponent.class).getDirectionSpeed(new Vector(0, 1d));
            this.wantedRotation = 90;
            this.changeDir = true;
        }
    }

    public void left() {
        if (canMove) {
            this.wantedDirection = getComponent(PhysicComponent.class).getDirectionSpeed(new Vector(-1d, 0));
            this.wantedRotation = 180;
            this.changeDir = true;
        }
    }

    public void right() {
        if (canMove) {
            this.wantedDirection = getComponent(PhysicComponent.class).getDirectionSpeed(new Vector(1d, 0));
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

            Vector realFinalDir = HitBoxIntersecter.getVectorForPasteEdge(wantedDirection, collisionSize, this.getComponent(PhysicComponent.class).getSpeed());

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
