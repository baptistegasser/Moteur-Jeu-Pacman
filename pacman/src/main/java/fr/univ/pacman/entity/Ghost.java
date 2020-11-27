package fr.univ.pacman.entity;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.PhysicObject;
import fr.univ.engine.physic.hitbox.SquareHitBox;
import fr.univ.engine.render.texture.Texture;
import fr.univ.pacman.gameplay.GamePlay;

/**
 * The class handling the logic of Ghost.
 */
public class Ghost extends GameObject {
    public Ghost(int x, int y, String color) {
        super(x, y);
        setName("GHOST-" + color.toUpperCase());

        Texture texture = new Texture(20, 20, GamePlay.resolver.getImage("sprites/ghosts/"+color+"Ghost.png"));
        texture.setZIndex(9);
        setTexture(texture);

        physicObject.direction = new Vector(0,0);

        // Set HitBox size to 16 because render size of ghost are a little bigger than physic size
        this.physicObject.setHitBox(new SquareHitBox(16));
    }

    @Override
    public void onCollisionEnter(PhysicObject collider) {

    }
}
