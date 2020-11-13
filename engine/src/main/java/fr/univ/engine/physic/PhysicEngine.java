package fr.univ.engine.physic;

public class PhysicEngine {

    public void physicObject(PhysicObject o) {
        switch (o.dir) {
            case 0:
                o.pos.x += o.velocity;
                break;
            case 1:
                o.pos.y += o.velocity;
                break;
            case 2:
                o.pos.x -= o.velocity;
                break;
            case 3:
                o.pos.y -= o.velocity;
                break;
            default:
                break;
        }
    }
}
