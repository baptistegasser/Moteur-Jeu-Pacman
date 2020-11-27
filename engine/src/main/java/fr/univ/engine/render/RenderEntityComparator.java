package fr.univ.engine.render;

import java.util.Comparator;

/**
 * Define how to compare render entities.
 */
public class RenderEntityComparator implements Comparator<RenderEntity> {
    @Override
    public int compare(RenderEntity o1, RenderEntity o2) {
        return o1.getTexture().zIndex() - o2.getTexture().zIndex();
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
