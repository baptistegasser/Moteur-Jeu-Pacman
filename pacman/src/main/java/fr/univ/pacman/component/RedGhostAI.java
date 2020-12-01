package fr.univ.pacman.component;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import fr.univ.engine.core.component.Component;
import fr.univ.engine.core.component.TransformComponent;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.component.PhysicComponent;
import fr.univ.pacman.Type;

import java.lang.reflect.Array;

/**
 * The red ghost is always chasing the playing
 */
public class RedGhostAI extends Component {
    boolean canMove = true;
    private boolean changeDir = false;
    private Vector wantedDirection;
    private double wantedRotation;

    public void up() {
        this.wantedDirection = new Vector(0, -0.5d);
        this.wantedRotation = 270;
        this.changeDir = true;
    }

    public void down() {
        this.wantedDirection = new Vector(0, 0.5d);
        this.wantedRotation = 90;
        this.changeDir = true;
    }

    public void left() {
        this.wantedDirection = new Vector(-0.5d, 0);
        this.wantedRotation = 180;
        this.changeDir = true;
    }

    public void right() {
        this.wantedDirection = new Vector(0.5d, 0);
        this.wantedRotation = 0;
        this.changeDir = true;
    }

    public void stop() {
        this.wantedDirection = new Vector(0, 0);
        this.changeDir = true;
    }


    @Override
    public void update() {

        PhysicComponent physic = getComponent(PhysicComponent.class);
        TransformComponent transform = getComponent(TransformComponent.class);
        Point myPosition = getComponent(TransformComponent.class).position();
        Point pacManPos = getLevel().getSingletonEntity(Type.PACMAN).getComponent(TransformComponent.class).position();

        //System.out.print("PAC MAN  : " + pacManPos.x + pacManPos.y);
        //System.out.println("MOI : " + myPosition.x +  myPosition.y);
        double xdiff = pacManPos.x - myPosition.x; // si négatif : pacman est plus à gauche
        double ydiff = pacManPos.y - myPosition.y; // si négatif : pacman est plus haut

        double absy = Math.abs(ydiff);
        double absx = Math.abs(xdiff);
        //System.out.println("absy : " + absy+ " absy x :"+ absx);

        if(canMove) {
            if (absy >= absx) {
                // haut ou bas
                //System.out.println("VERTICAL");
                if (xdiff < 0) this.wantedDirection = new Vector(-0.5d, 0); // on va a gauche
                else this.wantedDirection = new Vector(0.5d, 0); // on va a droite
            } else {
                //System.out.println("HORIZONTAL");
                // gauche ou droite
                if (ydiff < 0) this.wantedDirection = new Vector(0, -0.5d); // on monte
                else this.wantedDirection = new Vector(0, 0.5d); // on descend
            }
        }

        Point newPos = transform.position().copy();

        if (getPhysics().canMoveTo(this.getEntity(), newPos)) {
            System.out.println("i can move lmao");
            physic.setDirection(wantedDirection);
            transform.setRotation(wantedRotation);
            changeDir = false;
            canMove = false;
        }
        else {
            //System.out.println("BRUH");
            canMove = true;
        }

    }
}
