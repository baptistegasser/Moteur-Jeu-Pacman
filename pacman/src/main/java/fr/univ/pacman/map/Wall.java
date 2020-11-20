package fr.univ.pacman.map;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.hitbox.SquareHitBox;

public class Wall extends GameObject {
    public Wall(int posX, int posY) {
        renderObject.pos = new Point(posX, posY);
        renderObject.width = 16;
        renderObject.height = 16;

        this.physicObject.hitBox = new SquareHitBox(renderObject.pos.x-8 + Map.BACKGROUND_WIGHT, renderObject.pos.y-8 + Map.BACKGROUND_HEIGHT, renderObject.width);
    }
}
