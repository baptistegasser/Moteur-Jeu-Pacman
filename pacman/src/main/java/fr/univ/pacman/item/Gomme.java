package fr.univ.pacman.item;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.PhysicObject;
import fr.univ.engine.physic.hitbox.SquareHitBox;
import fr.univ.pacman.gameplay.GamePlay;

/**
 * The class handling the logic of Gomme.
 */
public class Gomme extends GameObject {
    public Gomme(int x, int y) {
        super(x, y);
        setName("GOMME");

        renderObject.width = 3;
        renderObject.height = 3;
        renderObject.zIndex = 5;
        renderObject.textureName = "item/gomme.png";

        this.physicObject.setHitBox(new SquareHitBox(renderObject.width));
    }

    @Override
    public void onCollisionEnter(PhysicObject collider) {
        if (collider.name().equals("PAC-MAN")) {
            GamePlay.getInventory().addScore(10);
            this.destroy();
        }
    }
}
