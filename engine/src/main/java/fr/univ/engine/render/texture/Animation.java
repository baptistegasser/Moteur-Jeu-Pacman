package fr.univ.engine.render.texture;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Animation {
    /**
     * The list of image for animation
     */
    private ArrayList<Image> imageAnimate;

    /**
     * The current image in the animation
     */
    private Image currentImage;

    /**
     * The number of a current frame
     */
    private int frame;

    /**
     * number of frame in animation
     */
    private int nbFrame;

    /**
     * The animation speed
     */
    private int speed;

    /**
     * The time when the current frame is charge
     */
    private long lastTimeFrame;

    /**
     * Animation is infinite
     */
    private boolean stopAnimation;

    public Animation(ArrayList<Image> imageAnimate, int speed, int nbFrame, boolean stopAnimation) {
        if (imageAnimate.size() == 0) {
            System.out.println("Liste d'animation vide");
            return;
        }
        this.imageAnimate = imageAnimate;
        this.currentImage = imageAnimate.get(0);

        lastTimeFrame = System.currentTimeMillis();

        this.speed = speed;
        this.nbFrame = nbFrame;
        this.frame = 0;
        this.stopAnimation = stopAnimation;
    }

    /**
     * Go to the next image on the animation
     */
    public void nextFrame() {
        if (lastTimeFrame+speed < System.currentTimeMillis()) {
            if (frame >= nbFrame) {
                frame = 0;
                if (stopAnimation) {
                    return;
                }
            }
            currentImage = imageAnimate.get(frame);
            frame+=1;
            lastTimeFrame = System.currentTimeMillis();
        }
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public int getNbFrame() {
        return nbFrame;
    }

    public int getSpeed() {
        return speed;
    }
}
