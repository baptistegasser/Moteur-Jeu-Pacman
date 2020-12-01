package fr.univ.engine.core;

import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.render.RenderComponent;

import java.util.ArrayList;
import java.util.List;

public class Animator {
    /**
     * The entity to animate
     */
    private List<Entity> entities;

    /**
     * time when animation start
     */
    private long start;

    /**
     * The animation duration
     */
    private long time;

    /**
     * The core engine managing this instance.
     */
    Core core;

    public Animator(Core core) {
        this.core = core;
        entities = new ArrayList<>();
    }

    /**
     * Init the entities list and time of animation
     * @param renderComponentEntities the entities list to filter
     */
    public void init(List<Entity> renderComponentEntities) {
        for (Entity entity : renderComponentEntities) {
            if (entity.getComponent(RenderComponent.class).isForAnimator())
                time = entity.getComponent(RenderComponent.class).getTexture().getAnimation().getSpeed() *
                        entity.getComponent(RenderComponent.class).getTexture().getAnimation().getNbFrame();
                //System.out.println(entity.getComponent(TransformComponent.class).rotation());
            entities.add(entity);
        }
    }

    public boolean isFinish(long end) {
        return (start + time) <= end;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
