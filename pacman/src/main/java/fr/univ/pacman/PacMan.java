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
import fr.univ.engine.render.RenderComponent;
import fr.univ.engine.render.texture.Animation;
import fr.univ.engine.render.texture.Texture;
import fr.univ.pacman.component.PacManLogic;
import fr.univ.pacman.component.ai.GhostAIComponent;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static fr.univ.pacman.Type.*;

/**
 * The logic of the PacMan game
 */
public class PacMan extends GameApplication {

    /**
     * The time when a superpower has been ate by Pacman
     */
    private long lastSuperPower = 0L;

    /**
     * Configure the Pac-Man Window
     * @param config the configuration to edit.
     */
    @Override
    protected void config(Config config) {
        config.title = "Pac-Man";
        config.version = "2.0";
        config.width = 510;
        config.height = 620;
        config.resizable = false;
        config.displayFPS = true;
    }

    /**
     * Initialize the game (loading level, number of lives)
     * Setup events for Pacman (keyboard)
     */
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

        List<Entity> ghosts = getLevel().getEntitiesWithComponent(GhostAIComponent.class);
        for (int i = 1; i <= 4; ++i) {
            final int index = i-1;
            timeEngine().runIn(i*10, TimeUnit.SECONDS, () -> ghosts.get(index).getComponent(GhostAIComponent.class).spawn());
        }

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

        setEvents(pacmanLogic);

        physicEngine().onCollision(GHOST, SPAWN_EXIT, (ghost, e2) -> {
            ghost.getComponent(GhostAIComponent.class).notifySpawnExit();
        });
    }

    /**
     * Setup events for Pacman when he hit something (pac, ghost ...)
     * Playing music or modify variable regarding of  the event.
     * @param pacmanLogic
     */
    public void setEvents (PacManLogic pacmanLogic) {
        physicEngine().onCollision(PACMAN, GHOST, (e1, e2) -> {
            globalVars().put("lives", globalVars().getInt("lives")-1);

            soundEngine().playClip("pac_die.wav");
            pacmanLogic.stop();
            pacmanLogic.hit();
            pacmanLogic.setCanMove(false);

            getLevel().getSingletonEntity(Type.PACMAN).getComponent(TransformComponent.class).setRotation(0);
            getLevel().getSingletonEntity(Type.PACMAN).getComponent(RenderComponent.class).setForAnimator(true);

            ArrayList<Image> animationDeath = new ArrayList<>();
            for (int i = 1; i < 12; i++) {
                String deathName = "sprites/animation/pacmanDeath/pacmanDeath" + i + ".png";
                animationDeath.add(AssetsLoader.loadImage(deathName));
            }

            Animation animation = new Animation(animationDeath, 70, 11, true);

            getLevel().getSingletonEntity(Type.PACMAN).getComponent(RenderComponent.class).getTexture().setAnimation(animation);
            animatedPause();

            getLevel().getEntitiesWithComponent(GhostAIComponent.class).forEach(ghost -> {
                GhostAIComponent ai = ghost.getComponent(GhostAIComponent.class);
                ai.teleportToBase();
                ai.spawn();
            });

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

    /**
     * The initial pacman skin and position
     */
    @Override
    protected void initLevel() {
        getLevel().getSingletonEntity(Type.PACMAN).getComponent(TransformComponent.class).setPosition(new Point(8,128));
        pacmanSkin(false);
        getLevel().getSingletonEntity(Type.PACMAN).getComponent(PacManLogic.class).setCanMove(true);
    }

    /**
     * Edit the PacMan skin
     * @param isSuper True if you want the super pacman skin
     */
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

    /**
     * Load the map with the map.txt
     */
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