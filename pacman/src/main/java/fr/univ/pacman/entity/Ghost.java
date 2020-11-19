package fr.univ.pacman.entity;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.CircleHitBox;
import fr.univ.engine.physic.PhysicObject;
import javafx.scene.shape.Circle;

/**
 * The class handling the logic of Ghost.
 */
public class Ghost extends GameObject {
    public Ghost(int posX, int posY, String color) {
        renderObject.pos.x = posX;
        renderObject.pos.y = posY;
        renderObject.width = 22;
        renderObject.height = 22;
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

        physicObject.movement = new Point(0,0);

        //this.physicObject.shape = new Circle(this.renderObject.pos.x, this.renderObject.pos.y, this.renderObject.width/2);
        this.physicObject.hitBox = new CircleHitBox(this.renderObject.pos.x, this.renderObject.pos.y, this.renderObject.width/2);
    }

    @Override
    public void onCollisionEnter(PhysicObject collider) {
        //this.physicObject.movement.x = 0;
        //this.physicObject.movement.y = 0.5;
    }
}
