package fr.univ.pacman.entity;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.hitbox.CircleHitBox;
import fr.univ.engine.physic.PhysicObject;
import fr.univ.pacman.map.Map;
import javafx.scene.shape.Circle;

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

        this.physicObject.hitBox = new CircleHitBox(this.renderObject.pos.x + Map.BACKGROUND_WIGHT, this.renderObject.pos.y + Map.BACKGROUND_HEIGHT, 8);
        if (this.physicObject.hitBox.getShape() instanceof Circle)
            System.out.println(((Circle) this.physicObject.hitBox.getShape()).getRadius());
       }

    @Override
    public void onCollisionEnter(PhysicObject collider) {
        // Permet de RollBack lors d'une collision dans le précédente position //TODO A déplacer surement
        this.physicObject.getPos().x -= this.physicObject.movement.x;
        this.physicObject.getPos().y -= this.physicObject.movement.y;

        this.getPhysicObject().hitBox.setPosX(this.getPhysicObject().hitBox.getPosX() - this.getPhysicObject().movement.x);
        this.getPhysicObject().hitBox.setPosY(this.getPhysicObject().hitBox.getPosY() - this.getPhysicObject().movement.y);

        //Simple pattern de déplacement pour PACMAN
        if (this.physicObject.movement.x > 0) {
            this.physicObject.movement.x = 0;
            this.physicObject.movement.y = -0.5;
        } else if (this.physicObject.movement.x < 0) {
            this.physicObject.movement.x = 0;
            this.physicObject.movement.y = 0.5;
        } else if (this.physicObject.movement.y > 0) {
            this.physicObject.movement.x = 0.5;
            this.physicObject.movement.y = 0;
        } else if (this.physicObject.movement.y < 0) {
            this.physicObject.movement.x = -0.5;
            this.physicObject.movement.y = 0;
        }

    }

    @Override
    public void update() {
        //System.out.println("I am Pac-Man and this method is called every frame, much cool !");
    }
}
