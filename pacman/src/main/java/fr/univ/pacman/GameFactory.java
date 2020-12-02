package fr.univ.pacman;

import fr.univ.engine.assets.AssetsLoader;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.core.entity.EntityBuilder;
import fr.univ.engine.core.entity.EntityFactory;
import fr.univ.engine.core.level.loader.CharInfo;
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

public class GameFactory extends EntityFactory<CharInfo> {

    /**
     * Size of each tile in the map
     */
    private final int TILE_SIZE = 16;

    private Point tilePos(CharInfo info) {
        return new Point(TILE_SIZE * info.x() - 216, TILE_SIZE * info.y() - 240);
    }

    /**
     * Setup parameters for pacman
     * @param info
     * @return
     */
    @From("P")
    public Entity pacman(CharInfo info) {
        ArrayList<Image> imageAnimated = new ArrayList<>();
        imageAnimated.add(AssetsLoader.loadImage("sprites/animation/pacmanWalk/pacmanWalk1.png"));
        imageAnimated.add(AssetsLoader.loadImage("sprites/animation/pacmanWalk/pacmanWalk2.png"));

        Animation animation = new Animation(imageAnimated,60,2,false);
        Texture texture = new Texture(16, 16, animation);
        //Texture texture = new Texture(16, 16, AssetsLoader.loadImage("sprites/pacman.png"));
        texture.setZIndex(10);
        return new EntityBuilder()
                .type(Type.PACMAN)
                .position(tilePos(info))
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                .with(new PacManLogic())
                .build();
    }

    /**
     * Setup parameters for common walls
     * @param info
     * @return
     */
    @From("1")
    public Entity wall(CharInfo info) {
        return new EntityBuilder()
                .type(Type.WALL)
                .position(tilePos(info))
                .hitbox(new SquareHitBox(16))
                .build();
    }

    /**
     * Setup parameters for unbreakable walls
     * @param info
     * @return
     */
    @From("7")
    public Entity greatwall(CharInfo info) {
        return new EntityBuilder()
                .type(Type.GREATWALL)
                .position(tilePos(info))
                .hitbox(new SquareHitBox(16))
                .build();
    }

    /**
     * Setup parameters for pacs
     * @param info
     * @return
     */
    @From("2")
    public Entity pac(CharInfo info) {
        Texture texture = new Texture(3, 3, AssetsLoader.loadImage("item/gomme.png"));
        return new EntityBuilder()
                .type(Type.PAC)
                .position(tilePos(info))
                .texture(texture)
                .hitbox(new SquareHitBox(3))
                .isSolid(false)
                .build();
    }

    /**
     * Setup parameters for superpacs
     * @param info
     * @return
     */
    @From("3")
    public Entity superpac(CharInfo info) {
        Texture texture = new Texture(10, 10, AssetsLoader.loadImage("item/superGomme.png"));
        return new EntityBuilder()
                .type(Type.SUPER_PAC)
                .position(tilePos(info))
                .texture(texture)
                .hitbox(new SquareHitBox(10))
                .isSolid(false)
                .build();
    }

    /**
     * Setup parameter for rainbowpacs
     * @param info
     * @return
     */
    @From("4")
    public Entity rainbowpac(CharInfo info) {
        Texture texture = new Texture(10, 10, AssetsLoader.loadImage("item/superGommeArcEnCiel.png"));
        return new EntityBuilder()
                .type(Type.SUPER_RAINBOW_PAC)
                .position(tilePos(info))
                .texture(texture)
                .hitbox(new SquareHitBox(10))
                .isSolid(false)
                .build();
    }

    /**
     * Setup parameter for the teleport
     * @param info
     * @return
     */
    @From("5")
    public Entity teleport(CharInfo info) {
        return new EntityBuilder()
                .type(Type.TELEPORT)
                .position(tilePos(info))
                .hitbox(new SquareHitBox(16))
                .isSolid(false)
                .build();
    }

    @From("S")
    public Entity spawnExit(CharInfo info) {
        return new EntityBuilder()
                .type(Type.SPAWN_EXIT)
                .position(tilePos(info))
                .hitbox(new SquareHitBox(16))
                .isSolid(false)
                .build();
    }

    @From("R")
    public Entity redGhost(CharInfo info) {
        Texture texture = new Texture(20, 20, AssetsLoader.loadImage("sprites/ghosts/redGhost.png"));
        texture.setZIndex(9);
        return new EntityBuilder()
                .type(Type.GHOST)
                .position(tilePos(info))
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                .with(new RedGhostAIComponent())
                .build();
    }

    @From("B")
    public Entity blueGhost(CharInfo info) {
        Texture texture = new Texture(20, 20, AssetsLoader.loadImage("sprites/ghosts/blueGhost.png"));
        texture.setZIndex(9);
        return new EntityBuilder()
                .type(Type.GHOST)
                .position(tilePos(info))
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                .with(new BlueGhostAIComponent())
                .build();
    }

    @From("O")
    public Entity orangeGhost(CharInfo info) {
        Texture texture = new Texture(20, 20, AssetsLoader.loadImage("sprites/ghosts/orangeGhost.png"));
        texture.setZIndex(9);
        return new EntityBuilder()
                .type(Type.GHOST)
                .position(tilePos(info))
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                .with(new OrangeGhostAIComponent())
                .build();
    }

    @From("I")
    public Entity pinkGhost(CharInfo info) {
        Texture texture = new Texture(20, 20, AssetsLoader.loadImage("sprites/ghosts/pinkGhost.png"));
        texture.setZIndex(9);
        return new EntityBuilder()
                .type(Type.GHOST)
                .position(tilePos(info))
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                .with(new PinkGhostAIComponent())
                .build();
    }
}
