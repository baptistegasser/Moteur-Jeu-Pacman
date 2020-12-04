package fr.univ.pacman.component.ai;

import fr.univ.engine.assets.AssetsLoader;
import fr.univ.engine.core.TransformComponent;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.PhysicComponent;
import fr.univ.engine.render.RenderComponent;
import fr.univ.pacman.Type;

public class BlueGhostAIComponent extends GhostAIComponent {
    public BlueGhostAIComponent() {
        this.base = new Point(-24, -16);
        this.scatterPos = new Point(224, 260);
    }

    @Override
    protected Point calcTargetPos() {
        Entity pacman = getLevel().getSingletonEntity(Type.PACMAN);
        Point target = pacman.getComponent(TransformComponent.class).position().copy();
        target.add(pacman.getComponent(PhysicComponent.class).direction().multiplyBy(2));

        Entity redGhost = getLevel().getEntitiesWithComponent(RedGhostAIComponent.class).get(0);
        Point redGhostPos = redGhost.getComponent(TransformComponent.class).position();

        Vector vector = new Vector(target.x - redGhostPos.x, target.y - redGhostPos.y);
        target.add(vector);
        return target;
    }

    @Override
    protected void updateSprite(String dir) {
        getComponent(RenderComponent.class).setTexture(AssetsLoader.loadTexture(20, 20, "sprites/ghosts/blueGhost"+dir+".png"));
    }
}
