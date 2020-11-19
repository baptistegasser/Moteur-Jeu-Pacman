package fr.univ.pacman.entity;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.hitbox.CircleHitBox;
import fr.univ.engine.physic.PhysicObject;
import javafx.scene.shape.Circle;

import java.util.Random;

/**
 * The class handling the logic of Pac-Man controlled by the player.
 */
public class PacMan extends GameObject {
    public PacMan(int posX, int posY) {
        renderObject.pos.x = posX;
        renderObject.pos.y = posY;
        renderObject.width = 16;
        renderObject.height = 16;
        renderObject.zIndex = 10;
        renderObject.textureName = "sprites/pacman.png";

        physicObject.movement = new Point(0.5,0);

        this.physicObject.hitBox = new CircleHitBox(this.renderObject.pos.x, this.renderObject.pos.y, 8);
        if (this.physicObject.hitBox.getShape() instanceof Circle)
            System.out.println(((Circle) this.physicObject.hitBox.getShape()).getRadius());

       }

    @Override
    public void onCollisionEnter(PhysicObject collider) {
        System.out.println("COLLISION PACMAN");

        Random r = new Random();
        int nb = r.nextInt(2);
        System.out.println(nb);

        /*if (nb == 0) {
            this.physicObject.movement.x = 0.5;
            this.physicObject.movement.y = 0;
        } else if (nb == 1) {
            this.physicObject.movement.x = -0.5;
            this.physicObject.movement.y = 0;
        } else if (nb == 2) {
            this.physicObject.movement.x = 0;
            this.physicObject.movement.y = 0.5;
        } else if (nb == 3) {
            this.physicObject.movement.x = 0;
            this.physicObject.movement.y = -0.5;
        }*/


        this.physicObject.movement.x = 0;
        this.physicObject.movement.y = -0.5;
        /*if (this.physicObject.movement.y > 0) this.physicObject.movement.y = -0.5;
        else this.physicObject.movement.y = 0.5;*/
    }

    @Override
    public void update() {
        //System.out.println("I am Pac-Man and this method is called every frame, much cool !");
    }
}
