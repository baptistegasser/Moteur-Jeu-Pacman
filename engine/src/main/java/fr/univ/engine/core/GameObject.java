package fr.univ.engine.core;

import fr.univ.engine.math.Point;
import fr.univ.engine.physic.PhysicEntity;
import fr.univ.engine.physic.PhysicObject;
import fr.univ.engine.render.RenderEntity;
import fr.univ.engine.render.RenderObject;

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
    protected RenderObject renderObject;

    /**
     * The render component used to display this object.
     */
    protected PhysicObject physicObject;

    /**
     * Allow to identify a GameObject, might not unique.
     */
    private String name;

    protected GameObject() {
        this(0, 0);
    }

    protected GameObject(double x, double y) {
        Point pos = new Point(x, y);
        this.ID = GameObject.currentIDs++;
        this.physicObject = new PhysicObject(pos);
        this.renderObject = new RenderObject(pos);

        setName("UNKNOWN GameObject"); // default GameObject name
    }

    @Override
    public RenderObject getRenderObject() {
        return renderObject;
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
        this.renderObject.setName(name);
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
        Point point = new Point(0,0);
        this.renderObject = new RenderObject(point);
        this.physicObject = new PhysicObject(point);
    }
}
