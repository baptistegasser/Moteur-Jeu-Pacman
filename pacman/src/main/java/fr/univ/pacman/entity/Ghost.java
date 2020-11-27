package fr.univ.pacman.entity;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.PhysicObject;
import fr.univ.engine.physic.hitbox.SquareHitBox;
import fr.univ.pacman.gameplay.GamePlay;

/**
 * The class handling the logic of Ghost.
 */
public class Ghost extends GameObject {
    public Ghost(int x, int y, String color) {
        super(x, y);
        setName("GHOST-" + color.toUpperCase());

        renderObject.width = 20;
        renderObject.height = 20;
        renderObject.zIndex = 9;
        try {
            switch (color) {
                case "blue":
                    renderObject.textureName = "sprites/ghosts/blueGhost.png";
                    break;
                case "red":
                    renderObject.textureName = "sprites/ghosts/redGhost.png";
                    break;
                case "orange":
                    renderObject.textureName = "sprites/ghosts/orangeGhost.png";
                    break;
                case "pink":
                    renderObject.textureName = "sprites/ghosts/pinkGhost.png";
                    break;
                default:
                    throw new Exception("this color not exist");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        physicObject.direction = new Vector(0,0);

        // Set HitBox size to 16 because render size of ghost are a little bigger than physic size
        this.physicObject.setHitBox(new SquareHitBox(16));
    }

    @Override
    public void onCollisionEnter(PhysicObject collider) {
        if (collider.name().equals("PAC-MAN")) {
            GamePlay.getInventory().lostLife();
            collider.getPos().set(new Point(0,32));
        }
    }
}
