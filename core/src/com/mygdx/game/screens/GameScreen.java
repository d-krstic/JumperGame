package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.JumperGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.Map;
import com.mygdx.game.assets.Particle;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.assets.SoundEffect;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.config.ViewportUtils;
import com.mygdx.game.objects.Enemy;
import com.mygdx.game.objects.GameObject;
import com.mygdx.game.objects.Gravity;
import com.mygdx.game.objects.Jumper;
import com.mygdx.game.objects.Platform;

import java.util.Random;

public class GameScreen extends ScreenAdapter {
    private final JumperGame game;
    private final AssetManager assetManager;
    private static final Logger log = new Logger(ViewportUtils.class.getName(), Logger.DEBUG);

    private Viewport viewport;
    private Stage stage;
    private Boolean isOver;
    private Random rnd;
    private int currentScore;

    private Skin skin;
    private TextureAtlas gameplayAtlas;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SoundEffect bSoundEffect;
    private BitmapFont font;

    //objects
    Image background;
    Image jumperImage;
    Image[] enemyImages = new Image[8];
    Jumper jumper;
    Enemy enemy;
    private Array<GameObject> dynamicObjects;

    public GameScreen(JumperGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
        Gravity.reset();
        show();
    }

    //called when onLoad
    public void show() {
        log.debug("show :)");

        //create camera and SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, (float) Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight());
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        stage = new Stage(viewport, game.getBatch());
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        font.getData().setScale(2f);

        isOver = false;
        currentScore = 0;

        skin = assetManager.get(AssetDescriptors.UI_SKIN);
        gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY);
        rnd = new Random();

        Particle.load(gameplayAtlas);
        Map.load();
        bSoundEffect = new SoundEffect("bounce.mp3");

        //sets background
        background = new Image(gameplayAtlas.findRegion(RegionNames.BACKGROUND));
        background.setWidth(GameConfig.WORLD_WIDTH);
        background.setHeight(GameConfig.WORLD_HEIGHT);

        //loads enemy images
        enemyImages[0] = new Image(gameplayAtlas.findRegion(RegionNames.ENEMY_1));
        enemyImages[1] = new Image(gameplayAtlas.findRegion(RegionNames.ENEMY_2));
        enemyImages[2] = new Image(gameplayAtlas.findRegion(RegionNames.ENEMY_3));
        enemyImages[3] = new Image(gameplayAtlas.findRegion(RegionNames.ENEMY_4));
        enemyImages[4] = new Image(gameplayAtlas.findRegion(RegionNames.ENEMY_5));
        enemyImages[5] = new Image(gameplayAtlas.findRegion(RegionNames.ENEMY_6));
        enemyImages[6] = new Image(gameplayAtlas.findRegion(RegionNames.ENEMY_7));
        enemyImages[7] = new Image(gameplayAtlas.findRegion(RegionNames.ENEMY_8));

        jumperImage = new Image(gameplayAtlas.findRegion(RegionNames.JUMPER));
        dynamicObjects = new Array<GameObject>();
        jumper = new Jumper(jumperImage);
        enemy = new Enemy(enemyImages[5], 6);
        enemy.setSpawnPoint(0, 0);
        Enemy.lastEnemyTime = TimeUtils.nanoTime();

        dynamicObjects.clear();
        spawnInitialPlatforms();
    }

    //called every frame
    public void render(float delta) {
        ScreenUtils.clear(100 / 255f, 161 / 255f, 104 / 255f, 0f);

        if (isOver) {
            log.debug("Game Over :)");
            gameOver();
        }

        //check input
        if (Gdx.input.isKeyPressed(Input.Keys.A)) jumper.move(false, Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.D)) jumper.move(true, Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyJustPressed((Input.Keys.B))) gameOver();

        //updates values
        updateDy(Gdx.graphics.getDeltaTime());
        jumper.update(Gdx.graphics.getDeltaTime());
        enemy.update(Gdx.graphics.getDeltaTime());
        Particle.update(Gdx.graphics.getDeltaTime(), jumper.bounds.x, jumper.bounds.y);

        //checks for bounces and updates platforms pos
        for (GameObject obj : dynamicObjects) {
            obj.update(Gdx.graphics.getDeltaTime());
            if (jumper.bounds.overlaps(obj.bounds) && jumper.bounds.y > obj.bounds.y && Gravity.DY > 0) {
                Particle.play();
                bounce();
            }
        }

        //checks for enemy collision (CHANGE THIS)
        if (jumper.bounds.overlaps(enemy.bounds) || dynamicObjects.get(Platform.LOWEST_PLATFORM).bounds.y > jumper.bounds.y + 2) {
            isOver = true;
        }

        //reuses platforms below the screen
        for (int i = 0; i < dynamicObjects.size; i++) {
            if (dynamicObjects.get(i).bounds.y < -1) {
                reusePlatform(i);
            }
        }

        //spawns enemy if needed
        if (Enemy.isTimeToCreateNew()) {
            spawnEnemy();
        }

        //draws with 1st viewport
        viewport.apply();
        game.getBatch().setProjectionMatrix(viewport.getCamera().combined);
        game.getBatch().begin();
        {
            //background.draw(game.getBatch(), 1f);
            Map.render(camera);
            for (GameObject obj : dynamicObjects) {
                obj.draw(game.getBatch());
            }
            enemy.draw(game.getBatch());
            jumper.draw(game.getBatch());
            Particle.draw(game.getBatch());
        }
        game.getBatch().end();

        //debug
        // print rectangles
        /*shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        {
            shapeRenderer.setColor(1, 0, 0, 1);
            for (GameObject act : dynamicObjects) {
                shapeRenderer.rect(act.bounds.x, act.bounds.y, act.bounds.width, act.bounds.height);
            }

            shapeRenderer.rect(jumper.bounds.x, jumper.bounds.y, jumperImage.getWidth(), jumperImage.getHeight());
            shapeRenderer.rect(enemy.bounds.x, enemy.bounds.y, enemy.bounds.width, enemy.bounds.height);
        }
        shapeRenderer.end();*/
    }

    public void updateDy(float deltaTime) {
        Gravity.DY += Gravity.GRAVITY * deltaTime;
    }

    public void bounce() {
        Gravity.DY = Gravity.JUMP_HEIGHT * (-1);
        bSoundEffect.play();
        currentScore++;
    }

    public void gameOver() {
        game.checkHighscores(currentScore);
        this.dispose();
        game.setScreen(new GameOverScreen(game, currentScore));
    }

    public void reusePlatform(int x) {
        dynamicObjects.get(x).bounds.y = dynamicObjects.get(Platform.HIGHEST_PLATFORM).bounds.y + 3;
        dynamicObjects.get(x).bounds.x = 0 + rnd.nextFloat() * (GameConfig.WORLD_WIDTH - 1);

        Platform.HIGHEST_PLATFORM++;
        Platform.LOWEST_PLATFORM++;
        if(Platform.HIGHEST_PLATFORM == 5) {
            Platform.HIGHEST_PLATFORM = 0;
        }
        if(Platform.LOWEST_PLATFORM == 5) {
            Platform.LOWEST_PLATFORM = 0;
        }
    }

    public void spawnInitialPlatforms() { //creates 5 platforms
        Platform.NEW_HEIGHT = 0f;
        Platform.LOWEST_PLATFORM = 0;
        Platform.HIGHEST_PLATFORM = 4;
        for(int i=0; i<5; i++) {
            dynamicObjects.add(new Platform(new Image(gameplayAtlas.findRegion(RegionNames.PLATFORM_1))));
            log.debug(String.valueOf(dynamicObjects.get(i).bounds.y));
        }

        log.debug("new_height: " + Platform.NEW_HEIGHT + " obj: " + dynamicObjects.size);
    }

    public void spawnEnemy() {
        int rndType = rnd.nextInt(8);
        enemy = new Enemy(enemyImages[rndType], rndType + 1); //overwrites enemy object
        enemy.setSpawnPoint(dynamicObjects.get(Platform.HIGHEST_PLATFORM).bounds.x, dynamicObjects.get(Platform.HIGHEST_PLATFORM).bounds.y);
        Enemy.lastEnemyTime = TimeUtils.nanoTime();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        ViewportUtils.debugPixelsPerUnit(viewport);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        Particle.dispose();
    }
}
