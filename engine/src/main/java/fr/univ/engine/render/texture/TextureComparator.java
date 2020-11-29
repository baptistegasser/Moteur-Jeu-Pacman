package fr.univ.engine.render.texture;

import java.util.Comparator;

/**
 * Comparator of textures based on their z-index.
 * Useful to sort which textures should be rendered first.
 */
public class TextureComparator implements Comparator<Texture> {
    @Override
    public int compare(Texture t1, Texture t2) {
        if (t1 == null) {
            return -1;
        } else if (t2 == null) {
            return 1;
        }

        return t1.zIndex() - t2.zIndex();
    }
}
