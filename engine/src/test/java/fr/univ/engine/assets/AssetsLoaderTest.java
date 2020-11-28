package fr.univ.engine.assets;

import fr.univ.engine.GameEngineTest;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.text.Font;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void loadFont() {
        String fontName = "test.ttf";
        assertDoesNotThrow(() -> {
            Font font = AssetsLoader.loadFont(fontName);
            assertNotNull(font);
        });
        assertNotNull(AssetsLoader.getFromCache(Font.class, AssetsLoader.ASSETS_ROOT + AssetsLoader.FONTS + fontName));
    }

    @Test
    void loadSound() {
        String soundName = "test.wav";
        assertDoesNotThrow(() -> {
            Media sound = AssetsLoader.loadSound(soundName);
            assertNotNull(sound);
        });
        assertNotNull(AssetsLoader.getFromCache(Media.class, AssetsLoader.ASSETS_ROOT + AssetsLoader.SOUNDS + soundName));
    }

    @Test
    void loadInvalid() {
        String fontName = "test.ttf";
        String soundName = "test.wav";
        String imageName = "test.png";

        assertDoesNotThrow(() -> AssetsLoader.loadFont(fontName));
        assertThrows(AssetException.class, () -> AssetsLoader.loadSound(fontName));
        assertThrows(AssetException.class, () -> AssetsLoader.loadImage(fontName));

        assertThrows(AssetException.class, () -> AssetsLoader.loadFont(soundName));
        assertDoesNotThrow(() -> AssetsLoader.loadSound(soundName));
        assertThrows(AssetException.class, () -> AssetsLoader.loadImage(soundName));

        assertThrows(AssetException.class, () -> AssetsLoader.loadFont(imageName));
        assertThrows(AssetException.class, () -> AssetsLoader.loadSound(imageName));
        assertDoesNotThrow(() -> AssetsLoader.loadImage(imageName));
    }
}