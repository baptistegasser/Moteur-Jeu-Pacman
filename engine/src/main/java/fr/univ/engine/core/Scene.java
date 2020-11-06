package fr.univ.engine.core;

import java.util.ArrayList;
import java.util.List;

/**
 * A scene representing a game's level.
 */
public class Scene {
    private final ArrayList<Entity> entities;

    public Scene() {
        this.entities = new ArrayList<>();
    }

    public Scene(List<Entity> entities) {
        this.entities = new ArrayList<>(entities);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void add(Entity e) {
        this.entities.add(e);
    }
}
