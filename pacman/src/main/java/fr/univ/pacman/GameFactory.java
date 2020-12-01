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
import fr.univ.pacman.component.RedGhostAI;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class GameFactory extends EntityFactory<CharInfo> {

    private final int TILE_SIZE = 16;

    private Point tilePos(CharInfo info) {
        return new Point(TILE_SIZE * info.x() - 216, TILE_SIZE * info.y() - 240);
    }

    @From("P")
    public Entity pacman(CharInfo info) {
        ArrayList<Image> imageAnimated = new ArrayList<>();
        imageAnimated.add(AssetsLoader.loadImage("sprites/animation/pacmanWalk1.png"));
        imageAnimated.add(AssetsLoader.loadImage("sprites/animation/pacmanWalk2.png"));

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

    @From("1")
    public Entity wall(CharInfo info) {
        return new EntityBuilder()
                .type(Type.WALL)
                .position(tilePos(info))
                .hitbox(new SquareHitBox(16))
                .isSolid(true)
                .build();
    }

    @From("2")
    public Entity pac(CharInfo info) {
        Texture texture = new Texture(3, 3, AssetsLoader.loadImage("item/gomme.png"));
        return new EntityBuilder()
                .type(Type.PAC)
                .position(tilePos(info))
                .texture(texture)
                .hitbox(new SquareHitBox(3))
                .build();
    }

    @From("3")
    public Entity superpac(CharInfo info) {
        Texture texture = new Texture(10, 10, AssetsLoader.loadImage("item/superGomme.png"));
        return new EntityBuilder()
                .type(Type.SUPER_PAC)
                .position(tilePos(info))
                .texture(texture)
                .hitbox(new SquareHitBox(10))
                .build();
    }

    @From("4")
    public Entity rainbowpac(CharInfo info) {
        Texture texture = new Texture(10, 10, AssetsLoader.loadImage("item/superGommeArcEnCiel.png"));
        return new EntityBuilder()
                .type(Type.SUPER_RAINBOW_PAC)
                .position(tilePos(info))
                .texture(texture)
                .hitbox(new SquareHitBox(10))
                .build();
    }

    private Point ghostPos(CharInfo info) {
        return new Point(TILE_SIZE * info.x() - 214, TILE_SIZE * info.y() - 238);
    }

    @From("R")
    public Entity redGhost(CharInfo info) {
        Texture texture = new Texture(20, 20, AssetsLoader.loadImage("sprites/ghosts/redGhost.png"));
        return new EntityBuilder()
                .type(Type.GHOST)
                .position(ghostPos(info))
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                .with(new RedGhostAI())
                // TODO red ghost behavior component
                .build();
    }

    @From("B")
    public Entity blueGhost(CharInfo info) {
        Texture texture = new Texture(20, 20, AssetsLoader.loadImage("sprites/ghosts/blueGhost.png"));
        return new EntityBuilder()
                .type(Type.GHOST)
                .position(ghostPos(info))
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                // TODO blue ghost behavior component
                .build();
    }

    @From("O")
    public Entity orangeGhost(CharInfo info) {
        Texture texture = new Texture(20, 20, AssetsLoader.loadImage("sprites/ghosts/orangeGhost.png"));
        return new EntityBuilder()
                .type(Type.GHOST)
                .position(ghostPos(info))
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                // TODO orange ghost behavior component
                .build();
    }

    @From("I")
    public Entity pinkGhost(CharInfo info) {
        Texture texture = new Texture(20, 20, AssetsLoader.loadImage("sprites/ghosts/pinkGhost.png"));
        return new EntityBuilder()
                .type(Type.GHOST)
                .position(ghostPos(info))
                .texture(texture)
                .hitbox(new SquareHitBox(16))
                // TODO pink ghost behavior component
                .build();
    }
}
