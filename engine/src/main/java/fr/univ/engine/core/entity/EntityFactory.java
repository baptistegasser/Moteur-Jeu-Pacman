package fr.univ.engine.core.entity;

import fr.univ.engine.math.Point;

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
 * <p>
 * An entity factory implementation should declare methods that take a
 * Point as only argument, return an entity and are annotated with the
 * {@link From} annotation.
 * <p>
 * Example, this method is valid and will be called for char 'A':
 * <pre>
 *     {@literal @}From('A')
 *     public void myFunc(Point charPos) {
 *         return appropriateEntity();
 *     }
 * </pre>
 */
public abstract class EntityFactory {
    /**
     * The valid builder found in this instance.
     */
    private final Map<Character, Method> builderMethods;

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
                    && Point.class.isAssignableFrom(method.getParameterTypes()[0])
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
    public final Entity getEntity(char character, Point charPos) {
        Method builder = this.builderMethods.get(character);
        if (builder == null)
            return null;
        try {
            return ((Entity) builder.invoke(this, charPos));
        } catch (Exception e) {
            // Don't handle exception it can come from the user factory failing.
            e.printStackTrace();
            return null;
        }
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface From {
        char value();
    }
}
