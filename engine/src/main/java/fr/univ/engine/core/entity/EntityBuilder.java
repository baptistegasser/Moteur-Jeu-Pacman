package fr.univ.engine.core.entity;

import fr.univ.engine.core.component.Component;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Transform;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.component.PhysicComponent;
import fr.univ.engine.physic.hitbox.HitBox;
import fr.univ.engine.render.component.RenderComponent;
import fr.univ.engine.render.texture.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class charged of building entities.
 */
public class EntityBuilder {
    private Object type = null;

    private Point position = new Point(0, 0);

    private double rotation = 0;

    private HitBox hitBox = null;

    private Vector direction = new Vector(0, 0);

    private Texture texture = null;

    List<Component> components;

    public EntityBuilder() {
        // Reserve place for render and physic components.
        components = new ArrayList<>(Arrays.asList(null, null));
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
        components.set(0, new RenderComponent(texture));
        components.set(1, new PhysicComponent(hitBox, direction));
        Transform transform = new Transform(position, rotation);
        return new Entity(type, transform, components);
    }
}
