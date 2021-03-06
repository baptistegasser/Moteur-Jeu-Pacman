package fr.univ.pacman.component.ai;

import fr.univ.engine.core.TransformComponent;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.PhysicComponent;
import fr.univ.pacman.Type;

/**
 * Pink ghost class
 */
public class PinkGhostAIComponent extends GhostAIComponent {

    /**
     * Initialise pink ghost
     */
    public PinkGhostAIComponent() {
        this.base = new Point(8, -32);
        this.scatterPos = new Point(-224, -260);
    }


    /**
     * Calculate target position
     * @return the target position
     */
    @Override
    protected Point calcTargetPos() {
        Entity pacman = getLevel().getSingletonEntity(Type.PACMAN);
        Point target = pacman.getComponent(TransformComponent.class).position().copy();
        target.add(pacman.getComponent(PhysicComponent.class).direction().multiplyBy(4));
        return target;
    }
}
