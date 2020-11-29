package fr.univ.engine.physic.hitbox;

/**
 * A square hitbox implementation.
 */
// Third grade geometry: a square is a rectangle.
public class SquareHitBox extends RectangleHitBox {

    public SquareHitBox(double size) {
        super(size, size);
    }
}
