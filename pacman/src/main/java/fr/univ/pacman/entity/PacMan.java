package fr.univ.pacman.entity;

import fr.univ.engine.core.Entity;
import fr.univ.engine.core.math.Point;
import fr.univ.engine.core.math.Rect;
import fr.univ.engine.render.texture.Textured;

public class PacMan extends Entity implements Textured {

    public PacMan() {
        super(new Point(0, 32), new Rect(16, 16));
        setZIndex(10);
    }

    @Override
    public String getTexture() {
        return "sprites/pacman.png";
    }
}
