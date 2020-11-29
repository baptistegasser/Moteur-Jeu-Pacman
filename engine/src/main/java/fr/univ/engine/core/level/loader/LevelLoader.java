package fr.univ.engine.core.level.loader;

import fr.univ.engine.core.entity.EntityFactory;
import fr.univ.engine.core.level.Level;

import java.io.InputStream;

/**
 * A level loader load a level from a file using an {@link EntityFactory}.
 */
public interface LevelLoader<T extends LevelInfo> {
    /**
     * Load a level from a file.
     *
     * @param mapStream a streaming containing the level data.
     * @param factory the factory used to create entities from the level datas.
     * @return the created Level instance.
     */
    Level load(InputStream mapStream, EntityFactory<T> factory);
}
