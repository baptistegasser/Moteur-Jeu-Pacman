package fr.univ.engine.core.entity;

import fr.univ.engine.core.Component;
import fr.univ.engine.core.TransformComponent;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.PhysicComponent;
import fr.univ.engine.physic.hitbox.HitBox;
import fr.univ.engine.render.RenderComponent;
import fr.univ.engine.render.texture.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class charged of building entities.
 */
public class EntityBuilder {
    /**
     * The desired type of the entity.
     */
    private Object type = null;
    /**
     * The position of the entity in the level.
     */
    private Point position = new Point(0, 0);
    /**
     * The rotation of the entity.
     */
    private double rotation = 0;
    /**
     * The hitbox used for collisions with the entity.
     */
    private HitBox hitBox = null;
    /**
     * The entity have a solid hitbox, true by default.
     */
    private boolean isSolid = true;
    /**
     * The direction in which the entity will go at spawn.
     */
    private Vector direction = new Vector(0, 0);
    /**
     * The texture used to render the entity.
     */
    private Texture texture = null;
    /**
     * The components defining the behavior of the entity.
     */
    private final List<Component> components;

    public EntityBuilder() {
        // Reserve place for render and physic components.
        components = new ArrayList<>(Arrays.asList(null, null, null));
    }

    public EntityBuilder type(Object type) {
        this.type = type;
        return this;
    }

    public EntityBuilder position(Point position) {
        this.position = position;
        return this;
    }

    public EntityBuilder rotation(double rotation) {
        this.rotation = rotation;
        return this;
    }

    public EntityBuilder hitbox(HitBox hitBox) {
        this.hitBox = hitBox;
        return this;
    }

    public EntityBuilder isSolid(boolean isSolid) {
        this.isSolid = isSolid;
        return this;
    }

    public EntityBuilder texture(Texture texture) {
        this.texture = texture;
        return this;
    }

    public EntityBuilder direction(Vector direction) {
        this.direction = direction;
        return this;
    }

    public EntityBuilder with(Component component) {
        components.add(component);
        return this;
    }

    public Entity build() {
        if (hitBox != null) {
            hitBox.setSolid(isSolid);
        }
        components.set(0, new TransformComponent(position, rotation));
        components.set(1, new RenderComponent(texture));
        components.set(2, new PhysicComponent(hitBox, direction));
        return new Entity(type, components);
    }
}
