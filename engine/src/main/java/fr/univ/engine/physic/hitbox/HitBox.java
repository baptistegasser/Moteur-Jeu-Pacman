package fr.univ.engine.physic.hitbox;

import javafx.scene.shape.Shape;

/**
 * Represent the HitBox of object
 */
public abstract class HitBox implements Intersect {
    /**
     * Center Pos
     */
    protected double posX;
    protected double posY;
    protected double wight;
    protected Shape shape;

    public HitBox(double posX, double posY, double wight) {
        this.posX = posX;
        this.posY = posY;
        this.wight = wight;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getWight() {
        return wight;
    }

    public void setWight(double wight) {
        this.wight = wight;
    }

    public Shape getShape() {
        return shape;
    }

    /**
     * Function permit to modifie HitBox
     */
    public abstract void updateShape();
}
