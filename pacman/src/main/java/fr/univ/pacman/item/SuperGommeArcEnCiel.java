package fr.univ.pacman.item;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.physic.hitbox.SquareHitBox;
import fr.univ.engine.render.texture.Texture;
import fr.univ.pacman.Game;

public class SuperGommeArcEnCiel extends GameObject {
    public SuperGommeArcEnCiel(double x, double y) {
        super(x, y);
        setName("SUPER-GOMME-ARC-EN-CIEL");

        Texture texture = new Texture(10, 10, Game.resolver.getImage("item/superGommeArcEnCiel.png"));
        texture.setZIndex(5);
        setTexture(texture);

        this.physicObject.setHitBox(new SquareHitBox(10));
    }
}
