package fr.univ.engine.render.texture;

import javafx.scene.image.Image;

import java.io.File;
import java.net.URL;
import java.util.HashMap;

public class CachedResourcesResolver implements TextureResolver {
    private final String folder;
    private final HashMap<String, Image> cache;

    public CachedResourcesResolver(String folder) {
        this.folder = folder;
        this.cache = new HashMap<>();
    }

    @Override
    public Image getTexture(String name) throws IllegalArgumentException {
        if (cache.containsKey(name)) {
            return cache.get(name);
        }

        URL url = getClass().getClassLoader().getResource(folder + name);
        if (url == null) {
            throw new IllegalArgumentException(String.format("No file found at '%s'", folder+name));
        }

        try {
            Image texture = new Image(getClass().getClassLoader().getResourceAsStream(folder+name));
            cache.put(name, texture);
            return texture;
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Failed to load image from '%s'", folder+name), e);
        }
    }
}
