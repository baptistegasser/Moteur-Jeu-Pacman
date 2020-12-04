package fr.univ.pacman;

import fr.univ.engine.assets.AssetsLoader;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.core.entity.EntityBuilder;
import fr.univ.engine.core.entity.EntityFactory;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.hitbox.SquareHitBox;
import fr.univ.engine.render.texture.Animation;
import fr.univ.engine.render.texture.Texture;
import fr.univ.pacman.component.PacManLogic;
import fr.univ.pacman.component.ai.BlueGhostAIComponent;
import fr.univ.pacman.component.ai.OrangeGhostAIComponent;
import fr.univ.pacman.component.ai.PinkGhostAIComponent;
import fr.univ.pacman.component.ai.RedGhostAIComponent;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class GameFactory extends EntityFactory {

    /**
     * Size of each tile in the map
     */
    private final int TILE_SIZE = 16;

    private Point tilePos(Point charPos) {
        return new Point(TILE_SIZE * charPos.x - 216, TILE_SIZE * charPos.y - 240);
    }

    /**
     * Setup parameters for pacman
     * @param charPos
     * @return
     */
    @From('P')
    public Entity pacman(Point charPos) {
        ArrayList<Image> imageAnimated = new ArrayList<>();
        imageAnimated.add(AssetsLoader.loadImage("sprites/animation/pacmanWalk/pacmanWalk1.png"));
        imageAnimated.add(AssetsLoader.loadImage("sprites/animation/pacmanWalk/pacmanWalk2.png"));

        Animation animation = new Animation(imageAnimated,60,2,false);
        Texture texture = new Texture(16, 16, animation);
        //Texture texture = new Texture(16, 16, AssetsLoader.loadImage("sprites/pacman.png"));
        texture.setZIndex(10);
        return new EntityBuilder()
                .type(Type.PACMAN)
                .position(tilePos(charPos))
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                .with(new PacManLogic())
                .build();
    }

    /**
     * Setup parameters for common walls
     * @param charPos
     * @return
     */
    @From('1')
    public Entity wall(Point charPos) {
        return new EntityBuilder()
                .type(Type.WALL)
                .position(tilePos(charPos))
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
        Texture texture = new Texture(3, 3, AssetsLoader.loadImage("item/gomme.png"));
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
        Texture texture = new Texture(10, 10, AssetsLoader.loadImage("item/superGomme.png"));
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
        Texture texture = new Texture(10, 10, AssetsLoader.loadImage("item/superGommeArcEnCiel.png"));
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

    @From('S')
    public Entity spawnExit(Point charPos) {
        return new EntityBuilder()
                .type(Type.SPAWN_EXIT)
                .position(tilePos(charPos))
                .hitbox(new SquareHitBox(16))
                .build();
    }

    @From('R')
    public Entity redGhost(Point charPos) {
        Texture texture = new Texture(20, 20, AssetsLoader.loadImage("sprites/ghosts/redGhost.png"));
        texture.setZIndex(9);
        return new EntityBuilder()
                .type(Type.GHOST)
                .position(tilePos(charPos))
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                .with(new RedGhostAIComponent())
                .build();
    }

    @From('B')
    public Entity blueGhost(Point charPos) {
        Texture texture = new Texture(20, 20, AssetsLoader.loadImage("sprites/ghosts/blueGhost.png"));
        texture.setZIndex(9);
        return new EntityBuilder()
                .type(Type.GHOST)
                .position(tilePos(charPos))
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                .with(new BlueGhostAIComponent())
                .build();
    }

    @From('O')
    public Entity orangeGhost(Point charPos) {
        Texture texture = new Texture(20, 20, AssetsLoader.loadImage("sprites/ghosts/orangeGhost.png"));
        texture.setZIndex(9);
        return new EntityBuilder()
                .type(Type.GHOST)
                .position(tilePos(charPos))
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                .with(new OrangeGhostAIComponent())
                .build();
    }

    @From('I')
    public Entity pinkGhost(Point charPos) {
        Texture texture = new Texture(20, 20, AssetsLoader.loadImage("sprites/ghosts/pinkGhost.png"));
        texture.setZIndex(9);
        return new EntityBuilder()
                .type(Type.GHOST)
                .position(tilePos(charPos))
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                .with(new PinkGhostAIComponent())
                .build();
    }
}
