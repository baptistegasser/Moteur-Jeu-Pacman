package fr.univ.pacman.entity;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.logging.LoggingEngine;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.PhysicEngine;
import fr.univ.engine.physic.PhysicObject;
import fr.univ.engine.physic.hitbox.SquareHitBox;
import fr.univ.engine.render.texture.Texture;
import fr.univ.engine.sound.SoundEngine;
import fr.univ.pacman.Game;

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

        Texture texture = new Texture(16, 16, Game.resolver.getImage("sprites/pacman.png"));
        texture.setZIndex(10);
        setTexture(texture);

        physicObject.direction = new Vector(0.5,0);

        this.physicObject.setHitBox(new SquareHitBox(16));
       }

    @Override
    public void onCollisionEnter(PhysicObject collider) {
        switch (collider.name()) {
            case "WALL":
                getPhysicObject().direction = new Vector(0, 0);
                break;
            case "GOMME":
                LoggingEngine.info("Pac-Man eat a Pac.");
                SoundEngine.staticPlay("sounds/eating_pac.wav");
                break;
            case "SUPER-GOMME":
                LoggingEngine.info("Pac-Man eat a super Pac !");
        }
        if (collider.name().startsWith("GHOST-")) {
            SoundEngine.staticPlay("sounds/pac_die.wav" );
            LoggingEngine.info("Got caught by a ghost !");
        }
    }

    @Override
    public void update() {
    }

    public void up() {
        nextDirection = DIR.UP;
    }

    public void down() {
        nextDirection = DIR.DOWN;
    }

    public void left() {
        nextDirection = DIR.LEFT;
    }

    public void right() {
        nextDirection = DIR.RIGHT;
    }

    @Override
    public void fixedUpdate(double t, double dt) {
        if (nextDirection != DIR.NONE) {
            Point target = getPhysicObject().getHitBox().getPosition().copy();

            Vector vDir = new Vector(0, 0);
            switch (nextDirection) {
                case UP:
                    vDir.setY(-speed);
                    break;
                case DOWN:
                    vDir.setY(speed);
                    break;
                case LEFT:
                    vDir.setX(-speed);
                    break;
                case RIGHT:
                    vDir.setX(speed);
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
