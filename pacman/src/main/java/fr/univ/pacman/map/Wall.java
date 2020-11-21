package fr.univ.pacman.map;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.hitbox.SquareHitBox;

public class Wall extends GameObject {
    public Wall(int posX, int posY) {
        renderObject.pos = new Point(posX, posY);
        renderObject.width = 16;
        renderObject.height = 16;

        // The coordinates of square are on top left corner so subtracted half of tile_size
        this.physicObject.hitBox = new SquareHitBox(renderObject.pos.x - (Map.TILE_SIZE/2), renderObject.pos.y - (Map.TILE_SIZE/2), renderObject.width);
    }
}
