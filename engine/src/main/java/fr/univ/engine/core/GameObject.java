package fr.univ.engine.core;

import fr.univ.engine.render.RenderObject;

/**
 * The {@code GameObject} is the base class for any object to use in a game.
 */
public abstract class GameObject {
    /**
     * The render component used to display this object.
     */
    protected final RenderObject renderObject;

    protected GameObject() {
        this.renderObject = new RenderObject();
    }
}
