package fr.univ.pacman;

/**
 * The Pac-Man entities types.
 */
public enum Type {
    PACMAN, // Pacman
    GHOST, // Ghosts
    PAC, // A pac
    SUPER_PAC, // Pac which allow pacman to eat ghost
    SUPER_RAINBOW_PAC, // Pac which allow pacman to destroy walls
    WALL, // Walls
    GREATWALL, //Walls around the map, unbreakable
    TELEPORT, // Teleport
    SPAWN_EXIT, // Exit of ghost spawn
    GHOST_BASE, // Ghost area (their spawn)
    CHERRY, // Bonus + 200
    STRAWBERRY, // Bonus + 500
    ORANGE // Bonus + 1000
}
