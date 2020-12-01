package fr.univ.pacman.component;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import fr.univ.engine.core.component.Component;
import fr.univ.engine.core.component.TransformComponent;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.component.PhysicComponent;
import fr.univ.pacman.Type;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
        ArrayList<Boolean> move = whereCanIMove(transform);

        double horizontal = pacManPos.x - myPosition.x; // si négatif : pacman est plus à gauche
        double vertical = pacManPos.y - myPosition.y; // si négatif : pacman est plus haut
        double absVertical = Math.abs(vertical);
        double absHorizontal = Math.abs(horizontal);
        if(absHorizontal >= absVertical) {
            if (horizontal < 0) {
                if (move.get(0)) this.wantedDirection = new Vector(-0.5d, 0); // on va a gauche
                else if (move.get(1)) this.wantedDirection = new Vector(0.5d, 0); // on monte
                else if (vertical < 0) {
                    if (move.get(2)) this.wantedDirection = new Vector(0, -0.5d); // on monte
                } else if (vertical > 0) {
                    if (move.get(3)) this.wantedDirection = new Vector(0, 0.5d);
                }
            } else {
                if (move.get(2)) this.wantedDirection = new Vector(0, -0.5d); // on va a gauche
                else if (move.get(3)) this.wantedDirection = new Vector(0, 0.5d); // on monte
                else if (horizontal < 0) {
                    if (move.get(0)) this.wantedDirection = new Vector(-0.5d, 0); // on monte
                } else if (horizontal > 0) {
                    if (move.get(1)) this.wantedDirection = new Vector(0.5d, 0);
                }
            }
        }
        else {
            if (vertical > 0) {
                if (move.get(2)) this.wantedDirection = new Vector(-0.5d, 0); // on va a gauche
                else if (move.get(3)) this.wantedDirection = new Vector(0.5d, 0); // on monte
                else if (horizontal < 0) {
                    if (move.get(0)) this.wantedDirection = new Vector(0, -0.5d); // on monte
                } else if (horizontal > 0) {
                    if (move.get(1)) this.wantedDirection = new Vector(0, 0.5d);
                }
            } else {
                if (move.get(2)) this.wantedDirection = new Vector(0, -0.5d); // on va a gauche
                else if (move.get(3)) this.wantedDirection = new Vector(0, 0.5d); // on monte
                else if (vertical < 0) {
                    if (move.get(0)) this.wantedDirection = new Vector(-0.5d, 0); // on monte
                } else if (vertical > 0) {
                    if (move.get(1)) this.wantedDirection = new Vector(0.5d, 0);
                }
            }
        }



       /* if (absVertical <= absHorizontal && (move.get(0) || move.get(1))) { // Si on peux bouger verticalement et que la distance vertical est la meilleure
            // haut ou bas

            if (vertical < 0) {
                if(move.get(2)) this.wantedDirection = new Vector(0, -0.5d); // on monte
            }
            else {
                if(move.get(3)) this.wantedDirection = new Vector(0, 0.5d); // on descend
            }
        }
        else if (absHorizontal < absVertical && (move.get(2) || move.get(3))) { // Si on peux bouger verticalement et que la distance horizontale est l
            // gauche ou droite
            if (horizontal < 0) {
                if(move.get(0))this.wantedDirection = new Vector(-0.5d, 0); // on va a gauche
            }
            else{
                if(move.get(1)) this.wantedDirection = new Vector(0.5d, 0); // on va a droite
            }
        }
        else if(absVertical <= absHorizontal && (move.get(2) || move.get(3))) {
            if (horizontal < 0) {
                if(move.get(0))this.wantedDirection = new Vector(-0.5d, 0); // on va a gauche
            }
            else{
                if(move.get(1)) this.wantedDirection = new Vector(0.5d, 0); // on va a droite
            }
        }
        else if(absHorizontal < absVertical && (move.get(0) || move.get(1))) {
            if (vertical < 0) {
                if(move.get(2)) this.wantedDirection = new Vector(0, -0.5d); // on monte
            }
            else {
                if(move.get(3)) this.wantedDirection = new Vector(0, 0.5d); // on descend
            }
        }*/



        Point newPos = transform.position().copy();
        newPos.add(wantedDirection);
        physic.setDirection(wantedDirection);
        transform.setRotation(wantedRotation);

    }

    public ArrayList<Boolean> whereCanIMove(TransformComponent transform) {
        //TODO Refractor cette fonction DEGEULASSE
        ArrayList<Boolean> move = new ArrayList<>();
        Point newPos = transform.position().copy();
        newPos.add(new Vector(-0.5d, 0));
        if (getPhysics().canMoveTo(this.getEntity(), newPos)) move.add(Boolean.TRUE);
        else move.add(Boolean.FALSE);
        newPos.add(new Vector(0.5d, 0));
        if (getPhysics().canMoveTo(this.getEntity(), newPos)) move.add(Boolean.TRUE);
        else move.add(Boolean.FALSE);
        newPos.add(new Vector(0, -0.5d));
        if (getPhysics().canMoveTo(this.getEntity(), newPos)) move.add(Boolean.TRUE);
        else move.add(Boolean.FALSE);
        newPos.add(new Vector(0, 0.5d));
        if (getPhysics().canMoveTo(this.getEntity(), newPos)) move.add(Boolean.TRUE);
        else move.add(Boolean.FALSE);
        return move;
    }

}
