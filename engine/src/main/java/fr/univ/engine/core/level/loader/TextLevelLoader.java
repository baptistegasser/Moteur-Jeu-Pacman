package fr.univ.engine.core.level.loader;

import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.core.entity.EntityFactory;
import fr.univ.engine.core.level.Level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
    public Level load(InputStream mapStream, EntityFactory<CharInfo> factory) {
        this.level = new Level();
        this.info = new CharInfo();
        this.factory = factory;

        try (InputStreamReader isr = new InputStreamReader(mapStream);
             BufferedReader br = new BufferedReader(isr)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                parseLine(line);
                info.x = 0;
                info.y += 1;
            }
        } catch (IOException ioe) {
            throw new RuntimeException("Failed to load map from", ioe);
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
