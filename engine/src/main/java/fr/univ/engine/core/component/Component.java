package fr.univ.engine.core.component;

/**
 * Base component that should be implemented by any other entity's components.
 */
public interface Component {
    /**
     * Method called once every frame.
     */
    default void update() {}
}
