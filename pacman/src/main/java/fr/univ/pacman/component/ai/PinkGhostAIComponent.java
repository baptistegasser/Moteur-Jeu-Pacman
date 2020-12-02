package fr.univ.pacman.component.ai;

import fr.univ.engine.core.component.TransformComponent;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.PhysicComponent;
import fr.univ.pacman.Type;

public class PinkGhostAIComponent extends GhostAIComponent {
    public PinkGhostAIComponent() {
        this.base = new Point(8, -16);
        this.scatterPos = new Point(-224, -260);
    }

    @Override
    protected Point calcTargetPos() {
        Entity pacman = getLevel().getSingletonEntity(Type.PACMAN);
        Point target = pacman.getComponent(TransformComponent.class).position().copy();
        target.add(pacman.getComponent(PhysicComponent.class).direction().multiplyBy(4));
        return target;
    }
}
