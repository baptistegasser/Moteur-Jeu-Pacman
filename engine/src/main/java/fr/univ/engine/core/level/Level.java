package fr.univ.engine.core.level;

import fr.univ.engine.core.component.Component;
import fr.univ.engine.core.entity.Entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represent a game level.
 */
public class Level {
    /**
     * The entities present in this level.
     */
    private final List<Entity> entities;
    /**
     * A HashSet that contains the entities for a fast contains() check.
     */
    private final HashSet<Entity> entitiesSet;

    public Level() {
        entities = new ArrayList<>();
        entitiesSet = new HashSet<>();
    }

    /**
     * Add an entity to the level.
     *
     * @param entity the instance to add.
     */
    public void add(Entity entity) {
        if (!entitiesSet.contains(entity)) {
            entities.add(entity);
            entitiesSet.add(entity);
        }
    }

    /**
     * Remove an entity from the world.
     *
     * @param entity the instance to remove.
     */
    public void destroyEntity(Entity entity) {
        // contains() is so fast on HashSet that it's cost is lower than blindly calling remove()
        if (entitiesSet.contains(entity)) {
            entities.remove(entity);
            entitiesSet.remove(entity);
        }
    }

    /**
     * Get an entity that is unique by it's type.
     * If multiple entities have the same type, only the first
     * one will be returned by the method.
     *
     * @param type the wanted type.
     * @return a matching entity or null if none match.
     */
    public Entity getSingletonEntity(Object type) {
        for (Entity entity : entities) {
            if (entity.type().equals(type)) {
                return entity;
            }
        }

        return null;
    }

    /**
     * Get all entities.
     *
     * @return the list of entities.
     */
    public List<Entity> getEntities() {
        return entities;
    }

    /**
     * Get all entities with the matching type.
     *
     * @param type the wanted type.
     * @return a list of matching entities or an empty list.
     */
    public List<Entity> getEntities(Object type) {
        return entities.stream()
                .filter(entity -> type.equals(entity.type()))
                .collect(Collectors.toList());
    }

    /**
     * Return all entities with the specified component.
     *
     * @param componentClass the type of the wanted component
     * @return the matching entities.
     */
    public List<Entity> getEntitiesWithComponent(Class<? extends Component> componentClass) {
        return entities.stream()
                .filter(entity -> entity.hasComponent(componentClass))
                .collect(Collectors.toList());
    }
}
