package fr.univ.pacman;

import fr.univ.engine.assets.AssetsLoader;
import fr.univ.engine.core.Config;
import fr.univ.engine.core.GameApplication;
import fr.univ.engine.core.TransformComponent;
import fr.univ.engine.core.entity.Entity;
import fr.univ.engine.core.entity.EntityBuilder;
import fr.univ.engine.core.entity.Level;
import fr.univ.engine.core.entity.LevelLoader;
import fr.univ.engine.math.Point;
import fr.univ.engine.math.Vector;
import fr.univ.engine.physic.CollisionHandler;
import fr.univ.engine.physic.PhysicComponent;
import fr.univ.engine.physic.hitbox.SquareHitBox;
import fr.univ.engine.render.RenderComponent;
import fr.univ.engine.render.texture.Texture;
import fr.univ.engine.time.FutureTask;
import fr.univ.pacman.component.PacManLogic;
import fr.univ.pacman.component.ai.GhostAIComponent;
import javafx.scene.input.KeyCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static fr.univ.pacman.Type.*;

/**
 * The logic of the PacMan game
 */
public class PacMan extends GameApplication {

    /**
     * Represent Pacman
     */
    PacManLogic pacmanLogic;

    /**
     * Eat multipliyer for the super-pac (200,400,800,1600)
     */
    private static int eatMultipliyer = 0;

    /**
     * Fruit list
     */
    public static final ArrayList<Entity> listFruits = new ArrayList<>();

    /**
     * Fruit list for the current level
     */
    public static ArrayList<Entity> currentListFruits = new ArrayList<>();

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
        globalVars().put("level", 0);

        Texture textureCherry = new Texture(18, 18, AssetsLoader.loadSprite("item/Cherry.png"));
        Texture textureStrawberry = new Texture(18, 18, AssetsLoader.loadSprite("item/Strawberry.png"));
        Texture textureOrange = new Texture(18, 18, AssetsLoader.loadSprite("item/Orange.png"));


        listFruits.add(new EntityBuilder()
                .type(CHERRY)
                .position(new Point(0, 32))
                .texture(textureCherry)
                .hitbox(new SquareHitBox(16))
                .isSolid(false)
                .build());

        listFruits.add(new EntityBuilder()
                .type(STRAWBERRY)
                .position(new Point(0, 32))
                .texture(textureStrawberry)
                .hitbox(new SquareHitBox(16))
                .isSolid(false)
                .build());

        listFruits.add(new EntityBuilder()
                .type(ORANGE)
                .position(new Point(0, 32))
                .texture(textureOrange)
                .hitbox(new SquareHitBox(16))
                .isSolid(false)
                .build());

        uiEngine().clear();
        AssetsLoader.loadFont("PressStart2P.ttf");
        uiEngine().display(AssetsLoader.loadView("Menu.fxml"));

        IOEngine().onKeyPressed(KeyCode.UP, () -> pacmanLogic.up());
        IOEngine().onKeyPressed(KeyCode.DOWN, () -> pacmanLogic.down());
        IOEngine().onKeyPressed(KeyCode.LEFT, () -> pacmanLogic.left());
        IOEngine().onKeyPressed(KeyCode.RIGHT, () -> pacmanLogic.right());
        IOEngine().onKeyPressed(KeyCode.ESCAPE, () -> {
            pause();
            uiEngine().display(AssetsLoader.loadView("Pause.fxml"));
        });

        setEvents();
    }

    /**
     * Setup events for Pacman when he hit something (pac, ghost ...)
     * Playing music or modify variable regarding of  the event.
     */
    public void setEvents () {
        physicEngine().onCollision(PACMAN, GHOST, this::pacmanWithGhost);

        physicEngine().onCollision(PACMAN, GREATWALL, (e1, e2) -> pacmanLogic.stop());

        physicEngine().onCollision(PACMAN, WALL, this::pacmanWithWall);

        physicEngine().onCollision(PACMAN, GHOST_BASE, (e1, e2) -> pacmanLogic.stop());

        physicEngine().onCollision(PACMAN, PAC, this::eatPac);

        physicEngine().onCollision(PACMAN, SUPER_PAC, this::eatSuperPac);

        physicEngine().onCollision(PACMAN, SUPER_RAINBOW_PAC, this::eatRainbowPac);

        // Teleport entities that hit a teleport pad.
        CollisionHandler teleportCollisionsHandler = (e1, pad) -> {
            TransformComponent transform = e1.getComponent(TransformComponent.class);
            transform.setPosition(new Point(transform.position().x().multiply(BigDecimal.valueOf(-1)), transform.position().y()));
        };
        physicEngine().onCollision(PACMAN, TELEPORT, teleportCollisionsHandler);
        physicEngine().onCollision(GHOST, TELEPORT, teleportCollisionsHandler);

        physicEngine().onCollision(PACMAN, CHERRY, (e1, e2) -> {
            globalVars().put("score", globalVars().getInt("score")+200);
            soundEngine().playClip("eating_fruit.wav", 0.1);
            getLevel().destroyEntity(e2);
            currentListFruits.remove(e2);
            globalVars().put("fruits", globalVars().getInt("fruits")-1);
        });

        physicEngine().onCollision(PACMAN, STRAWBERRY, (e1, e2) -> {
            globalVars().put("score", globalVars().getInt("score")+500);
            soundEngine().playClip("eating_fruit.wav", 0.1);
            getLevel().destroyEntity(e2);
            currentListFruits.remove(e2);
            globalVars().put("fruits", globalVars().getInt("fruits")-1);
        });

        physicEngine().onCollision(PACMAN, ORANGE, (e1, e2) -> {
            globalVars().put("score", globalVars().getInt("score")+1000);
            soundEngine().playClip("eating_fruit.wav", 0.1);
            getLevel().destroyEntity(e2);
            currentListFruits.remove(e2);
            globalVars().put("fruits", globalVars().getInt("fruits")-1);
        });

        physicEngine().onCollision(GHOST, SPAWN_EXIT, (ghost, e2) -> {
            if (!ghost.getComponent(GhostAIComponent.class).isDead() && !ghost.getComponent(GhostAIComponent.class).isScared()) {
                ghost.getComponent(GhostAIComponent.class).notifySpawnExit();
                ghost.getComponent(PhysicComponent.class).setSpeed(GhostAIComponent.NORMALESPEED);
            }
        });

        physicEngine().onCollision(GHOST, GHOST_BASE, (ghost, e2) -> {
            if(ghost.getComponent(GhostAIComponent.class).isDead()) {
                soundEngine().stop("ghost_return_spawn.wav", ghost);
            }
            ghost.getComponent(GhostAIComponent.class).spawn();
            ghost.getComponent(PhysicComponent.class).setSpeed(GhostAIComponent.NORMALESPEED);
        });

    }

    /**
     * Load the map with the map.txt
     */
    private void loadLevel() {
        Level lvl = new LevelLoader(new GameFactory()).load(AssetsLoader.getLevel("map.txt"));
        Texture texture = new Texture(448, 496, -1, AssetsLoader.loadSprite("map/map.png"));
        Entity background = new EntityBuilder()
                .position(new Point(0, 0))
                .texture(texture)
                .build();
        lvl.add(background);
        setLevel(lvl);
    }

    /**
     * Load bonus
     * @param fruitType The fruit type (strawberry, cherry)
     */
    private void loadFruit(Type fruitType) {
        switch (fruitType) {
            case CHERRY:
                getLevel().add(listFruits.get(0));
                timeEngine().schedule(15, TimeUnit.SECONDS, () -> getLevel().destroyEntity(listFruits.get(0)));
                break;
            case STRAWBERRY:
                getLevel().add(listFruits.get(1));
                timeEngine().schedule(15, TimeUnit.SECONDS, () -> getLevel().destroyEntity(listFruits.get(1)));
                break;
            case ORANGE:
                getLevel().add(listFruits.get(2));
                timeEngine().schedule(15, TimeUnit.SECONDS, () -> getLevel().destroyEntity(listFruits.get(2)));
                break;
            default:
                System.out.println("Can't find this type fruit");
        }
    }

    /**
     * Calculating remaining pacs
     * @return The remaining pacs
     */
    public int remainingPacs() {
        int pac =  getLevel().getEntities(PAC).size();
        int superpac = getLevel().getEntities(SUPER_PAC).size();
        int rainbowpac = getLevel().getEntities(SUPER_RAINBOW_PAC).size();
        return pac + superpac + rainbowpac;
    }

    /**
     * Handle when pacman hit a ghost and chose the action to do
     * @param pacman
     * @param ghost
     */
    private void pacmanWithGhost(Entity pacman, Entity ghost) {
        if (ghost.getComponent(GhostAIComponent.class).isDead()) {
            return;
        } else if (ghost.getComponent(GhostAIComponent.class).isScared()) {
            eatGhost(ghost);
        } else pacmanHit(pacman);
    }

    /**
     * Handle action with walls
     * @param pacman Pacman
     * @param wall the wall in the colision
     */
    private void pacmanWithWall(Entity pacman, Entity wall) {
        if (pacman.getComponent(PacManLogic.class).isInRainbowMode()) {
            wall.getComponent(RenderComponent.class).getTexture().setCurrentChannel("destroyed");
            wall.getComponent(PhysicComponent.class).getHitBox().setSolid(false);
        }
    }

    /**
     * Handle action when pacman eat a pac
     * @param pacman Pacman
     * @param pac the pac in the colision
     */
    private void eatPac(Entity pacman, Entity pac) {
        soundEngine().playClip("eating_pac.wav", 0.04);
        globalVars().put("score", globalVars().getInt("score")+10);
        getLevel().destroyEntity(pac);
        if(remainingPacs() == 0) {
            startPlay();
        }
    }

    /**
     * Handle when pacman eat a superpac
     * @param pacman the pac
     * @param superPac the superpac in the colision
     */
    private void eatSuperPac(Entity pacman, Entity superPac) {
        soundEngine().playClip("eating_pac.wav",0.05);
        soundEngine().playSong("pac_can_eat_ghost.wav", 0.05, true);
        GhostAIComponent.setCurrentGlobalState(GhostAIComponent.State.SCARED);
        getLevel().getEntitiesWithComponent(GhostAIComponent.class).forEach(ghost -> {
            ghost.getComponent(GhostAIComponent.class).setTakeCurrentGlobalState(true);
            if (!ghost.getComponent(GhostAIComponent.class).isDead())
                ghost.getComponent(PhysicComponent.class).setSpeed(GhostAIComponent.SCAREDSPEED);
        });
        timeEngine().cancel("FINISHPOWER");

        FutureTask finishPower = new FutureTask(14, TimeUnit.SECONDS, "FINISHPOWER", () -> {
            soundEngine().stop("pac_can_eat_ghost.wav");
            GhostAIComponent.setCurrentGlobalState(GhostAIComponent.State.CHASE);
            getLevel().getEntitiesWithComponent(GhostAIComponent.class).forEach(ghost -> {
                ghost.getComponent(GhostAIComponent.class).setTakeCurrentGlobalState(true);
                eatMultipliyer = 0;
                if (!ghost.getComponent(GhostAIComponent.class).isDead())
                    ghost.getComponent(PhysicComponent.class).setSpeed(GhostAIComponent.NORMALESPEED);
            });
        });

        timeEngine().schedule(finishPower);

        getLevel().destroyEntity(superPac);
        if(remainingPacs() == 0) {
            startPlay();
        }
    }

    /**
     * Handle when action eat a raimbowpac
     * @param pacman Pacman
     * @param rainbowPac the rainbow-pac in the colisioon
     */
    private void eatRainbowPac(Entity pacman, Entity rainbowPac) {
        pacmanLogic.setCurrentMode(PacManLogic.Mode.RAINBOW);
        soundEngine().playSong("get_out_of_my_swamp.wav", 0.05);
        getLevel().destroyEntity(rainbowPac);
        if(remainingPacs() == 0){
            startPlay();
        }

        timeEngine().schedule(10, TimeUnit.SECONDS, () -> {
            pacmanLogic.setCurrentMode(PacManLogic.Mode.NORMAL);
            soundEngine().stop("get_out_of_my_swamp.wav");
        });
    }

    /**
     * Handle when pacman is hit by a fantom who was chasing him
     */
    private void pacmanHit(Entity pacman) {
        if (!pacman.getComponent(PacManLogic.class).isDeath()) {
            globalVars().put("lives", globalVars().getInt("lives") - 1);

            soundEngine().stopAll();
            soundEngine().playSong("pac_die.wav", 0.05);

            pacmanLogic.stop();
            pacmanLogic.setCanMove(false);

            pacman.getComponent(PacManLogic.class).setCurrentMode(PacManLogic.Mode.DEATH);

            pacman.getComponent(RenderComponent.class).getTexture().setCurrentChannel("death");

            timeEngine().schedule(1, TimeUnit.SECONDS, () -> replaceEntity(pacman));

            getLevel().getEntitiesWithComponent(GhostAIComponent.class).forEach(ghost -> {
                ghost.getComponent(PhysicComponent.class).setSpeed(0);
            });

            if (globalVars().getInt("lives") <= 0) {
                pause();
                uiEngine().display(AssetsLoader.loadView("GameOver.fxml"));
            }
        }
    }

    /**
     * Replace entities when PacMan is dying
     */
    private void replaceEntity(Entity pacman) {
        //Replace PacMan
        pacman.getComponent(RenderComponent.class).getTexture().setCurrentChannel("walking");
        pacman.getComponent(TransformComponent.class).setPosition(new Point(8,128));
        pacman.getComponent(PacManLogic.class).setCurrentMode(PacManLogic.Mode.NORMAL);
        pacman.getComponent(PacManLogic.class).setCanMove(true);
        pacman.getComponent(TransformComponent.class).setRotation(0);

        //replaceGhost
        getLevel().getEntitiesWithComponent(GhostAIComponent.class).forEach(ghost -> {
            ghost.getComponent(GhostAIComponent.class).teleportToBase();
            ghost.getComponent(PhysicComponent.class).setSpeed(GhostAIComponent.NORMALESPEED);
        });
    }

    /**
     * Handle when pacman hit a ghost while ghost was scared
     * @param ghost that has been eat by pacman
     */
    private void eatGhost(Entity ghost) {
        eatMultipliyer += 1;
        int eatPoint = 200*eatMultipliyer;
        if (eatPoint > 1600) eatPoint = 1600;
        GhostAIComponent ai = ghost.getComponent(GhostAIComponent.class);
        ai.setDead();
        globalVars().put("score", globalVars().getInt("score")+eatPoint);
        soundEngine().playSong("ghost_return_spawn.wav", 0.15, true, ghost);
        ghost.getComponent(PhysicComponent.class).setSpeed(GhostAIComponent.DEATHSPEED);
    }

    /**
     * Initialise some variables when the games realy start, or when a player pass a level
     */
    @Override
    protected void startPlay() {
        soundEngine().stopAll();
        uiEngine().clear();


        currentListFruits.clear();
        timeEngine().cancel("LOADFRUIT");

        globalVars().put("level", globalVars().getInt("level") + 1);
        for (int i = 0; i< globalVars().getInt("level"); i++) {
            if (i<listFruits.size())
                currentListFruits.add(listFruits.get(i));
        }

        globalVars().put("fruits", globalVars().getInt("level"));

        soundEngine().playSong("intro.wav", 0.05);
        uiEngine().display(AssetsLoader.loadView("Overlay.fxml"));
        loadLevel();
        pacmanLogic = getLevel().getSingletonEntity(Type.PACMAN).getComponent(PacManLogic.class);

        List<Entity> ghosts = getLevel().getEntitiesWithComponent(GhostAIComponent.class);
        for (int i = 0; i <= 3; ++i) {
            final int index = i;
            timeEngine().schedule((i*7)+4, TimeUnit.SECONDS, () -> ghosts.get(index).getComponent(GhostAIComponent.class).spawn());
        }

        for (int i = 0; i<currentListFruits.size(); i++) {
            switch (i) {
                case 0 :
                    FutureTask futureTaskCherry = new FutureTask(30, TimeUnit.SECONDS, "LOADFRUIT", () -> loadFruit(CHERRY));
                    timeEngine().schedule(futureTaskCherry);
                    break;
                case 1:
                    FutureTask futureTaskStrawberry = new FutureTask(60, TimeUnit.SECONDS, "LOADFRUIT", () -> loadFruit(STRAWBERRY));
                    timeEngine().schedule(futureTaskStrawberry);
                    break;
                case 2:
                    FutureTask futureTaskOrange = new FutureTask(90, TimeUnit.SECONDS, "LOADFRUIT", () -> loadFruit(ORANGE));
                    timeEngine().schedule(futureTaskOrange);
                    break;
                default:
                    System.out.println("No fruit");
                    break;
            }
        }

        pacmanLogic.setCanMove(false);
        timeEngine().schedule(4, TimeUnit.SECONDS, () -> {
            pacmanLogic.setCurrentMode(PacManLogic.Mode.NORMAL);
            pacmanLogic.getComponent(PhysicComponent.class).setDirection(new Vector(getLevel().getEntities(PACMAN).get(0).getComponent(PhysicComponent.class).getSpeed(),0));
            pacmanLogic.setCanMove(true);
        });
    }

    /**
     * The main, launching the app
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}