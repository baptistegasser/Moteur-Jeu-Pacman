package fr.univ.pacman.entity;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.io.IoEngine;
import fr.univ.engine.logging.LoggingEngine;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.hitbox.CircleHitBox;
import fr.univ.engine.physic.PhysicObject;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;

import java.util.logging.Level;

/**
 * The class handling the logic of Pac-Man controlled by the player.
 */
public class PacMan extends GameObject {
    public PacMan(int posX, int posY) {
        LoggingEngine.enableLogging(PacMan.class);
        renderObject.pos.x = posX;
        renderObject.pos.y = posY;
        renderObject.width = 16;
        renderObject.height = 16;
        renderObject.zIndex = 10;
        renderObject.textureName = "sprites/pacman.png";

        physicObject.movement = new Point(0.5,0);

        this.physicObject.hitBox = new CircleHitBox(this.renderObject.pos.x, this.renderObject.pos.y, this.renderObject.width/2);
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
        if (IoEngine.getKeyDown(KeyCode.A)) {
            LoggingEngine.log(Level.INFO, "Key A started press");
        }
        if (IoEngine.getKey(KeyCode.A)) {
            LoggingEngine.log(Level.INFO, "Key A is pressed");
        }
        if (IoEngine.getKeyUp(KeyCode.A)) {
            LoggingEngine.log(Level.INFO, "Key A was released");
        }
    }
}
