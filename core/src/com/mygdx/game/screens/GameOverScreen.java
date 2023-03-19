package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.JumperGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.config.GameConfig;

public class GameOverScreen extends ScreenAdapter {
    private final JumperGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;

    private Skin skin;
    private TextureAtlas gameplayAtlas;

    private float duration = 0f;
    public static final float SCREEN_DURATION_IN_SEC = 5f;
    private int s;

    public GameOverScreen(JumperGame game, int score) {
        this.game = game;
        assetManager = game.getAssetManager();
        s = score;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(100 / 255f, 161 / 255f, 104 / 255f, 0f);

        duration += delta;

        // go to the MenuScreen after SCREEN_DURATION_IN_SEC seconds
        if (duration > SCREEN_DURATION_IN_SEC) {
            game.setScreen(new MenuScreen(game));
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        skin = assetManager.get(AssetDescriptors.UI_SKIN);
        gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY);

        stage.addActor(background());
        stage.addActor(jumper());
        stage.addActor(text());
        stage.addActor(score());
        Gdx.input.setInputProcessor(stage);
    }

    private Actor background() {
        Image background = new Image(gameplayAtlas.findRegion(RegionNames.BACKGROUND));
        background.setFillParent(true);
        return background;
    }

    private Actor text() {
        Label l = new Label("Game Over", skin);
        l.setFontScale(8f);
        l.setColor(Color.RED);
        l.setPosition(GameConfig.WIDTH / 2, GameConfig.HEIGHT / 2);
        l.setAlignment(Align.center);

        return l;
    }

    private Actor score() {
        Label l = new Label("Score: " + s, skin);
        l.setFontScale(4f);
        l.setPosition(GameConfig.WIDTH / 2, GameConfig.HEIGHT / 2 - 100);
        l.setAlignment(Align.center);

        return l;
    }

    private Actor jumper() {
        Image j = new Image(gameplayAtlas.findRegion(RegionNames.JUMPER));
        j.setHeight(100);
        j.setWidth(100);
        j.setOrigin(Align.center);
        j.setPosition(GameConfig.WIDTH / 2, GameConfig.HEIGHT);

        j.addAction(Actions.parallel(
                Actions.rotateBy(1080, 3f),
                Actions.moveBy(0, -800, 2f)
        ));

        return j;
    }
}
