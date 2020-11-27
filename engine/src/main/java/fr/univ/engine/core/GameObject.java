package fr.univ.engine.core;

import fr.univ.engine.math.Point;
import fr.univ.engine.physic.PhysicEntity;
import fr.univ.engine.physic.PhysicObject;
import fr.univ.engine.render.entity.RenderEntity;
import fr.univ.engine.render.texture.Texture;

import java.util.Objects;

/**
 * The {@code GameObject} is the base class for any object to use in a game.
 */
public abstract class GameObject implements RenderEntity, PhysicEntity {
    private static int currentIDs = 0;

    /**
     * Every game object have a unique ID to identify it.
     */
    private final int ID;

    /**
     * The render component used to display this object.
     */
    protected PhysicObject physicObject;

    /**
     * Allow to identify a GameObject, might not unique.
     */
    private String name;

    private Point position;
    private Texture texture;

    protected GameObject() {
        this(0, 0);
    }

    protected GameObject(double x, double y) {
        this.position = new Point(x, y);
        this.ID = GameObject.currentIDs++;
        this.physicObject = new PhysicObject(this.position);

        setName("UNKNOWN GameObject"); // default GameObject name
    }

    @Override
    public PhysicObject getPhysicObject() {
        return physicObject;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.physicObject.setName(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameObject that = (GameObject) o;
        return ID == that.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public int getId() {
        return ID;
    }

    /**
     * Destroy an object, he don't have render and physic
     */
    public void destroy() {
        Point pos = new Point(0, 0);
        this.physicObject = new PhysicObject(pos);
        this.texture = null;
    }

    @Override
    public Point pos() {
        return this.position;
    }

    @Override
    public Texture getTexture() {
        return this.texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
