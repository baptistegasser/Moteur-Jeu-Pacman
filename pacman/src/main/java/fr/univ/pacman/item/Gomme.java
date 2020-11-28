package fr.univ.pacman.item;

import fr.univ.engine.physic.hitbox.SquareHitBox;
import fr.univ.engine.render.texture.Texture;
import fr.univ.pacman.Game;
import fr.univ.pacman.gameplay.GamePlay;

/**
 * The class handling the logic of Gomme.
 */
public class Gomme /*extends GameObject*/ {/*
    public Gomme(int x, int y) {
        super(x, y);
        setName("GOMME");

        Texture texture = new Texture(3, 3, Game.resolver.getImage("item/gomme.png"));
        texture.setZIndex(5);
        setTexture(texture);

        this.physicObject.setHitBox(new SquareHitBox(3));
    }

    @Override
    public void onCollisionEnter(PhysicObject collider) {
        if (collider.name().equals("PAC-MAN")) {
            GamePlay.getInventory().addScore(10);
            this.destroy();
        }
    }*/
}
