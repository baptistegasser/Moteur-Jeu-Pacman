package fr.univ.pacman.util;

import javafx.scene.image.Image;

import java.net.URL;

public class ResourcesUtil {
    /**
     * Load the url of an assets for the Pac-Man game from the folder fr/univ/pacman/assets inside the resources folder.
     * Name should not start with "/" and be relative to the aforementioned folder.
     *
     * @param name the name of the resources, like sprites/pacman.jpg
     * @return a URL instance pointing to the assets or null if not found
     */
    public static URL getAssetsURL(final String name) {
        return ResourcesUtil.class.getResource("/fr/univ/pacman/assets/" + name);
    }

    public static Image loadImage(String name) {
        URL url = getAssetsURL(name);
        if (url == null) {
            return null;
        } else {
            return new Image("file://"+url.getPath());
        }
    }
}
