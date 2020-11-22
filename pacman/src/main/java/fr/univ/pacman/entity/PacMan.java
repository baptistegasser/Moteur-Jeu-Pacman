package fr.univ.pacman.entity;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.io.IOEngine;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.PhysicEngine;
import fr.univ.engine.physic.PhysicObject;
import fr.univ.engine.physic.hitbox.SquareHitBox;
import javafx.scene.input.KeyCode;

/**
 * The class handling the logic of Pac-Man controlled by the player.
 */
public class PacMan extends GameObject {
    private enum DIR {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        NONE
    }

    private double speed = 0.5;
    private DIR nextDirection = DIR.NONE;

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
        if (IOEngine.getKey(KeyCode.UP)) nextDirection = DIR.UP;
        if (IOEngine.getKey(KeyCode.DOWN)) nextDirection = DIR.DOWN;
        if (IOEngine.getKey(KeyCode.LEFT)) nextDirection = DIR.LEFT;
        if (IOEngine.getKey(KeyCode.RIGHT)) nextDirection = DIR.RIGHT;
    }

    @Override
    public void fixedUpdate(double t, double dt) {
        if (nextDirection != DIR.NONE) {
            Point target = getPhysicObject().getHitBox().getPosition().copy();

            Vector vDir = new Vector(0, 0);
            switch (nextDirection) {
                case UP:
                    vDir.setY(-0.5);
                    break;
                case DOWN:
                    vDir.setY(0.5);
                    break;
                case LEFT:
                    vDir.setX(-0.5);
                    break;
                case RIGHT:
                    vDir.setX(0.5);
                    break;
            }
            target.x += vDir.x();
            target.y += vDir.y();

            if (PhysicEngine.isThereSolidCollision(getPhysicObject(), target)) {
                // Ignore is no place
            } else {
                getPhysicObject().direction = vDir;
                nextDirection = DIR.NONE;
            }
        }
    }
}
