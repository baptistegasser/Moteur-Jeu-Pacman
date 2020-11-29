package fr.univ.engine.core.level.loader;

import fr.univ.engine.core.entity.EntityFactory;
import fr.univ.engine.core.level.Level;

import java.io.File;

/**
 * A level loader load a level from a file using an {@link EntityFactory}.
 */
public interface LevelLoader<T extends LevelInfo> {
    /**
     * Load a level from a file.
     *
     * @param mapFile the file storing the level data.
     * @param factory the factory used to create entities from the level datas.
     * @return the created Level instance.
     */
    Level load(File mapFile, EntityFactory<T> factory);
}
