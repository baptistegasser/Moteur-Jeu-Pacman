package fr.univ.engine.render.texture;

import java.util.Comparator;

/**
 * Comparator of textures based on their z-index.
 * Useful to sort which textures should be rendered first.
 */
public class TextureComparator implements Comparator<Texture> {
    /**
     * Compare two textures
     * @param t1 the first texture
     * @param t2 the second texture
     * @return if textures equals
     */
    @Override
    public int compare(Texture t1, Texture t2) {
        if (t1 == null && t2 == null) {
            return 0;
        } else if (t1 == null) {
            return -1;
        } else if (t2 == null) {
            return 1;
        }

        return t1.zIndex() - t2.zIndex();
    }
}
