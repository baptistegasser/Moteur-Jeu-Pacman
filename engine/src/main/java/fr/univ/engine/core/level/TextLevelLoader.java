package fr.univ.engine.core.level;

import fr.univ.engine.core.Level;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.core.entity.EntityFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Level loader loading level from simple txt files.
 * This parse file considering that each characters represent
 * an entity positioned at pos (x,y).
 * Note (x,y) represent the pos relative to the file, not the game.
 */
public class TextLevelLoader implements LevelLoader<CharInfo> {
    /**
     * The level being loaded.
     */
    private Level level;
    /**
     * The factory used to create entities.
     */
    private EntityFactory<CharInfo> factory;
    /**
     * Informations on the current char.
     */
    private CharInfo info;
    
    @Override
    public Level load(File mapFile, EntityFactory<CharInfo> factory) {
        this.level = new Level();
        this.info = new CharInfo();
        this.factory = factory;

        try (FileReader fr = new FileReader(mapFile);
             BufferedReader br = new BufferedReader(fr)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                parseLine(line);
                info.x = 0;
                info.y += 1;
            }
        } catch (IOException ioe) {
            String msg = String.format("Failed to load map from '%s'", mapFile.getAbsolutePath());
            throw new RuntimeException(msg, ioe);
        }

        return level;
    }

    /**
     * Parse a line of characters, one by one.
     *
     * @param line the line of chars to parse.
     */
    private void parseLine(String line) {
        for (char c : line.toCharArray()) {
            Entity e = factory.getEntity(String.valueOf(c), info);

            if (e != null) {
                level.add(e);
            }

            info.x += 1;
        }
    }
}
