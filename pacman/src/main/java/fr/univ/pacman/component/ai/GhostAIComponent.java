package fr.univ.pacman.component.ai;

import fr.univ.engine.core.Component;
import fr.univ.engine.core.TransformComponent;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.logging.LoggingEngine;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.PhysicComponent;
import fr.univ.engine.physic.hitbox.HitBoxIntersecter;
import fr.univ.engine.physic.hitbox.SquareHitBox;
import fr.univ.engine.render.RenderComponent;
import fr.univ.pacman.Type;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Base class for ghosts ai components.
 */
public abstract class GhostAIComponent extends Component {

    public static final double NORMALESPEED = 0.55;
    public static final double SCAREDSPEED = 0.53;
    public static final double DEATHSPEED = 1;

    /**
     * Possible state of a ghost.
     */
    public enum State {
        SCATTER,    // The ghost mind is own business.
        CHASE,      // The ghost chase the player.
        SCARED,     // The ghost is scare and move randomly.
        DEAD,       // The ghost is dead and go to the base.
        SPAWN,      // The ghost spawned and move out of the base.
        NONE        // The ghost is just here doing nothing.
    }

    /**
     * Possible directions.
     */
    private final Vector[] directions = new Vector[] {
            new Vector(0, -1d),    // UP
            new Vector(0, 1d),     // DOWN
            new Vector(1d, 0),     // RIGHT
            new Vector(-1d, 0)     // LEFT
    };

    /**
     * The current ghost state, default to doing nothing.
     */
    protected State state = State.NONE;
    /**
     * The current state of all ghosts.
     */
    private static State currentGlobalState = State.CHASE;

    /**
     * If ghost must keep this current state
     */
    private boolean takeCurrentGlobalState = true;
    /**
     * The ghost base position.
     */
    protected Point base;
    /**
     * The position where the ghost will exit from spawn.
     * Might change from one spawning to another.
     */
    protected Point spawnExit;
    /**
     * The position where the ghost will scatter.
     */
    protected Point scatterPos;

    /**
     * Update the ghost
     * Allow us to catch when the ghost is scared, dead ...
     * and adapt its behavior
     */
    @Override
    public void fixedUpdate() {
        // Ignore if no state.
        if (state == State.NONE) {
            return;
        }

        // If the global state have changed and ghost is not in dead/spawning state, update the state.
        if (state != currentGlobalState && state != State.DEAD && state != State.SPAWN && takeCurrentGlobalState) {
            state = currentGlobalState;
            if (currentGlobalState == State.SCARED) {
                this.getComponent(PhysicComponent.class).setDirection(this.getComponent(PhysicComponent.class).direction().reverse());
            }
            LoggingEngine.info(this + " setting current global mode");
        }

        switch (state) {
            case DEAD:
                moveToTarget(base);
                break;
            case SPAWN:
                moveToTarget(spawnExit);
                break;
            case SCATTER:
                moveToTarget(scatterPos);
                break;
            case CHASE:
                moveToTarget(calcTargetPos());
                break;
            case SCARED:
                moveRandomly();
                break;
        }
    }

    /**
     * Move to a random direction if there is an intersection.
     */
    private void moveRandomly() {
        List<Vector> validDirections = getValidDirections();
        if (validDirections.isEmpty()) {
            return;
        }

        Collections.shuffle(validDirections);
        getComponent(PhysicComponent.class).setDirection(validDirections.get(0));
        updateSpriteByVector(validDirections.get(0));
    }

    /**
     * Move to the direction that reduce the distance to the target
     * @param target
     */
    private void moveToTarget(Point target) {
        List<Vector> validDirections = getValidDirections();
        if (validDirections.isEmpty()) {
            return;
        }

        Vector bestDir = null;
        double bestDistance = Double.MAX_VALUE;
        for (Vector dir : validDirections) {
            Point p = getComponent(TransformComponent.class).position().copy();
            p.add(dir);


            double distance = target.distance(p);
            if (distance < bestDistance) {
                bestDir = dir;
                bestDistance = distance;
            }
        }

        if (bestDir != null) {
            getComponent(PhysicComponent.class).setDirection(bestDir);
            updateSpriteByVector(bestDir);
        }
    }

    /**
     * Get the directions who entity can go
     * @return the list of direction who can go
     */
    private List<Vector> getValidDirections() {
        final Vector currentDir = getComponent(PhysicComponent.class).direction();
        List<Vector> validDirections = new ArrayList<>();

        for (Vector dir : directions) {
            Vector realDir = getComponent(PhysicComponent.class).getDirectionSpeed(dir);
            Point p = getComponent(TransformComponent.class).position().copy();
            p.add(realDir);

            //Recup the entity in collision if there is one
            Entity collisionEntity = getPhysics().canMoveTo(getEntity(), p);

            if (!realDir.sameDirection(currentDir.reverse())) {
                // They are no collision between them
                if (collisionEntity == null) {
                    if (currentDir.sameOrientalDirection(realDir)) {
                        validDirections.add(realDir);
                    }
                } else {
                    // Else we can move for glue the edge

                    //Recup the size of collision
                    Vector collisionSize = HitBoxIntersecter.collisionSize(getComponent(PhysicComponent.class).getHitBox(), p,
                            collisionEntity.getComponent(PhysicComponent.class).getHitBox(),
                            collisionEntity.getComponent(TransformComponent.class).position());

                    Vector realFinalDir = HitBoxIntersecter.getVectorForPasteEdge(realDir, collisionSize, this.getComponent(PhysicComponent.class).getSpeed());

                    // Second verification of I can go here
                    if (realFinalDir != null) {
                        Point p2 = getComponent(TransformComponent.class).position().copy();
                        p2.add(realFinalDir);
                        if (getPhysics().canMoveTo(getEntity(), p2) == null && currentDir.sameOrientalDirection(realFinalDir)) {
                            validDirections.add(realFinalDir);
                        }
                    }
                }
            }
        }

        return validDirections;
    }

    /**
     * Calculate the tar depending on the ghost behavior.
     *
     * @return the target pos.
     */
    protected abstract Point calcTargetPos();

    /**
     * Update the sprite in function the direction and the state of ghost
     * @param dir the current direction of ghost
     */
    private void updateSpriteByVector(Vector dir) {
        String textureChannel = "up";

        if (this.state == State.SCARED) {
            textureChannel = "afraid";
        } else {
            if (dir.xValue() > 0 && dir.yValue() == 0) {
                textureChannel = "right";
            } else if (dir.xValue() < 0 && dir.yValue() == 0) {
                textureChannel = "left";
            } else if (dir.xValue() == 0 && dir.yValue() > 0) {
                textureChannel = "down";
            } else if (dir.xValue() == 0 && dir.yValue() < 0) {
                textureChannel = "up";
            } else {
                return;
            }
            if (this.state == State.DEAD) {
                textureChannel = "dead_" + textureChannel;
            }
        }

        getComponent(RenderComponent.class).getTexture().setCurrentChannel(textureChannel);
    }

    public void spawn() {
        List<Entity> exits = getLevel().getEntities(Type.SPAWN_EXIT);
        this.spawnExit = exits.get(new Random().nextInt(exits.size())).getComponent(TransformComponent.class).position();
        this.state = State.SPAWN;
    }

    public void notifySpawnExit() {
        this.state = State.CHASE;
        takeCurrentGlobalState = false;
    }

    public void setDead() {
        this.state = State.DEAD;
    }

    public boolean isDead() {
        return state == State.DEAD;
    }

    public static void setCurrentGlobalState(State currentGlobalState) {
        GhostAIComponent.currentGlobalState = currentGlobalState;
    }

    public static State getCurrentGlobalState() {
        return currentGlobalState;
    }

    public void teleportToBase() {
        getComponent(TransformComponent.class).setPosition(base);
    }

    public void setTakeCurrentGlobalState(boolean takeCurrentGlobalState) {
        this.takeCurrentGlobalState = takeCurrentGlobalState;
    }

    public boolean isScared() {
        return this.state == State.SCARED;
    }
}
