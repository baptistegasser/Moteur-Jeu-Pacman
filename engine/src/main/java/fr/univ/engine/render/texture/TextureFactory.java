package fr.univ.engine.render.texture;

import fr.univ.engine.render.RenderException;
import fr.univ.engine.utils.CachedResourcesLoader;
import javafx.scene.image.Image;

/**
 * Factory used to load textures.
 */
public class TextureFactory {
    /**
     * The resources loader used to load a texture's image.
     */
    private final CachedResourcesLoader loader;

    public TextureFactory(CachedResourcesLoader loader) {
        this.loader = loader;
    }

    /**
     * Create a texture from a resource image.
     * 
     * @param name the relative name of the file as specified in {@link CachedResourcesLoader#getImage(String)} (String)}.
     * @return a texture or null if the image was not found
     */
    public Texture fromResourcesFile(String name) {
        Image image = loader.getImage(name);
        if (image == null) {
            throw new RenderException(String.format("The texture for %s is null.", name));
        }
        return new Texture(image);
    }
}
