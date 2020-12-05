package fr.univ.pacman;

import fr.univ.engine.assets.AssetsLoader;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.core.entity.EntityBuilder;
import fr.univ.engine.core.entity.EntityFactory;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.hitbox.SquareHitBox;
import fr.univ.engine.render.RenderComponent;
import fr.univ.engine.render.texture.ISprite;
import fr.univ.engine.render.texture.Texture;
import fr.univ.pacman.component.PacManLogic;
import fr.univ.pacman.component.ai.BlueGhostAIComponent;
import fr.univ.pacman.component.ai.OrangeGhostAIComponent;
import fr.univ.pacman.component.ai.PinkGhostAIComponent;
import fr.univ.pacman.component.ai.RedGhostAIComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameFactory extends EntityFactory {

    /**
     * Size of each tile in the map
     */
    private final int TILE_SIZE = 16;

    private Point tilePos(Point charPos) {
        return new Point(TILE_SIZE * charPos.xValue() - 216, TILE_SIZE * charPos.yValue() - 240);
    }

    /**
     * Setup parameters for pacman
     * @param charPos
     * @return
     */
    @From('P')
    public Entity pacman(Point charPos) {
        Texture texture = new Texture(16, 16, 10);
        texture.addSprite("walking", AssetsLoader.loadAnimation(100, Arrays.asList("sprites/animation/pacmanWalk/pacmanWalk1.png", "sprites/animation/pacmanWalk/pacmanWalk2.png")));

        texture.addSprite("super", AssetsLoader.loadAnimation(100, Arrays.asList("sprites/animation/pacmanWalk/super_open.png", "sprites/animation/pacmanWalk/super_close.png")));

        List<String> framesName = new ArrayList<>();
        for (int i = 1; i < 12; i++) framesName.add("sprites/animation/pacmanDeath/pacmanDeath" + i + ".png");
        texture.addSprite("death", AssetsLoader.loadAnimation(70, framesName));

        texture.setCurrentChannel("walking");
        return new EntityBuilder()
                .type(Type.PACMAN)
                .position(tilePos(charPos))
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                .with(new PacManLogic())
                .speed(0.5)
                .build();
    }

    /**
     * Setup parameters for common walls
     * @param charPos
     * @return
     */
    @From('1')
    public Entity wall(Point charPos) {
        Texture texture = new Texture(16, 16);
        texture.addSprite("destroyed", AssetsLoader.loadSprite("item/black.png"));
        texture.addSprite("none", ISprite.NONE);
        texture.setCurrentChannel("none");

        return new EntityBuilder()
                .type(Type.WALL)
                .position(tilePos(charPos))
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                .isSolid(true)
                .build();
    }

    /**
     * Setup parameters for unbreakable walls
     * @param charPos
     * @return
     */
    @From('7')
    public Entity greatwall(Point charPos) {
        return new EntityBuilder()
                .type(Type.GREATWALL)
                .position(tilePos(charPos))
                .hitbox(new SquareHitBox(16))
                .isSolid(true)
                .build();
    }

    /**
     * Setup parameters for pacs
     * @param charPos
     * @return
     */
    @From('2')
    public Entity pac(Point charPos) {
        Texture texture = new Texture(3, 3, AssetsLoader.loadSprite("item/gomme.png"));
        return new EntityBuilder()
                .type(Type.PAC)
                .position(tilePos(charPos))
                .texture(texture)
                .hitbox(new SquareHitBox(3))
                .build();
    }

    /**
     * Setup parameters for superpacs
     * @param charPos
     * @return
     */
    @From('3')
    public Entity superpac(Point charPos) {
        Texture texture = new Texture(10, 10, AssetsLoader.loadSprite("item/superGomme.png"));
        return new EntityBuilder()
                .type(Type.SUPER_PAC)
                .position(tilePos(charPos))
                .texture(texture)
                .hitbox(new SquareHitBox(10))
                .build();
    }

    /**
     * Setup parameter for rainbowpacs
     * @param charPos
     * @return
     */
    @From('4')
    public Entity rainbowpac(Point charPos) {
        Texture texture = new Texture(10, 10, AssetsLoader.loadSprite("item/superGommeArcEnCiel.png"));
        return new EntityBuilder()
                .type(Type.SUPER_RAINBOW_PAC)
                .position(tilePos(charPos))
                .texture(texture)
                .hitbox(new SquareHitBox(10))
                .build();
    }

    /**
     * Setup parameter for the teleport
     * @param charPos
     * @return
     */
    @From('5')
    public Entity teleport(Point charPos) {
        return new EntityBuilder()
                .type(Type.TELEPORT)
                .position(tilePos(charPos))
                .hitbox(new SquareHitBox(16))
                .build();
    }

    /**
     * Setup parameter for the spawn exit
     * @param charPos
     * @return
     */
    @From('S')
    public Entity spawnExit(Point charPos) {
        return new EntityBuilder()
                .type(Type.SPAWN_EXIT)
                .position(tilePos(charPos))
                .hitbox(new SquareHitBox(16))
                .build();
    }

    /**
     * Setup parameter for the ghost base
     * @param charPos
     * @return
     */
    @From('G')
    public Entity ghostBase(Point charPos) {
        return new EntityBuilder()
                .type(Type.GHOST_BASE)
                .position(tilePos(charPos))
                .hitbox(new SquareHitBox(16))
                .build();
    }

    /**
     * Setup parameter or the red ghost
     * @param charPos
     * @return
     */
    @From('R')
    public Entity redGhost(Point charPos) {
        Texture texture = createGhostTexture("red");
        texture.setCurrentChannel("up");
        return new EntityBuilder()
                .type(Type.GHOST)
                .position(tilePos(charPos))
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                .with(new RedGhostAIComponent())
                .speed(0.5)
                .build();
    }

    /**
     * Setup parameter for the blue ghost
     * @param charPos
     * @return
     */
    @From('B')
    public Entity blueGhost(Point charPos) {
        Texture texture = createGhostTexture("blue");
        texture.setCurrentChannel("up");
        return new EntityBuilder()
                .type(Type.GHOST)
                .position(tilePos(charPos))
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                .with(new BlueGhostAIComponent())
                .speed(0.5)
                .build();
    }

    /**
     * Setup the orange ghost
     * @param charPos
     * @return
     */
    @From('O')
    public Entity orangeGhost(Point charPos) {
        Texture texture = createGhostTexture("orange");
        texture.setCurrentChannel("up");
        return new EntityBuilder()
                .type(Type.GHOST)
                .position(tilePos(charPos))
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                .with(new OrangeGhostAIComponent())
                .speed(0.5)
                .build();
    }

    /**
     * Setup the pink ghost
     * @param charPos
     * @return
     */
    @From('I')
    public Entity pinkGhost(Point charPos) {
        Texture texture = createGhostTexture("pink");
        texture.setCurrentChannel("up");
        return new EntityBuilder()
                .type(Type.GHOST)
                .position(tilePos(charPos))
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                .with(new PinkGhostAIComponent())
                .speed(0.5)
                .build();
    }

    /**
     * Setup the textures for all the ghosts : eyes, afraid, dead ...
     * @param color
     * @return
     */
    private Texture createGhostTexture(String color) {
        Texture texture = new Texture(20, 20, 9);
        texture.addSprite("up", AssetsLoader.loadSprite("sprites/ghosts/"+color+"GhostUp.png"));
        texture.addSprite("down", AssetsLoader.loadSprite("sprites/ghosts/"+color+"GhostDown.png"));
        texture.addSprite("left", AssetsLoader.loadSprite("sprites/ghosts/"+color+"GhostLeft.png"));
        texture.addSprite("right", AssetsLoader.loadSprite("sprites/ghosts/"+color+"GhostRight.png"));

        texture.addSprite("afraid", AssetsLoader.loadSprite("sprites/ghosts/afraidGhost.png"));

        texture.addSprite("dead_up", AssetsLoader.loadSprite("sprites/ghosts/deadGhostUp.png"));
        texture.addSprite("dead_down", AssetsLoader.loadSprite("sprites/ghosts/deadGhostDown.png"));
        texture.addSprite("dead_left", AssetsLoader.loadSprite("sprites/ghosts/deadGhostLeft.png"));
        texture.addSprite("dead_right", AssetsLoader.loadSprite("sprites/ghosts/deadGhostRight.png"));

        return texture;
    }
}
