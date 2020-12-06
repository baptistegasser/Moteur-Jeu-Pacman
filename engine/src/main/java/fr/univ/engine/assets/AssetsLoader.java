package fr.univ.engine.assets;

import fr.univ.engine.core.GameApplication;
import fr.univ.engine.render.texture.Animation;
import fr.univ.engine.render.texture.Sprite;
import fr.univ.engine.sound.Song;
import fr.univ.engine.sound.Clip;
import fr.univ.engine.ui.JFXController;
import fr.univ.engine.ui.JFXView;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Load an asset from the src/main/resources folder of a game.
 * The loader will look for specifics folder name to find the
 * different types of assets:
 * - /fonts/    : font to render text
 * - /sounds/   : short sound to play on events
 * - /level/ : files used for levels
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
    public final static String LEVELS = "/level/";
    public final static String SOUNDS = "/sounds/";
    public final static String FONTS = "/fonts/";
    public final static String UI = "/ui/";


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
     * Load a fixed texture from the textures folder.
     * The name is relative ie:
     *  - pacman.png
     *  - item/pac.png
     *
     * @param name the image full name relative to the texture folder.
     * @return a {@link Sprite} instance.
     */
    public static Sprite loadSprite(String name) {
        Sprite texture = getFromCache(Sprite.class, name);

        if (texture == null) {
            texture = new Sprite(loadImage(name));
            cache.put(name, texture);
        }

        return texture;
    }

    /**
     * Load an animated texture from the textures folder.
     * The names are relative ie:
     *  - pacman.png
     *  - item/pac.png
     *
     * @param frameDuration the duration of a single frame.
     * @param names the frames' images names relative to the texture folder.
     * @return a {@link Animation} instance.
     */
    public static Animation loadAnimation(int frameDuration, List<String> names) {
        StringBuilder key = new StringBuilder();
        names.forEach(key::append);
        Animation texture = getFromCache(Animation.class, key.toString());

        if (texture == null) {
            List<Image> frames = new ArrayList<>();
            names.forEach(name -> frames.add(loadImage(name)));
            texture = new Animation(frameDuration, frames);
            cache.put(key.toString(), texture);
        }

        return texture;
    }

    /**
     * Load a level from the levels folder.
     * The name is relative ie:
     *  - map.txt
     *  - levels/hell_1.txt
     *
     * @param name the level full name relative to the levels folder.
     * @return a {@link InputStream} pointing to the file.
     */
    public static InputStream getLevel(String name) {
        String key = ASSETS_ROOT + LEVELS + name;
        return getResourceAsStream(key);
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
     * Load a song from the fonts folder.
     * Song are more suited for long running instance, such as background music, dialogues.
     * The name is relative ie:
     *  - sound.wav
     *  - cave/scared.wav
     *
     * @param name the sound full name relative to the sounds folder.
     * @return a {@link Song} instance.
     */
    public static Song loadSong(String name) {
        String key = ASSETS_ROOT + SOUNDS + name;

        URI uri = getResourceURI(key);
        MediaPlayer player = new MediaPlayer(new Media(uri.toString()));
        return new Song(player, name);
    }

    /**
     * Load an audio clip from the fonts folder.
     * Clips are more suited for short sounds.
     * The name is relative ie:
     *  - sound.wav
     *  - gun/ak_47.wav
     *
     * @param name the clip full name relative to the sounds folder.
     * @return a {@link Clip} instance.
     */
    public static Clip loadClip(String name) {
        String key = ASSETS_ROOT + SOUNDS + name;

        URI uri = getResourceURI(key);
        AudioClip player = new AudioClip(uri.toString());

        return new Clip(player, name);
    }

    /**
     * Load an JavaFX view from fxml.
     * The name is relative ie:
     *  - menu.fxml
     *  - space/head.fxml
     *
     * @param name the fxml full name relative to the ui folder.
     * @return a {@link JFXView} instance.
     */
    public static JFXView loadView(String name) {
        try {
            String key = ASSETS_ROOT + UI + name;
            URI uri = getResourceURI(key);
            FXMLLoader loader = new FXMLLoader(uri.toURL());
            JFXView view = new JFXView(loader.load());

            Object controller = loader.getController();
            if (controller instanceof JFXController) {
                view.setController((JFXController) controller);
            }
            return view;
        } catch (Exception e) {
            throw new AssetException("Failed to load fxml", e);
        }
    }

    /**
     * Get an URI to the resource.
     *
     * @param relativePath the path of the resource.
     * @return an {@link URI} to the the resource.
     */
    private static URI getResourceURI(String relativePath) {
        // Get ressource from the resources folder of the game implementation.
        URL url = GameApplication.app().getClass().getResource(relativePath);

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
     * Get a resource as an readable stream.
     *
     * @param relativePath the path of the resource.
     * @return an {@link InputStream} instance.
     */
    private static InputStream getResourceAsStream(String relativePath) {
        // Get ressource from the resources folder of the game implementation.
        InputStream is = GameApplication.app().getClass().getResourceAsStream(relativePath);

        if (is == null) {
            throw new AssetException(String.format("Failed to find asset at '%s'", relativePath));
        } else {
            return is;
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
