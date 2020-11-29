package fr.univ.engine.render.component;

import fr.univ.engine.core.component.Component;
import fr.univ.engine.render.texture.Texture;

/**
 * Render component for entity with textures.
 */
public class RenderComponent extends Component {
    /**
     * The texture used to render the entity.
     */
    private Texture texture;

    public RenderComponent(Texture texture) {
        this.texture = texture;
    }

    /**
     * Get the texture of this entity.
     *
     * @return the texture displayed for this entity.
     */
    public Texture getTexture() {
        return this.texture;
    }

    /**
     * Set a new texture for the entity.
     *
     * @param texture the new texture.
     */
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
