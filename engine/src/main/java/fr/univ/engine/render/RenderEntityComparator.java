package fr.univ.engine.render;

import fr.univ.engine.render.texture.Texture;

import java.util.Comparator;

/**
 * Define how to compare render entities.
 */
public class RenderEntityComparator implements Comparator<RenderEntity> {
    @Override
    public int compare(RenderEntity o1, RenderEntity o2) {
        Texture t1 = o1.getTexture();
        Texture t2 = o2.getTexture();

        if (t1 == null || t2 == null) {
            return 0;
        } else {
            return t1.zIndex() - t2.zIndex();
        }
    }
}
