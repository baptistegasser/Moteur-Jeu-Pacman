package fr.univ.engine.core.entity;

import fr.univ.engine.core.level.loader.LevelInfo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Representation of a factory capable of producing {@link Entity}.
 * A factory should implement methods annotated with the {@link From}
 * annotation to define how to build an entity based on a string key value.
 */
public abstract class EntityFactory<T extends LevelInfo> {
    /**
     * The valid builder found in this instance.
     */
    private final Map<String, Method> builderMethods;

    /**
     * Constructor, build a list of valid builder methods.
     */
    public EntityFactory() {
        this.builderMethods = new HashMap<>();
        for (Method method : getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(From.class)
                    && method.canAccess(this)
                    && Entity.class.isAssignableFrom(method.getReturnType())
                    && method.getParameterCount() == 1
                    && LevelInfo.class.isAssignableFrom(method.getParameterTypes()[0])
            ) {
                From from = method.getAnnotation(From.class);
                builderMethods.put(from.value(), method);
            }
        }
    }

    /**
     * Get an entity from this key.
     * This will call a builder method that is annotated as such: {@code @From(key)}.
     * If no builder is defined for this key, this will return null.
     *
     * @param key the key identifying the entity.
     * @return a new entity instance or null.
     */
    public final Entity getEntity(String key, T info) {
        Method builder = this.builderMethods.get(key);
        if (builder == null)
            return null;
        try {
            return ((Entity) builder.invoke(this, info));
        } catch (Exception e) {
            // Don't handle exception it can come from the user factory failing.
            e.printStackTrace();
            return null;
        }
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface From {
        String value();
    }
}
