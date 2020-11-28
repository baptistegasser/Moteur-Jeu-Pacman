package fr.univ.pacman.migration;

import fr.univ.engine.core.Level;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.core.entity.EntityBuilder;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.hitbox.SquareHitBox;
import fr.univ.engine.render.texture.Texture;
import fr.univ.engine.utils.CachedResourcesLoader;
import fr.univ.pacman.migration.component.PacManLogic;

public class Labyrinth extends Level {
    /**
     * The class used to load our resources.
     */
    private final static CachedResourcesLoader resources = new CachedResourcesLoader("assets/");

    public Labyrinth() {
        Texture texture = new Texture(16, 16, resources.getImage("sprites/pacman.png"));
        texture.setZIndex(10);
        Entity pacman = new EntityBuilder()
                .type(Type.PACMAN)
                .position(new Point(0, 32))
                .rotation(-90)
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                .with(new PacManLogic())
                .build();
        add(pacman);
    }
}
