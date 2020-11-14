package fr.univ.engine.core;

import fr.univ.engine.math.Point;
import fr.univ.engine.physic.PhysicObject;
import fr.univ.engine.render.RenderObject;

/**
 * The {@code GameObject} is the base class for any object to use in a game.
 */
public abstract class GameObject {
    /**
     * The render component used to display this object.
     */
    protected final RenderObject renderObject;

    /**
     * The render component used to display this object.
     */
    protected final PhysicObject physicObject;

    protected Point point;

    protected GameObject() {
        this.point = new Point(0,0);
        this.physicObject = new PhysicObject(this.point);
        this.renderObject = new RenderObject(this.point);
    }

    /**
     * Function permit to update objects in mainLoop
     */
    public boolean update() {
        return false;
    }

    /**
     * Function permit to update objects but better than update
     */
    public void fixedUpdate(double dt) {}

    /**
     * Function call when two objects are in collision
     */
    public void onTriggerEnter() {}

    public RenderObject getRenderObject() {
        return renderObject;
    }

    public PhysicObject getPhysicObject() {
        return physicObject;
    }

    public Point getPos() {
        return point;
    }
}
