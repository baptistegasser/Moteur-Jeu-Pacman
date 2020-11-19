package fr.univ.engine.physic;

import javafx.scene.shape.Shape;

public abstract class HitBox {
    double posX;
    double posY;
    double wight;
    Shape shape;

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

    public abstract void updateShape();
}
