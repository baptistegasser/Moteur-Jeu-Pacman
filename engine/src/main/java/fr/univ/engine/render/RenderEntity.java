package fr.univ.engine.render;

/**
 * A Render entity is an entity able to be rendered.
 * It provide informations in the form of a {@link RenderObject} that contain necessary
 * elements to render the {@code RenderEntity} instance.
 */
public interface RenderEntity {
    /**
     * @return the RenderObject associated to this entity
     */
    RenderObject getRenderObject();

    /**
     * This function is called before every render of the object, thus once per frame.
     * Provide a default update implementation that does nothing.
     */
    default void update() {
    }
}
