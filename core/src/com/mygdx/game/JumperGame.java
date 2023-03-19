package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.common.JsonFileHandler;
import com.mygdx.game.objects.ScoreList;
import com.mygdx.game.screens.IntroScreen;

public class JumperGame extends Game {
	private AssetManager assetManager;
	private SpriteBatch batch;

	public ScoreList scores;
	private JsonFileHandler file;

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		assetManager = new AssetManager();
		assetManager.getLogger().setLevel(Logger.DEBUG);

		batch = new SpriteBatch();
		scores = new ScoreList();

		//opens file and reads from it
		file = new JsonFileHandler();
		scores = file.readFromFile();
		scores.sort();

		setScreen(new IntroScreen(this));
	}

	public void checkHighscores(int s) {
		scores.add("Denis", s);
		scores.sort();
		scores.remove(5);
		file.writeToFile(scores);
	}

	@Override
	public void dispose() {
		assetManager.dispose();
		batch.dispose();
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public SpriteBatch getBatch() {
		return batch;
	}
}
