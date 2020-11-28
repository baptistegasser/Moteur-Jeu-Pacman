package fr.univ.engine.assets;

import fr.univ.engine.core.GameApplication;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.text.Font;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

/**
 * Load an asset from the src/main/resources folder of a game.
 * The loader will look for specifics folder name to find the
 * different types of assets:
 * - /fonts/    : font to render text
 * - /sounds/   : short sound to play on events
 * - /textures/ : image used for textures or anything else
 *
 * The different assets are cached as reading a file is heavy and slow.
 *
 * This class is a singleton as to prevent duplication of datas in
 * multiple loaders cache.
 */
public final class AssetsLoader {
    public final static String ASSETS_ROOT = "/assets";
    public final static String TEXTURES = "/textures/";
    public final static String SOUNDS = "/sounds/";
    public final static String FONTS = "/fonts/";


    /**
     * The cache of assets.
     */
    private static final HashMap<String, Object> cache;

    static {
        cache = new HashMap<>();
    }

    /**
     * Load a image from the textures folder.
     * The name is relative ie:
     *  - pacman.png
     *  - item/pac.png
     *
     * @param name the image full name relative to the texture folder.
     * @return a {@link Image} instance.
     */
    public static Image loadImage(String name) {
        String key = ASSETS_ROOT + TEXTURES + name;
        Image image = getFromCache(Image.class, key);

        if (image == null) {
            URI uri = getResourceURI(key);
            image = new Image(uri.toString());
            cache.put(key, image);
        }
        return image;
    }

    /**
     * Load a font from the fonts folder.
     * The name is relative ie:
     * ie:
     *  - font.ttf
     *  - halloween/hell.ttf
     *
     * @param name the font full name relative to the fonts folder.
     * @return a {@link Font} instance.
     */
    public static Font loadFont(String name) {
        String key = ASSETS_ROOT + FONTS + name;
        Font font = getFromCache(Font.class, key);

        if (font == null) {
            URI uri = getResourceURI(key);
            font = Font.loadFont(uri.toString().replace("%20", " "), 1);
            cache.put(key, font);
        }
        return font;
    }


    /**
     * Load a sound from the fonts folder.
     * The name is relative ie:
     *  - sound.wav
     *  - cave/scared.wav
     *
     * @param name the sound full name relative to the sounds folder.
     * @return a {@link Media} instance.
     */
    public static Media loadSound(String name) {
        String key = ASSETS_ROOT + SOUNDS + name;
        Media sound = getFromCache(Media.class, key);

        if (sound == null) {
            URI uri = getResourceURI(key);
            sound = new Media(uri.toString());
            cache.put(key, sound);
        }
        return sound;
    }

    private static URI getResourceURI(String relativePath) {
        // Get ressource from the resources folder of the game implementation.
        URL url = GameApplication.getInstance().getClass().getResource(relativePath);

        if (url == null) {
            throw new AssetException(String.format("Failed to find asset at '%s'", relativePath));
        }

        try {
            return url.toURI();
        } catch (URISyntaxException e) {
            throw new AssetException(String.format("Invalid path to asset at '%s'", relativePath), e);
        }
    }

    /**
     * Empty the cache, useful for testing purpose.
     */
    protected static void clearCache() {
        cache.clear();
    }

    /**
     * Get an asset from the cache.
     * If the cached asset is not from the correct class,
     * this will return null.
     *
     * @param clazz the target type class.
     * @param key the jey mapped to the asset.
     * @param <T> the target type.
     * @return the asset or null.
     */
    protected static <T> T getFromCache(Class<T> clazz, String key) {
        Object o = cache.get(key);
        // Return null if null or invalid type.
        if (!clazz.isInstance(o)) return null;
        //noinspection unchecked
        return (T) o;
    }
}