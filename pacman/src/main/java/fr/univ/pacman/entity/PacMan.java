package fr.univ.pacman.entity;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.io.IOEngine;
import fr.univ.engine.logging.LoggingEngine;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.hitbox.CircleHitBox;
import fr.univ.engine.physic.PhysicObject;
import fr.univ.engine.physic.hitbox.SquareHitBox;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;

import java.util.logging.Level;

/**
 * The class handling the logic of Pac-Man controlled by the player.
 */
public class PacMan extends GameObject {
    public PacMan(int x, int y) {
        super(x, y);
        setName("PAC-MAN");

        renderObject.width = 16;
        renderObject.height = 16;
        renderObject.zIndex = 10;
        renderObject.textureName = "sprites/pacman.png";

        physicObject.movement = new Point(0.5,0);

        this.physicObject.setHitBox(new SquareHitBox(this.renderObject.width));
       }

    @Override
    public void onCollisionEnter(PhysicObject collider) {
        if (collider.name().equals("WALL")) {
            getPhysicObject().movement = new Point(0, 0);
        }
    }

    @Override
    public void update() {
        Point dir = new Point(0, 0);

        if (IOEngine.getKey(KeyCode.UP)) dir.y = -0.5;
        if (IOEngine.getKey(KeyCode.DOWN)) dir.y = 0.5;
        if (IOEngine.getKey(KeyCode.LEFT)) dir.x = -0.5;
        if (IOEngine.getKey(KeyCode.RIGHT)) dir.x = 0.5;

        if(dir.x != 0 || dir.y != 0) this.physicObject.movement = dir;
    }
}
