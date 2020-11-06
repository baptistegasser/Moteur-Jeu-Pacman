package fr.univ.engine.render.texture;

import javafx.scene.image.Image;

public interface TextureResolver {
    Image getTexture(String name) throws IllegalArgumentException;
}
