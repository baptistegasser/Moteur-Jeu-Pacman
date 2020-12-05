package fr.univ.engine.core.entity;

import fr.univ.engine.math.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Load a level from a simple txt file.
 * This parse file considering that each characters represent an entity.
 * Entities are build with a provided {@link EntityFactory} implementation.
 * Note (x,y) represent the pos relative to the file, not the game.
 */
public class LevelLoader {
    /**
     * The level being loaded.
     */
    private Level level;
    /**
     * The factory used to create entities.
     */
    private final EntityFactory factory;
    /**
     * The position of the current char.
     */
    private Point charPos;

    public LevelLoader(EntityFactory factory) {
        this.factory = factory;
    }

    /**
     * Load a level from a file.
     *
     * @param mapStream a streaming containing the level data.
     * @return the created Level instance.
     */
    public Level load(InputStream mapStream) {
        this.level = new Level();
        this.charPos = new Point(0, 0);

        try (InputStreamReader isr = new InputStreamReader(mapStream);
             BufferedReader br = new BufferedReader(isr)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                parseLine(line);
                charPos.setX(0);
                charPos.incrementY();
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
            Entity e = factory.getEntity(c, charPos);

            if (e != null) {
                level.add(e);
            }

            charPos.incrementX();
        }
    }
}
