package fr.univ.engine.render.renderer;

import fr.univ.engine.render.entity.RenderEntity;

import java.util.List;

/**
 * Class charged to implement methods used to render {@link RenderEntity}.
 *
 * @param <T> the type of the area used to render as specified in {@link ViewPort}.
 */
public abstract class Renderer<T> {
    /**
     * The viewport used by this renderer.
     */
    protected final ViewPort<T> viewport;

    public Renderer(ViewPort<T> viewport) {
        this.viewport = viewport;
    }

    /**
     * Abstract method to implement to define how to render a {@code RenderEntity}.
     * Ideally an implementation should render all of them at once.
     *
     * @param entities the list of entities to render
     */
    public abstract void render(List<RenderEntity> entities);
}
