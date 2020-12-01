package fr.univ.pacman;

import fr.univ.engine.assets.AssetsLoader;
import fr.univ.engine.core.component.TransformComponent;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.core.entity.EntityBuilder;
import fr.univ.engine.core.level.Level;
import fr.univ.engine.core.config.Config;
import fr.univ.engine.core.GameApplication;
import fr.univ.engine.core.level.loader.TextLevelLoader;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.PhysicComponent;
import fr.univ.engine.render.component.RenderComponent;
import fr.univ.engine.render.texture.Animation;
import fr.univ.engine.render.texture.Texture;
import fr.univ.pacman.component.PacManLogic;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

import static fr.univ.pacman.Type.*;

public class PacMan extends GameApplication {

    private long lastSuperPower = 0L;

    @Override
    protected void config(Config config) {
        config.title = "Pac-Man";
        config.version = "2.0";
        config.width = 510;
        config.height = 620;
        config.resizable = false;
        config.displayFPS = true;
    }

    @Override
    protected void initGame() {
        globalVars().put("score", 0);
        globalVars().put("lives", 3);

        uiEngine().clear();
        AssetsLoader.loadFont("PressStart2P.ttf");
        uiEngine().display(AssetsLoader.loadView("Overlay.fxml"));
        uiEngine().display(AssetsLoader.loadView("Menu.fxml"));
        soundEngine().playClip("intro.wav", 0.05);

        loadLevel();

        PacManLogic pacmanLogic = getLevel().getSingletonEntity(Type.PACMAN).getComponent(PacManLogic.class);
        IOEngine().onKeyPressed(KeyCode.UP, pacmanLogic::up);
        IOEngine().onKeyPressed(KeyCode.DOWN, pacmanLogic::down);
        IOEngine().onKeyPressed(KeyCode.LEFT, pacmanLogic::left);
        IOEngine().onKeyPressed(KeyCode.RIGHT, pacmanLogic::right);
        IOEngine().onKeyPressed(KeyCode.ESCAPE, () -> {
            pause();
            uiEngine().display(AssetsLoader.loadView("Pause.fxml"));
        });

        pacmanLogic.setCanMove(true);

        physicEngine().onCollision(PACMAN, GHOST, (e1, e2) -> {
            globalVars().put("lives", globalVars().getInt("lives")-1);

            soundEngine().playClip("pac_die.wav");
            pacmanLogic.stop();
            pacmanLogic.hit();
            pacmanLogic.setCanMove(false);

            getLevel().getSingletonEntity(Type.PACMAN).getComponent(TransformComponent.class).setRotation(0);
            getLevel().getSingletonEntity(Type.PACMAN).getComponent(RenderComponent.class).setForAnimator(true);

            ArrayList<Image> animationDeath = new ArrayList<>();
            animationDeath.add(AssetsLoader.loadImage("sprites/animation/pacmanDeath/pacmanDeath1.png"));
            animationDeath.add(AssetsLoader.loadImage("sprites/animation/pacmanDeath/pacmanDeath2.png"));
            animationDeath.add(AssetsLoader.loadImage("sprites/animation/pacmanDeath/pacmanDeath3.png"));
            animationDeath.add(AssetsLoader.loadImage("sprites/animation/pacmanDeath/pacmanDeath4.png"));
            animationDeath.add(AssetsLoader.loadImage("sprites/animation/pacmanDeath/pacmanDeath5.png"));
            animationDeath.add(AssetsLoader.loadImage("sprites/animation/pacmanDeath/pacmanDeath6.png"));
            animationDeath.add(AssetsLoader.loadImage("sprites/animation/pacmanDeath/pacmanDeath7.png"));
            animationDeath.add(AssetsLoader.loadImage("sprites/animation/pacmanDeath/pacmanDeath8.png"));
            animationDeath.add(AssetsLoader.loadImage("sprites/animation/pacmanDeath/pacmanDeath9.png"));
            animationDeath.add(AssetsLoader.loadImage("sprites/animation/pacmanDeath/pacmanDeath10.png"));
            animationDeath.add(AssetsLoader.loadImage("sprites/animation/pacmanDeath/pacmanDeath11.png"));

            Animation animation = new Animation(animationDeath, 70, 11, true);

            getLevel().getSingletonEntity(Type.PACMAN).getComponent(RenderComponent.class).getTexture().setAnimation(animation);
            animatedPause();

            if (globalVars().getInt("lives") <= 0) {
                pause();
                uiEngine().display(AssetsLoader.loadView("GameOver.fxml"));
            }
        });
        physicEngine().onCollision(PACMAN, GREATWALL, (e1, e2) -> pacmanLogic.stop());
        physicEngine().onCollision(PACMAN, WALL, (e1, e2) -> {
            if(!e1.getComponent(PhysicComponent.class).getHitBox().isSolid()) {
                Texture texture = new Texture(16, 16, AssetsLoader.loadImage("item/black.png"));
                texture.setZIndex(1);
                e2.getComponent(RenderComponent.class).setTexture(texture);
                e2.getComponent(PhysicComponent.class).getHitBox().setSolid(false);
            }
        });

        physicEngine().onCollision(PACMAN, PAC, (e1, e2) -> {
            if(!e1.getComponent(PhysicComponent.class).getHitBox().isSolid()) {
                if(System.currentTimeMillis() - lastSuperPower > 10000 && lastSuperPower != 0) {
                    pacmanSkin(false);
                    e1.getComponent(PhysicComponent.class).getHitBox().setSolid(true);
                    soundEngine().stopClip("get_out_of_my_swamp.wav");
                }
            }
            soundEngine().playClip("eating_pac.wav", 0.05);
            globalVars().put("score", globalVars().getInt("score")+10);
            getLevel().destroyEntity(e2);
        });

        physicEngine().onCollision(PACMAN, SUPER_PAC, (e1, e2) -> {
            soundEngine().play("eating_pac.wav",0.05);
            soundEngine().playClip("pac_can_eat_ghost.wav",0.05);
            getLevel().destroyEntity(e2);
        });

        physicEngine().onCollision(PACMAN, SUPER_RAINBOW_PAC, (e1, e2) -> {
            soundEngine().playClip("get_out_of_my_swamp.wav",0.1);
            // todo scatter ghost ia
            e1.getComponent(PhysicComponent.class).getHitBox().setSolid(false);
            getLevel().destroyEntity(e2);
            pacmanSkin(true);
            lastSuperPower = System.currentTimeMillis();
        });

        physicEngine().onCollision(PACMAN, TELEPORT, (e1, e2) -> {
            // todo scatter ghost ia
            TransformComponent trs = getLevel().getSingletonEntity(Type.PACMAN).getComponent(TransformComponent.class);
            trs.setPosition(new Point(-1*trs.position().x, trs.position().y));
        });

    }

    @Override
    protected void initLevel() {
        getLevel().getSingletonEntity(Type.PACMAN).getComponent(TransformComponent.class).setPosition(new Point(8,128));

        pacmanSkin(false);

        getLevel().getSingletonEntity(Type.PACMAN).getComponent(PacManLogic.class).setCanMove(true);
    }

    protected void pacmanSkin(boolean isSuper) {
        ArrayList<Image> imageAnimated = new ArrayList<>();
        if(isSuper) {
            imageAnimated.add(AssetsLoader.loadImage("sprites/animation/pacmanWalk/super_open.png"));
            imageAnimated.add(AssetsLoader.loadImage("sprites/animation/pacmanWalk/super_close.png"));
        }
        else
        {
            imageAnimated.add(AssetsLoader.loadImage("sprites/animation/pacmanWalk/pacmanWalk1.png"));
            imageAnimated.add(AssetsLoader.loadImage("sprites/animation/pacmanWalk/pacmanWalk2.png"));
        }
        Animation animation = new Animation(imageAnimated,60,2,false);
        getLevel().getSingletonEntity(Type.PACMAN).getComponent(RenderComponent.class).getTexture().setAnimation(animation);
    }

    private void loadLevel() {
        Level lvl = new TextLevelLoader().load(AssetsLoader.getLevel("map.txt"), new GameFactory());
        Texture texture = new Texture(448, 496, AssetsLoader.loadImage("map/map.png"));
        texture.setZIndex(-1);
        Entity background = new EntityBuilder()
                .position(new Point(0, 0))
                .texture(texture)
                .build();
        lvl.add(background);
        setLevel(lvl);
    }

    public static void main(String[] args) {
        launch(args);
    }
}