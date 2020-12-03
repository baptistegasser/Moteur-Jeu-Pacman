package fr.univ.pacman.component.ai;

import fr.univ.engine.core.Component;
import fr.univ.engine.core.TransformComponent;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.logging.LoggingEngine;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.PhysicComponent;
import fr.univ.pacman.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Base class for ghosts ai components.
 */
public abstract class GhostAIComponent extends Component {
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
            new Vector(0, -0.5),    // UP
            new Vector(0, 0.5),     // DOWN
            new Vector(0.5, 0),     // RIGHT
            new Vector(-0.5, 0)     // LEFT
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

    @Override
    public void fixedUpdate() {
        // Ignore if no state.
        if (state == State.NONE) {
            return;
        }

        // If the global state have changed and ghost is not in dead/spawning state, update the state.
        if (state != currentGlobalState && state != State.DEAD && state != State.SPAWN) {
            state = currentGlobalState;
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
        }
    }

    private List<Vector> getValidDirections() {
        final Vector currentDir = getComponent(PhysicComponent.class).direction();
        List<Vector> validDirections = new ArrayList<>();

        for (Vector dir : directions) {
            Point p = getComponent(TransformComponent.class).position().copy();
            p.add(dir);

            if (getPhysics().canMoveTo(getEntity(), p) && !dir.equals(currentDir.reverse())) {
                validDirections.add(dir);
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

    public void spawn() {
        List<Entity> exits = getLevel().getEntities(Type.SPAWN_EXIT);
        this.spawnExit = exits.get(new Random().nextInt(exits.size())).getComponent(TransformComponent.class).position();
        this.state = State.SPAWN;
    }

    public void notifySpawnExit() {
        this.state = currentGlobalState;
    }

    public void setDead() {
        this.state = State.DEAD;
    }

    public static void setCurrentGlobalState(State currentGlobalState) {
        GhostAIComponent.currentGlobalState = currentGlobalState;
    }

    public void teleportToBase() {
        getComponent(TransformComponent.class).setPosition(base);
    }
}
