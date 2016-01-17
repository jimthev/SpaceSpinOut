package com.dragonjetgames.spacespinout;
// Copyright (C) 2015 James Thevenot - All Rights Reserved

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.dragonjetgames.spacespinout.scene.DebugScene;
import com.dragonjetgames.spacespinout.scene.GameCompleteScene;
import com.dragonjetgames.spacespinout.scene.GameLostScene;
import com.dragonjetgames.spacespinout.scene.HelpScene;
import com.dragonjetgames.spacespinout.scene.LevelCompleteScene;
import com.dragonjetgames.spacespinout.scene.MainGameScene;
import com.dragonjetgames.spacespinout.scene.MainMenuScene;
import com.dragonjetgames.spacespinout.script.LevelScript;
import com.dragonjetgames.spacespinout.spaceobjects.Asteroid;
import com.dragonjetgames.spacespinout.spaceobjects.Bullet;
import com.dragonjetgames.spacespinout.spaceobjects.PlayerShip;
import com.dragonjetgames.spacespinout.spaceobjects.SpaceObject;
import com.netthreads.libgdx.director.Director;
import com.netthreads.libgdx.event.ActorEvent;
import com.netthreads.libgdx.event.ActorEventObserver;
import com.netthreads.libgdx.scene.Scene;
import com.netthreads.libgdx.scene.transition.MoveInLTransitionScene;
import com.netthreads.libgdx.scene.transition.MoveInRTransitionScene;
import com.netthreads.libgdx.scene.transition.TransitionScene;
import com.netthreads.libgdx.texture.TextureCache;
import com.netthreads.libgdx.texture.TextureDefinition;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import aurelienribon.tweenengine.equations.Bounce;

public class SpaceSpinOut extends ApplicationAdapter implements ActorEventObserver {
    public static SpaceSpinOut spaceSpinOut;
    public static final boolean showDebug = false;

    public static final int DURATION_GAME_TRANSITION = 900;

    public static final String TEXTURE_DIR = "textures/";
    public static final String TEXTURE_SPACE_SHIP = "space-ship.png";
    public static final String TEXTURE_ASTEROID = "asteroid-0.png";
    public static final String TEXTURE_FIREBALL = "fireball.png";
    public static final String TEXTURE_STAR_BACKGROUND = "star background 1.jpg";
    public static final String TEXTURE_FIREBALL_SHEET = "fireball_sheet.png";
    public static final String TEXTURE_MASCOT = "DragonJetMascotGreyOutline.png";

    public static float BULLET_WIDTH = 5;
    public static float BULLET_HEIGHT = 10;


    public Sound hitAsteroid;
    public Sound shootBullet;
    public Sound loseLife;


    public com.dragonjetgames.spacespinout.scene.GameDebugScene gameDebugScene;
    public InputAdapter inputAdapter;


    public static SpaceSpinOutContactListener contactListener;
    public SpaceSpinOutOverlay overlay;
    OrthographicCamera gameCamera;
    public static World world;
    float accumulator = 0;
    static final float TIME_STEP = 1.0f / 45.0f;
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 2;
    public static final float gameCameraViewPortSize = 200;
    float lastBulletTime = 0;
    float minimumBulletDelta = .3f;
    float currentTime = 0;

    public int livesLeft = 3;

    public float screenRPM = 10; //10;
    boolean screenRPMUp = true;

    public boolean addAnAsteroid = false;
    public boolean isGamePaused = false;
    public boolean isGameBackgroundOnly = true;

    public int currentLevel = 1;

    Box2DDebugRenderer box2DDebugRenderer;

    com.dragonjetgames.spacespinout.spaceobjects.PlayerShip playerShip;
    SpriteBatch batch;

    com.dragonjetgames.spacespinout.spaceobjects.StarBackground starBackground;

    float shipSize = 30;

    private Director director;
    float w;
    float h;
    Scene lastScene;
    com.dragonjetgames.spacespinout.scene.DebugScene debugScene;
    MainGameScene mainGameScene;
    MainMenuScene mainMenuScene;
    LevelCompleteScene levelCompleteScene;
    GameCompleteScene gameCompleteScene;
    GameLostScene gameLostScene;
    HelpScene helpScene;

    public int currentScore = 0;

    boolean showBox2D = false;

    LevelScript levelScript;

    TextureCache textureCache;

    public SpaceSpinOut() {
        spaceSpinOut = this;
        currentLevel = 1;
    }

    public void loadCurrentLevel() {
        levelScript.loadLevel(currentLevel);
        levelScript.setupNextEvent();
    }

    public void pauseGame() {
        isGamePaused = true;
        levelScript.stopGame();
    }

    // these will be used a simple place to add sounds.
    public void lostLife() {
        loseLife.play();
        livesLeft--;
    }

    public void shotAsteroid() {
        hitAsteroid.play();
    }

    public void shotBullet() {
        currentScore--;
        shootBullet.play();
    }

    public float getAsteroidRPM() {
        return SpaceSpinOutConstants.iniAsteroidRPM;
    }

    public void setAsteroidRPM(float rpm) {
        SpaceSpinOutConstants.iniAsteroidRPM = rpm;
    }

    public float getViewRPM() {
        return screenRPM;
    }

    public void setViewRPM(float rpm) {
        screenRPM = rpm;
    }

    public float getShipRPM() {
        return playerShip.shipRPM;
    }

    public void setShipRPM(float rpm) {
        playerShip.setRPM(rpm);
    }

    public MainGameScene getMainGameScene() {
        if (mainGameScene == null) {
            mainGameScene = new MainGameScene();
            InputMultiplexer inputMultiplexer = mainGameScene.getInputMultiplexer();
            gameDebugScene.addInputProcessors(inputMultiplexer);
            inputMultiplexer.addProcessor(inputAdapter);
        }

        return mainGameScene;
    }

    public DebugScene getDebugScene() {
        if (debugScene == null) {
            debugScene = new DebugScene();
            InputMultiplexer inputMultiplexer = debugScene.getInputMultiplexer();
            gameDebugScene.addInputProcessors(inputMultiplexer);
            inputMultiplexer.addProcessor(inputAdapter);
        }

        return debugScene;
    }

    public HelpScene getHelpScene() {
        if (helpScene == null) {
            helpScene = new HelpScene();
            InputMultiplexer inputMultiplexer = helpScene.getInputMultiplexer();
            gameDebugScene.addInputProcessors(inputMultiplexer);
            inputMultiplexer.addProcessor(inputAdapter);
        }

        return helpScene;
    }

    public GameCompleteScene getGameCompleteScene() {
        if (gameCompleteScene == null) {
            gameCompleteScene = new GameCompleteScene();
            InputMultiplexer inputMultiplexer = gameCompleteScene.getInputMultiplexer();
            gameDebugScene.addInputProcessors(inputMultiplexer);
            inputMultiplexer.addProcessor(inputAdapter);
        }

        return gameCompleteScene;
    }

    public GameLostScene getGameLostScene() {
        if (gameLostScene == null) {
            gameLostScene = new GameLostScene();
            InputMultiplexer inputMultiplexer = gameLostScene.getInputMultiplexer();
            gameDebugScene.addInputProcessors(inputMultiplexer);
            inputMultiplexer.addProcessor(inputAdapter);
        }

        return gameLostScene;
    }

    public LevelCompleteScene getLevelCompleteScene() {
        if (levelCompleteScene == null) {
            levelCompleteScene = new LevelCompleteScene();
            InputMultiplexer inputMultiplexer = levelCompleteScene.getInputMultiplexer();
            gameDebugScene.addInputProcessors(inputMultiplexer);
            inputMultiplexer.addProcessor(inputAdapter);
        }

        return levelCompleteScene;
    }

    public MainMenuScene getMainMenuScene() {
        if (mainMenuScene == null) {
            mainMenuScene = new MainMenuScene();
            InputMultiplexer inputMultiplexer = mainMenuScene.getInputMultiplexer();
            gameDebugScene.addInputProcessors(inputMultiplexer);
            inputMultiplexer.addProcessor(inputAdapter);
        }

        return mainMenuScene;
    }

    @Override
    public void create() {
        director = Director.getDirector();
        textureCache = TextureCache.getTextureCache();

        levelScript = new LevelScript();

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        // Set initial width and height.
        director.setWidth((int) w);
        director.setHeight((int) h);

        // Add this as an event observer.
        director.registerEventHandler(this);

        shootBullet = Gdx.audio.newSound(Gdx.files.internal("audio/62412__fons__steek-1.wav"));
        hitAsteroid = Gdx.audio.newSound(Gdx.files.internal("audio/264779__anthonychan0__civilian-stun-gun-1-10s-zap.wav"));
        loseLife = Gdx.audio.newSound(Gdx.files.internal("audio/209772__johnnyfarmer__metal-boom.wav"));

        List<TextureDefinition> TEXTURES = new LinkedList<TextureDefinition>() {
            {
                add(new TextureDefinition(TEXTURE_MASCOT, TEXTURE_DIR + TEXTURE_MASCOT));
            }
        };

        textureCache.load(TEXTURES);

//    debugScene = getDebugScene();

        overlay = new SpaceSpinOutOverlay();
        overlay.create();
        setupScreenArea();

        setupBox2D();

        playerShip = new PlayerShip(shipSize);

        playerShip.setRPM(SpaceSpinOutConstants.iniShipRPM);

        starBackground = new com.dragonjetgames.spacespinout.spaceobjects.StarBackground(gameCameraViewPortSize);

        inputAdapter = new InputAdapter() {
            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                if (!isGamePaused && !isGameBackgroundOnly) {
                    if (currentTime >= lastBulletTime + minimumBulletDelta) {
                        lastBulletTime = currentTime;
                        playerShip.getBullet();
                        shotBullet();
                    }
                }
                return true; // return true to indicate the event was handled
            }

//      public boolean keyDown (int keycode) {
//        switch (keycode) {
//          case Input.Keys.SPACE:
//            if (screenRPMUp) {
//              screenRPM += SpaceSpinOutConstants.deltaScreenRPM;
//            } else {
//              screenRPM -= SpaceSpinOutConstants.deltaScreenRPM;
//            }
//            if (screenRPM > SpaceSpinOutConstants.maxScreenRPM) {
//              screenRPM = SpaceSpinOutConstants.maxScreenRPM - SpaceSpinOutConstants.deltaScreenRPM;
//              screenRPMUp = false;
//            } else if (screenRPM < -SpaceSpinOutConstants.maxScreenRPM) {
//              screenRPM = -SpaceSpinOutConstants.maxScreenRPM + SpaceSpinOutConstants.deltaScreenRPM;;
//              screenRPMUp = true;
//            }
//        }
//
//        return false;
//      }

//			public boolean keyDown (int keycode) {
//				System.exit(0);
//				return false;
//			}

        };

        setupScenes();

        batch = new SpriteBatch();

        MainMenuScene iniScene = getMainMenuScene();
        iniScene.iniGame();

        director.setScene(iniScene);
    }

    public void removeAndActSpaceObjects(float delta) {
//    int numBullets = 0;
        Array bodies = new Array();
        world.getBodies(bodies);
        Iterator it = bodies.iterator();
        while (it.hasNext()) {
            Body body = (Body) it.next();
            if (body == null) {
                continue;
            }

            com.dragonjetgames.spacespinout.spaceobjects.SpaceObject obj = (com.dragonjetgames.spacespinout.spaceobjects.SpaceObject) body.getUserData();

            if (obj == null) {
                continue;
            }

            obj.act(delta);

            if (obj instanceof Bullet) {
//        numBullets++;
                if (Math.abs(body.getPosition().x) * Math.abs(body.getPosition().x) + Math.abs(body.getPosition().y) * Math.abs(body.getPosition().y) > (gameCameraViewPortSize / 2 + 20) * (gameCameraViewPortSize / 2 + 20)) {
                    obj.toDelete = true;
                }
            }

            if (obj.toDelete) {
                world.destroyBody(body);
                body.setUserData(null);
            }
        }

//    System.out.println("numBullets:"+numBullets);

        starBackground.act(delta);
        levelScript.act(delta);
    }


    public void removeAllSpaceObjects() {
//    int numBullets = 0;
        Array bodies = new Array();
        world.getBodies(bodies);
        Iterator it = bodies.iterator();
        while (it.hasNext()) {
            Body body = (Body) it.next();
            if (body == null) {
                continue;
            }

            SpaceObject obj = (SpaceObject) body.getUserData();

            if (obj == null) {
                continue;
            }

            if (obj instanceof Bullet || obj instanceof Asteroid) {
                obj.toDelete = true;
            }

            if (obj.toDelete) {
                world.destroyBody(body);
                body.setUserData(null);
            }
        }
    }

    public void drawSpaceObjects(SpriteBatch batch) {
        Array bodies = new Array();
        world.getBodies(bodies);
        Iterator it = bodies.iterator();
        SpaceObject ship = null;
        while (it.hasNext()) {
            Body body = (Body) it.next();
            if (body == null) {
                continue;
            }

            SpaceObject obj = (SpaceObject) body.getUserData();

            if (obj == null) {
                continue;
            }

            if (obj instanceof PlayerShip) {
                ship = obj;
            } else {
                obj.draw(batch);
            }
        }
        if (ship != null) {
            ship.draw(batch);
        }
    }

    public void setupBox2D() {
        Box2D.init();
        world = new World(new Vector2(0, -20), true);
        box2DDebugRenderer = new Box2DDebugRenderer();

        contactListener = new SpaceSpinOutContactListener(this);

        world.setContactListener(contactListener);
    }

    public Asteroid getAsteroid() {
        return new Asteroid(SpaceSpinOutConstants.iniAsteroidRadius, SpaceSpinOutConstants.iniAsteroidRPM);
    }

    public void iniGame() {
        accumulator = 0;
        playerShip.angle = 0;
        playerShip.setRPM(0);
        screenRPM = 0;
        setViewUp();
        removeAllSpaceObjects();
        loadCurrentLevel();
    }

    public void setViewUp() {
        gameCamera.up.x = 0;
        gameCamera.up.y = 1;
        gameCamera.up.z = 0;
    }

    public void startGame() {
        isGameBackgroundOnly = false;
        isGamePaused = false;
        levelScript.startGame();
    }

    public void doPhysicsStep(float deltaTime) {
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            accumulator -= TIME_STEP;
        }
    }

    public void setupScreenArea() {
        overlay.setupScreenArea();

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int screenAreaInPixels = screenHeight;

        if (screenWidth < screenHeight) {
            screenAreaInPixels = screenWidth;
        }

        float gameCameraViewPortWidth = screenWidth * gameCameraViewPortSize / screenAreaInPixels;
        float gameCameraViewPortHeight = screenHeight * gameCameraViewPortSize / screenAreaInPixels;

        gameCamera = new OrthographicCamera(gameCameraViewPortWidth, gameCameraViewPortHeight);
        gameCamera.update();
    }

    public void setupScenes() {
        gameDebugScene = new com.dragonjetgames.spacespinout.scene.GameDebugScene();

        Gdx.input.setInputProcessor(gameDebugScene.getInputMultiplexer());
        gameDebugScene.getInputMultiplexer().addProcessor(inputAdapter);
    }

    @Override
    public void resize(int width, int height) {
        setupScreenArea();
        gameDebugScene.getViewport().setScreenSize(width, height);
    }

    @Override
    public void render() {
        if (livesLeft <= 0) {
            livesLeft = 3;
            transitionToGameLostScene();
        }

        currentTime += Gdx.graphics.getDeltaTime();
        super.render();
        float delta = Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float rotationDegreesPerSecond = (360.0f * screenRPM) * (1.0f / 60.0f);

        if (!isGamePaused && !isGameBackgroundOnly) {
            doPhysicsStep(delta);
            removeAndActSpaceObjects(delta);
            if (addAnAsteroid) {
                Asteroid asteroid = getAsteroid();
                asteroid.act(delta);
                addAnAsteroid = false;
            }

            gameCamera.rotate(rotationDegreesPerSecond * delta);
        } else if (isGameBackgroundOnly) {
            gameCamera.rotate(rotationDegreesPerSecond * delta);
            starBackground.act(delta);
        }
        gameCamera.update();

        batch.setProjectionMatrix(gameCamera.combined);
        batch.setTransformMatrix(new Matrix4());

        batch.begin();
        starBackground.draw(batch);
        if (!isGameBackgroundOnly) {
            drawSpaceObjects(batch);
        }
        batch.end();

        if (showBox2D) {
            box2DDebugRenderer.render(world, gameCamera.combined);
        }

        overlay.render();

        if (showDebug) {
            gameDebugScene.act(delta);
            gameDebugScene.draw();
        }

        director.update();

    }

    public void dispose() {
        director.dispose();

        Director.director = null;

        if (debugScene != null) {
            debugScene.dispose();
            debugScene = null;
        }

        if (mainGameScene != null) {
            mainGameScene.dispose();
            mainGameScene = null;
        }

    }

    public void toggleTransitionToDebugScene() {
//    actionResolver.hideAd();
        Scene outScene = director.getScene();
        DebugScene inScene = getDebugScene();
        if (inScene == outScene) {
            inScene = (DebugScene) lastScene;
        } else {
            lastScene = outScene;
            inScene.iniGame();
        }

        TransitionScene transitionScene = MoveInRTransitionScene.$(inScene, outScene, DURATION_GAME_TRANSITION, Bounce.OUT);

        director.setScene(transitionScene);
    }

    public void transitionToDebugScene() {
//    actionResolver.hideAd();
        DebugScene inScene = getDebugScene();
        inScene.iniGame();
        Scene outScene = director.getScene();

        TransitionScene transitionScene = MoveInRTransitionScene.$(inScene, outScene, DURATION_GAME_TRANSITION, Bounce.OUT);

        director.setScene(transitionScene);
    }

    public void toggleBox2D() {
        showBox2D = !showBox2D;
    }

    public void transitionToGameScene() {
        MainGameScene inScene = getMainGameScene();
        inScene.iniGame();
        Scene outScene = director.getScene();

        TransitionScene transitionScene = MoveInRTransitionScene.$(inScene,
                outScene, DURATION_GAME_TRANSITION, Bounce.OUT);

        director.setScene(transitionScene);
        startGame();

    }

    public void stopGame() {
        isGameBackgroundOnly = true;
        removeAllSpaceObjects();
        levelScript.stopGame();
    }

    public void transitionToLevelCompleteScene() {
        LevelCompleteScene inScene = getLevelCompleteScene();
        inScene.iniGame();
        Scene outScene = director.getScene();

        TransitionScene transitionScene = MoveInRTransitionScene.$(inScene,
                outScene, DURATION_GAME_TRANSITION, Bounce.OUT);

        director.setScene(transitionScene);
    }

    public void transitionToGameLostScene() {
        GameLostScene inScene = getGameLostScene();
        inScene.iniGame();
        Scene outScene = director.getScene();

        TransitionScene transitionScene = MoveInRTransitionScene.$(inScene,
                outScene, DURATION_GAME_TRANSITION, Bounce.OUT);

        director.setScene(transitionScene);
    }

    public void transitionToHelpScene() {
        HelpScene inScene = getHelpScene();
        inScene.iniGame();
        Scene outScene = director.getScene();

        TransitionScene transitionScene = MoveInLTransitionScene.$(inScene,
                outScene, DURATION_GAME_TRANSITION, Bounce.OUT);

        director.setScene(transitionScene);
    }

    public void transitionToGameCompleteScene() {
        GameCompleteScene inScene = getGameCompleteScene();
        inScene.iniGame();
        Scene outScene = director.getScene();

        TransitionScene transitionScene = MoveInRTransitionScene.$(inScene,
                outScene, DURATION_GAME_TRANSITION, Bounce.OUT);

        director.setScene(transitionScene);
    }

    public void transitionToMainMenuScene() {
        MainMenuScene inScene = getMainMenuScene();
        inScene.iniGame();
        Scene outScene = director.getScene();

        TransitionScene transitionScene = MoveInRTransitionScene.$(inScene,
                outScene, DURATION_GAME_TRANSITION, Bounce.OUT);

        director.setScene(transitionScene);
    }

    @Override
    public boolean handleEvent(ActorEvent event) {
//    System.out.println("event:"+event);
        boolean handled = false;
        switch (event.getId()) {
            case SpaceSpinOutEvents.EVENT_TRANSITION_TO_DEBUG_SCENE:
                transitionToDebugScene();
                handled = true;
                break;
            case SpaceSpinOutEvents.EVENT_TOGGLE_TRANSITION_TO_DEBUG_SCENE:
                toggleTransitionToDebugScene();
                handled = true;
                break;
            case SpaceSpinOutEvents.EVENT_TOGGLE_BOX2D:
                toggleBox2D();
                handled = true;
                break;
            case SpaceSpinOutEvents.EVENT_INCREASE_SHIP_RPM:
                setShipRPM(getShipRPM() + 1);
                handled = true;
                break;
            case SpaceSpinOutEvents.EVENT_DECREASE_SHIP_RPM:
                setShipRPM(getShipRPM() - 1);
                handled = true;
                break;
            case SpaceSpinOutEvents.EVENT_INCREASE_VIEW_RPM:
                setViewRPM(getViewRPM() + 1);
                handled = true;
                break;
            case SpaceSpinOutEvents.EVENT_DECREASE_VIEW_RPM:
                setViewRPM(getViewRPM() - 1);
                handled = true;
                break;
            case SpaceSpinOutEvents.EVENT_INCREASE_ASTEROID_RPM:
                setAsteroidRPM(getAsteroidRPM() + 1);
                handled = true;
                break;
            case SpaceSpinOutEvents.EVENT_DECREASE_ASTEROID_RPM:
                setAsteroidRPM(getAsteroidRPM() - 1);
                handled = true;
                break;
            case SpaceSpinOutEvents.EVENT_ADD_ASTEROID:
                addAnAsteroid = true;
                handled = true;
                break;
            case SpaceSpinOutEvents.EVENT_ERROR:
                pauseGame();
                handled = true;
                break;
            case SpaceSpinOutEvents.EVENT_TOGGLE_PAUSE:
                isGamePaused = !isGamePaused;
                handled = true;
                break;
            case SpaceSpinOutEvents.EVENT_TRANSITION_TO_GAME_SCENE:
                transitionToGameScene();
                handled = true;
                break;
            case SpaceSpinOutEvents.EVENT_TRANSITION_TO_MAIN_MENU_SCENE:
                transitionToMainMenuScene();
                handled = true;
                break;
            case SpaceSpinOutEvents.EVENT_START_GAME:
                startGame();
                handled = true;
                break;
            case SpaceSpinOutEvents.EVENT_TOGGLE_TRANSITION_TO_LEVEL_COMPLETE_SCENE:
                transitionToLevelCompleteScene();
                break;
            case SpaceSpinOutEvents.EVENT_TOGGLE_TRANSITION_TO_GAME_COMPLETE_SCENE:
                transitionToGameCompleteScene();
                break;
            case SpaceSpinOutEvents.EVENT_TRANSITION_TO_HELP_SCENE:
                transitionToHelpScene();
                break;
            default:
                break;
        }
        return handled;
    }

}
