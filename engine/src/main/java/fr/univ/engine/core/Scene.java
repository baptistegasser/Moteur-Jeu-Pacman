package fr.univ.engine.core;

import java.util.ArrayList;

/**
 * A scene representing a game's level.
 */
public class Scene {
    private final ArrayList<GameObject> objects;

    public Scene() {
        this.objects = new ArrayList<>();
    }

    public ArrayList<GameObject> getObjects() {
        return objects;
    }

    public void add(GameObject e) {
        this.objects.add(e);
    }
}
