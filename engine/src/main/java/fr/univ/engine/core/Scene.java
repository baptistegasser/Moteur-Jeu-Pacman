package fr.univ.engine.core;

import fr.univ.engine.physic.PhysicObject;
import fr.univ.engine.render.RenderObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A scene representing a game's level.
 */
public class Scene {
    private final ArrayList<GameObject> objects;

    public Scene() {
        this.objects = new ArrayList<>();
    }

    /**
     * Add an object to the scene.
     *
     * @param e the object to add
     */
    public void add(GameObject e) {
        this.objects.add(e);
    }

    /**
     * @return the list containing the physic component of each GameObject
     */
    public List<PhysicObject> getPhysicObjets() {
        return objects.parallelStream()
                .map(gameObject -> gameObject.physicObject)
                .collect(Collectors.toList());
    }

    /**
     * @return the list containing the render component of each GameObject
     */
    public List<RenderObject> getRenderObjets() {
        return objects.parallelStream()
                .map(gameObject -> gameObject.renderObject)
                .collect(Collectors.toList());
    }
}
