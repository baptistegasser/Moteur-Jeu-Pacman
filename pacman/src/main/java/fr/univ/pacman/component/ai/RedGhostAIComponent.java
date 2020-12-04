package fr.univ.pacman.component.ai;

import fr.univ.engine.assets.AssetsLoader;
import fr.univ.engine.core.TransformComponent;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.math.Point;
import fr.univ.engine.render.RenderComponent;
import fr.univ.pacman.Type;

public class RedGhostAIComponent extends GhostAIComponent {

    public RedGhostAIComponent() {
        this.base = new Point(-8, -32);
        this.scatterPos = new Point(224, -260);
    }

    @Override
    protected Point calcTargetPos() {
        Entity pacman = getLevel().getSingletonEntity(Type.PACMAN);
        return pacman.getComponent(TransformComponent.class).position();
    }

    @Override
    protected void updateSprite(String dir) {
        getComponent(RenderComponent.class).setTexture(AssetsLoader.loadTexture(20, 20, "sprites/ghosts/redGhost"+dir+".png"));
    }
}
