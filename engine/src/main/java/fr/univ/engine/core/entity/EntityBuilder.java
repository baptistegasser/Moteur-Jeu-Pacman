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
 * Builder class used to create entities more easily.
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
     * The entity have a solid hitbox, false by default.
     */
    private boolean isSolid = false;
    /**
     * The entity have a same hitbox, false by default.
     */
    private boolean isSpecial = false;
    /**
     * The direction in which the entity will go at spawn.
     */
    private Vector direction = new Vector(0, 0);

    /**
     * The speed of physic entity
     */
    private double speed = 1;
    /**
     * The texture used to render the entity.
     */
    private Texture texture = null;
    /**
     * The components defining the behavior of the entity.
     */
    private final List<Component> components;

    public EntityBuilder() {
        // Reserve place for transform, render and physic components.
        components = new ArrayList<>(Arrays.asList(null, null, null));
    }

    /**
     * Set the type of the entity.
     *
     * @param type the wanted type.
     * @return this builder allowing to chain methods.
     */
    public EntityBuilder type(Object type) {
        this.type = type;
        return this;
    }

    /**
     * Set the spawn position of the entity.
     *
     * @param position the spawn position.
     * @return this builder allowing to chain methods.
     */
    public EntityBuilder position(Point position) {
        this.position = position;
        return this;
    }

    /**
     * Set the rotation of the entity.
     *
     * @param rotation the start rotation.
     * @return this builder allowing to chain methods.
     */
    public EntityBuilder rotation(double rotation) {
        this.rotation = rotation;
        return this;
    }

    /**
     * Set the hitbox to use for the entity collision.
     *
     * @param hitBox the desired hitbox.
     * @return this builder allowing to chain methods.
     */
    public EntityBuilder hitbox(HitBox hitBox) {
        this.hitBox = hitBox;
        return this;
    }

    /**
     * Set if the entity should be solid.
     *
     * @param isSolid the desired solidity value.
     * @return this builder allowing to chain methods.
     */
    public EntityBuilder isSolid(boolean isSolid) {
        this.isSolid = isSolid;
        return this;
    }

    /**
     * Set if the entity hitbox should be special.
     *
     * @param isSpecial the desired special value.
     * @return this builder allowing to chain methods.
     */
    public EntityBuilder isSpecial(boolean isSpecial) {
        this.isSpecial = isSpecial;
        return this;
    }

    /**
     * Set the texture to render the entity.
     *
     * @param texture the desired texture.
     * @return this builder allowing to chain methods.
     */
    public EntityBuilder texture(Texture texture) {
        this.texture = texture;
        return this;
    }

    /**
     * Set the direction of the entity at spawn.
     *
     * @param direction the desired direction.
     * @return this builder allowing to chain methods.
     */
    public EntityBuilder direction(Vector direction) {
        this.direction = direction;
        return this;
    }

    /**
     * Set the speed of the entity at spawn.
     *
     * @param speed the desired speed.
     * @return this builder allowing to chain methods.
     */
    public EntityBuilder speed(double speed) {
        this.speed = speed;
        return this;
    }

    /**
     * Add a component to define the behavior of the entity.
     *
     * @param component a component of the entity.
     * @return this builder allowing to chain methods.
     */
    public EntityBuilder with(Component component) {
        components.add(component);
        return this;
    }

    /**
     * Build a new entity with all the attributes values currently set.
     *
     * @return an entity instance matching the builder state.
     */
    public Entity build() {
        if (hitBox != null) {
            hitBox.setSolid(isSolid);
            hitBox.setSpecial(isSpecial);
        }
        components.set(0, new TransformComponent(position, rotation));
        components.set(1, new RenderComponent(texture));
        components.set(2, new PhysicComponent(hitBox, direction, speed));
        return new Entity(type, components);
    }
}
