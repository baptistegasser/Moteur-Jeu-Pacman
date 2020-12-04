package fr.univ.pacman;

import fr.univ.engine.assets.AssetsLoader;
import fr.univ.engine.core.TransformComponent;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.core.entity.EntityBuilder;
import fr.univ.engine.core.entity.Level;
import fr.univ.engine.core.Config;
import fr.univ.engine.core.GameApplication;
import fr.univ.engine.core.entity.LevelLoader;
import fr.univ.engine.math.Point;
import fr.univ.engine.physic.CollisionHandler;
import fr.univ.engine.physic.PhysicComponent;
import fr.univ.engine.physic.hitbox.SquareHitBox;
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

        timeEngine().runIn(30, TimeUnit.SECONDS, () -> loadFruit(CHERRY));

        timeEngine().runIn(70, TimeUnit.SECONDS, () -> loadFruit(STRAWBERRY));
    }

    /**
     * Setup events for Pacman when he hit something (pac, ghost ...)
     * Playing music or modify variable regarding of  the event.
     * @param pacmanLogic
     */
    public void setEvents (PacManLogic pacmanLogic) {
        physicEngine().onCollision(PACMAN, GHOST, (e1, e2) -> {
            if (GhostAIComponent.getCurrentGlobalState() == GhostAIComponent.State.SCARED) {
                eatGhost(e2.getComponent(GhostAIComponent.class));
                return;
            }

            globalVars().put("lives", globalVars().getInt("lives")-1);

            soundEngine().stopAllClips();
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

            timeEngine().runIn(1, TimeUnit.SECONDS, this::replaceEntity);

            getLevel().getEntitiesWithComponent(GhostAIComponent.class).forEach(ghost -> {
                GhostAIComponent ai = ghost.getComponent(GhostAIComponent.class);
                ai.teleportToBase();
            });

            if (globalVars().getInt("lives") <= 0) {
                pause();
                uiEngine().display(AssetsLoader.loadView("GameOver.fxml"));
            }
        });

        physicEngine().onCollision(PACMAN, GREATWALL, (e1, e2) -> pacmanLogic.stop());

        physicEngine().onCollision(PACMAN, WALL, (pacman, wall) -> {
            if (pacman.getComponent(PacManLogic.class).isInRainbowMode()) {
                Texture texture = new Texture(16, 16, AssetsLoader.loadImage("item/black.png"));
                texture.setZIndex(1);
                wall.getComponent(RenderComponent.class).setTexture(texture);
                wall.getComponent(PhysicComponent.class).getHitBox().setSolid(false);
            }
        });

        physicEngine().onCollision(PACMAN, PAC, (e1, e2) -> {
            soundEngine().playClip("eating_pac.wav", 0.05);
            globalVars().put("score", globalVars().getInt("score")+10);
            getLevel().destroyEntity(e2);
            if(remainingPacs() == 0) {
                //do stuff
            }
        });

        physicEngine().onCollision(PACMAN, SUPER_PAC, (e1, e2) -> {
            soundEngine().play("eating_pac.wav",0.05);
            soundEngine().playClip("pac_can_eat_ghost.wav",0.05);
            GhostAIComponent.setCurrentGlobalState(GhostAIComponent.State.SCARED);
            timeEngine().runIn(15, TimeUnit.SECONDS, () -> {
                System.out.println("Fin");
                GhostAIComponent.setCurrentGlobalState(GhostAIComponent.State.CHASE);
            });
            getLevel().destroyEntity(e2);
            if(remainingPacs() == 0) {
                //do stuff
            }
        });

        physicEngine().onCollision(PACMAN, SUPER_RAINBOW_PAC, (pacman, rainbowPac) -> {
            pacmanLogic.setCurrentMode(PacManLogic.Mode.RAINBOW);
            soundEngine().playClip("get_out_of_my_swamp.wav", 0.1);
            getLevel().destroyEntity(rainbowPac);
            if(remainingPacs() == 0){
                // do stuff
            }
            timeEngine().runIn(5, TimeUnit.SECONDS, () -> {
                pacmanLogic.setCurrentMode(PacManLogic.Mode.NORMAL);
                soundEngine().stopClip("get_out_of_my_swamp.wav");
            });
        });

        // Teleport entities that hit a teleport pad.
        CollisionHandler teleportCollisionsHandler = (e1, pad) -> {
            TransformComponent transform = e1.getComponent(TransformComponent.class);
            transform.setPosition(new Point(-1*transform.position().x, transform.position().y));
        };
        physicEngine().onCollision(PACMAN, TELEPORT, teleportCollisionsHandler);
        physicEngine().onCollision(GHOST, TELEPORT, teleportCollisionsHandler);

        physicEngine().onCollision(PACMAN, CHERRY, (e1, e2) -> {
            globalVars().put("score", globalVars().getInt("score")+200);
            getLevel().destroyEntity(e2);
        });

        physicEngine().onCollision(PACMAN, STRAWBERRY, (e1, e2) -> {
            globalVars().put("score", globalVars().getInt("score")+500);
            getLevel().destroyEntity(e2);
        });
    }

    private void replaceEntity() {
        getLevel().getSingletonEntity(Type.PACMAN).getComponent(TransformComponent.class).setPosition(new Point(8,128));
        getLevel().getSingletonEntity(Type.PACMAN).getComponent(PacManLogic.class).setCurrentMode(PacManLogic.Mode.NORMAL);
        getLevel().getSingletonEntity(Type.PACMAN).getComponent(PacManLogic.class).setCanMove(true);
    }

    /**
     * Load the map with the map.txt
     */
    private void loadLevel() {
        Level lvl = new LevelLoader(new GameFactory()).load(AssetsLoader.getLevel("map.txt"));
        Texture texture = new Texture(448, 496, AssetsLoader.loadImage("map/map.png"));
        texture.setZIndex(-1);
        Entity background = new EntityBuilder()
                .position(new Point(0, 0))
                .texture(texture)
                .build();
        lvl.add(background);
        setLevel(lvl);
    }

    private void loadFruit(Type fruitType) {
        switch (fruitType) {
            case CHERRY:
                Texture textureCherry = new Texture(18, 18, AssetsLoader.loadImage("sprites/fruits/cherry.png"));
                Entity cherry = new EntityBuilder()
                        .type(fruitType)
                        .position(new Point(0, 32))
                        .texture(textureCherry)
                        .hitbox(new SquareHitBox(16))
                        .isSolid(false)
                        .build();
                getLevel().add(cherry);
                timeEngine().runIn(15, TimeUnit.SECONDS, () -> getLevel().destroyEntity(cherry));
                break;
            case STRAWBERRY:
                Texture textureStrawberry = new Texture(18, 18, AssetsLoader.loadImage("sprites/fruits/strawberry.png"));
                Entity strawberry = new EntityBuilder()
                        .type(fruitType)
                        .position(new Point(0, 32))
                        .texture(textureStrawberry)
                        .hitbox(new SquareHitBox(16))
                        .isSolid(false)
                        .build();
                getLevel().add(strawberry);
                timeEngine().runIn(15, TimeUnit.SECONDS, () -> getLevel().destroyEntity(strawberry));
                break;
            default:
                System.out.println("Can't find this type fruit");
        }
    }

    public int remainingPacs() {
        int pac =  getLevel().getEntities(PAC).size();
        int superpac = getLevel().getEntities(SUPER_PAC).size();
        int rainbowpac = getLevel().getEntities(SUPER_RAINBOW_PAC).size();
        return pac + superpac + rainbowpac;
    }

    private void eatGhost(GhostAIComponent ghost) {
        ghost.teleportToBase();
    }

    public static void main(String[] args) {
        launch(args);
    }
}