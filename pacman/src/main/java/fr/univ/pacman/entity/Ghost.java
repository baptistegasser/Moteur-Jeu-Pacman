package fr.univ.pacman.entity;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;

/**
 * The class handling the logic of Ghost.
 */
public class Ghost extends MobileEntity {
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

        movement = new Point(0,0);
    }
}
