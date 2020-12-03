package fr.univ.engine.render;

import fr.univ.engine.core.Component;
import fr.univ.engine.render.texture.Texture;

/**
 * Render component for entity with textures.
 */
public class RenderComponent extends Component {
    /**
     * The texture used to render the entity.
     */
    private Texture texture;

    /**
     * Texture must animated in the animator while global animation
     */
    private boolean forAnimator = false;

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

    public void setForAnimator(boolean forAnimator) {
        this.forAnimator = forAnimator;
    }

    public boolean isForAnimator() {
        return forAnimator;
    }
}
