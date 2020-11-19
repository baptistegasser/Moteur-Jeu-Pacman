package fr.univ.engine.core;

import fr.univ.engine.math.Point;
import fr.univ.engine.physic.PhysicEntity;
import fr.univ.engine.physic.PhysicObject;
import fr.univ.engine.render.RenderEntity;
import fr.univ.engine.render.RenderObject;

/**
 * The {@code GameObject} is the base class for any object to use in a game.
 */
public abstract class GameObject implements RenderEntity, PhysicEntity {
    /**
     * The render component used to display this object.
     */
    protected final RenderObject renderObject;

    /**
     * The render component used to display this object.
     */
    protected final PhysicObject physicObject;

    private Point pos;

    protected GameObject() {
        pos = new Point(0,0);
        this.physicObject = new PhysicObject(pos);
        this.renderObject = new RenderObject(pos);
    }

    @Override
    public RenderObject getRenderObject() {
        return renderObject;
    }

    @Override
    public PhysicObject getPhysicObject() {
        return physicObject;
    }
}
