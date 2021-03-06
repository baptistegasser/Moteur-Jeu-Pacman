package fr.univ.pacman.component.ai;

import fr.univ.engine.core.TransformComponent;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.PhysicComponent;
import fr.univ.pacman.Type;

/**
 * Blue ghost class
 */
public class BlueGhostAIComponent extends GhostAIComponent {
    public BlueGhostAIComponent() {
        this.base = new Point(-16, -32);
        this.scatterPos = new Point(224, 260);
    }

    /**
     * Calculate the target position
     * @return the target
     */
    @Override
    protected Point calcTargetPos() {
        Entity pacman = getLevel().getSingletonEntity(Type.PACMAN);
        Point target = pacman.getComponent(TransformComponent.class).position().copy();
        target.add(pacman.getComponent(PhysicComponent.class).direction().multiplyBy(2));

        Entity redGhost = getLevel().getEntitiesWithComponent(RedGhostAIComponent.class).get(0);
        Point redGhostPos = redGhost.getComponent(TransformComponent.class).position();

        Vector vector = new Vector(target.x().subtract(redGhostPos.x()), target.y().subtract(redGhostPos.y()));
        target.add(vector);
        return target;
    }
}
