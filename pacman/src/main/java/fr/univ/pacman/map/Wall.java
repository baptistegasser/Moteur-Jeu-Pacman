package fr.univ.pacman.map;

import fr.univ.engine.core.GameObject;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.hitbox.SquareHitBox;

public class Wall extends GameObject {
    public Wall(int x, int y) {
        super(x, y);
        setName("WALL");

        renderObject.width = 16;
        renderObject.height = 16;

        // The coordinates of square are on top left corner so subtracted half of tile_size
        this.physicObject.setHitBox(new SquareHitBox(renderObject.width));

        physicObject.getHitBox().setSolid(true);
    }
}
