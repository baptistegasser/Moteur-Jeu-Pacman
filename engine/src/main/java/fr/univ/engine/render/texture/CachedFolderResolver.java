package fr.univ.engine.render.texture;

import javafx.scene.image.Image;

import java.io.File;
import java.util.HashMap;

public class CachedFolderResolver implements TextureResolver {
    private final File folder;
    private final HashMap<String, Image> cache;

    public CachedFolderResolver(File folder) {
        this.folder = folder;
        this.cache = new HashMap<>();
    }

    @Override
    public Image getTexture(String name) throws IllegalArgumentException {
        if (cache.containsKey(name)) {
            return cache.get(name);
        }

        File imgFile = new File(folder.getAbsolutePath() + File.separatorChar + name);
        if (!imgFile.exists() || !imgFile.isFile()) {
            throw new IllegalArgumentException(String.format("No file found for name '%s'", name));
        }

        try {
            Image texture = new Image("file://"+imgFile.getAbsolutePath());
            cache.put(name, texture);
            return texture;
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Failed to load image from '%s'", imgFile.getAbsolutePath()), e);
        }
    }
}
