package fr.univ.engine.assets;

import fr.univ.engine.GameEngineTest;
import fr.univ.engine.render.texture.Animation;
import fr.univ.engine.render.texture.Sprite;
import fr.univ.engine.sound.Clip;
import fr.univ.engine.sound.Song;
import fr.univ.engine.ui.JFXView;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link AssetsLoader} class.
 */
class AssetsLoaderTest extends GameEngineTest {

    @BeforeEach
    void setUp() {
        AssetsLoader.clearCache(); // Clear the cache to be sure
    }

    @Test
    void loadImage() {
        String imageName = "test.png";
        assertDoesNotThrow(() -> {
            Image img = AssetsLoader.loadImage(imageName);
            assertNotNull(img);
        });
        assertNotNull(AssetsLoader.getFromCache(Image.class, AssetsLoader.ASSETS_ROOT + AssetsLoader.TEXTURES + imageName));
    }

    @Test
    void loadSprite() {
        String imageName = "test.png";
        assertDoesNotThrow(() -> {
            Sprite sprite = AssetsLoader.loadSprite(imageName);
            assertNotNull(sprite);
        });
    }

    @Test
    void loadAnimation() {
        String imageName = "test.png";
        assertDoesNotThrow(() -> {
            Animation animation = AssetsLoader.loadAnimation(100, Collections.singletonList(imageName));
            assertNotNull(animation);
        });
    }

    @Test
    void loadFont() {
        String fontName = "test.ttf";
        assertDoesNotThrow(() -> {
            Font font = AssetsLoader.loadFont(fontName);
            assertNotNull(font);
        });
        assertNotNull(AssetsLoader.getFromCache(Font.class, AssetsLoader.ASSETS_ROOT + AssetsLoader.FONTS + fontName));
    }

    @Test
    void loadSong() {
        String soundName = "test.wav";
        assertDoesNotThrow(() -> {
            Song song = AssetsLoader.loadSong(soundName);
            assertNotNull(song);
        });
    }

    @Test
    void loadClip() {
        String soundName = "test.wav";
        assertDoesNotThrow(() -> {
            Clip clip = AssetsLoader.loadClip(soundName);
            assertNotNull(clip);
        });
    }

    @Test
    public void loadView() {
        String invalid = "invalid.fxml";
        String notExist = "aaaaaaaaaaa.fxml";
        String name = "view.fxml";
        assertDoesNotThrow(() -> {
            assertThrows(AssetException.class, () -> AssetsLoader.loadView(invalid));
            assertThrows(AssetException.class, () -> AssetsLoader.loadView(notExist));
            JFXView view = AssetsLoader.loadView(name);
            assertNotNull(view);
        });
    }

    @Test
    void loadInvalid() {
        String fontName = "test.ttf";
        String soundName = "test.wav";
        String imageName = "test.png";

        assertDoesNotThrow(() -> AssetsLoader.loadFont(fontName));
        assertThrows(AssetException.class, () -> AssetsLoader.loadSong(fontName));
        assertThrows(AssetException.class, () -> AssetsLoader.loadImage(fontName));

        assertThrows(AssetException.class, () -> AssetsLoader.loadFont(soundName));
        assertDoesNotThrow(() -> AssetsLoader.loadSong(soundName));
        assertThrows(AssetException.class, () -> AssetsLoader.loadImage(soundName));

        assertThrows(AssetException.class, () -> AssetsLoader.loadFont(imageName));
        assertThrows(AssetException.class, () -> AssetsLoader.loadSong(imageName));
        assertDoesNotThrow(() -> AssetsLoader.loadImage(imageName));
    }
}