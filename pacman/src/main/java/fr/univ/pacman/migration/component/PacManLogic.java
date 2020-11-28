package fr.univ.pacman.migration.component;

import fr.univ.engine.core.component.Component;
import fr.univ.engine.logging.LoggingEngine;

import java.util.logging.Level;

public class PacManLogic implements Component {

    public void up() {
        LoggingEngine.log(Level.INFO, "Going up !");
    }

    public void down() {
        LoggingEngine.log(Level.INFO, "Going down !");
    }

    public void left() {
        LoggingEngine.log(Level.INFO, "Going left !");
    }

    public void right() {
        LoggingEngine.log(Level.INFO, "Going right !");
    }

    public void stop() {
        LoggingEngine.log(Level.INFO, "Stopping !");
    }

    public void hit() {
        LoggingEngine.log(Level.INFO, "Hit !");
    }
}
