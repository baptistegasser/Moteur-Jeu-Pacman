package fr.univ.engine.core;

import java.util.ArrayList;
import java.util.List;

/**
 * A scene representing a game's level.
 */
public class Scene {
    private final ArrayList<GameObject> objects;

    public static int tile_size;

    public Scene(int t) {
        this.objects = new ArrayList<>();
        tile_size = t;
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
     * Return the list of {@code GameObject} in the scene, all cast as another type.
     * NOTE: NO CHECK ARE DONE BEFORE CASTING.
     *
     * @param <T> the target type of the list
     * @return a list containing the casted objects
     * @throws ClassCastException if the target class is not a parent class or an
     *                            implemented interface of {@link GameObject}
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> objects() {
        return (List<T>) this.objects;
    }
}
