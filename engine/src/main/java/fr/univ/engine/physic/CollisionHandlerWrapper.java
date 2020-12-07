package fr.univ.engine.physic;

/**
 * Wrapper that associate a {@link CollisionHandler} with two types.
 * This create the explicit link that this handler handle collision
 * between entities of type t1 and t2.
 */
public class CollisionHandlerWrapper {
    /**
     * Object collision 1
     */
    private final Object t1;
    /**
     * Object collision 2
     */
    private final Object t2;
    /**
     * The handler charged of handling the collision.
     */
    private final CollisionHandler handler;

    public CollisionHandlerWrapper(Object t1, Object t2, CollisionHandler handler) {
        this.t1 = t1;
        this.t2 = t2;
        this.handler = handler;
    }

    /**
     * Test if this handler can handle collision between the passed type.
     *
     * @param t1 the first type of entity.
     * @param t2 the second type of entity.
     * @return true if the handle is associated with theses two types.
     */
    public final boolean canHandle(Object t1, Object t2) {
        return this.t1.equals(t1) && this.t2.equals(t2);
    }

    /**
     * @return the wrapped handler.
     */
    public CollisionHandler getHandler() {
        return handler;
    }
}
