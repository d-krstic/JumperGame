package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.JumperGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.config.GameConfig;

public class LeaderboardScreen extends ScreenAdapter {
    private final JumperGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;

    private Skin skin;
    private TextureAtlas gameplayAtlas;

    public LeaderboardScreen(JumperGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(65 / 255f, 159 / 255f, 221 / 255f, 0f);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        skin = assetManager.get(AssetDescriptors.UI_SKIN);
        gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY);

        stage.addActor(leaderboardUi());
        Gdx.input.setInputProcessor(stage);
    }

    public Actor leaderboardUi() {
        Table table = new Table();
        table.defaults().pad(25);
        Table scoreTable = new Table();
        Table textArea = new Table();

        TextureRegion backgroundRegion = gameplayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        TextureRegion menuRegion = gameplayAtlas.findRegion(RegionNames.MENU_BACKGROUND);
        scoreTable.setBackground(new TextureRegionDrawable(menuRegion));

        TextureRegion blackRegion = gameplayAtlas.findRegion(RegionNames.BLACK);
        textArea.setBackground(new TextureRegionDrawable(blackRegion));

        //button to return to menu
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });

        //inserts scores (only top 5)
        for(int i = 0; i < game.scores.size() && i < 5; i++) {
            Label lb1 = new Label(game.scores.get(i).toString(), skin);
            lb1.setAlignment(Align.center);
            lb1.setColor(Color.WHITE);
            lb1.setFontScale(1.5f);

            textArea.add(lb1).padBottom(5).padTop(5).expandX().fill().row();
        }

        scoreTable.defaults().padLeft(30).padRight(30);
        scoreTable.padBottom(20).padTop(20);
        scoreTable.align(2);

        scoreTable.add(textArea).align(Align.center).pad(20).expand().fill().row();
        scoreTable.add(backButton).align(Align.bottom).padBottom(15).fill().row();

        table.add(scoreTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }
}
