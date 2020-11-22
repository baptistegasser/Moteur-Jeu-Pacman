package fr.univ.pacman.entity;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.io.IOEngine;
import fr.univ.engine.logging.LoggingEngine;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.PhysicObject;
import fr.univ.engine.physic.hitbox.SquareHitBox;
import javafx.scene.input.KeyCode;

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

        physicObject.direction = new Vector(0.5,0);

        this.physicObject.setHitBox(new SquareHitBox(this.renderObject.width));
       }

    @Override
    public void onCollisionEnter(PhysicObject collider) {
        if (collider.name().equals("WALL")) {
            getPhysicObject().direction = new Vector(0, 0);
        }
    }

    @Override
    public void update() {
        Vector dir = new Vector(0, 0);

        if (IOEngine.getKey(KeyCode.UP)) dir.setY(-0.5);
        if (IOEngine.getKey(KeyCode.DOWN)) dir.setY(0.5);
        if (IOEngine.getKey(KeyCode.LEFT)) dir.setX(-0.5);
        if (IOEngine.getKey(KeyCode.RIGHT)) dir.setX(0.5);

        // Only update if we actually moved
        if(dir.magnitude() != 0) this.physicObject.direction = dir;
    }

    @Override
    public void fixedUpdate(double t, double dt) {
        LoggingEngine.log(Level.INFO, "Physic go brr");
    }
}
