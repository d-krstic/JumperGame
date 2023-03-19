package com.mygdx.game.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.objects.ScoreList;

public class JsonFileHandler {
    private FileHandle fileHandle;

    public JsonFileHandler() {
        //local files do not work in GWT apps
        try {
            fileHandle = Gdx.files.local("scores.json");
            //fileHandle = Gdx.files.internal("backup/scores.json");
        }
        //loads backup file for scores
        catch (GdxRuntimeException e) {
            fileHandle = Gdx.files.internal("backup/scores.json");
        }
    }

    public void writeToFile(ScoreList playerScores) {
        Json json = new Json();
        String text = json.toJson(playerScores);
        try {
            fileHandle.writeString(text, false);
        }
        catch (RuntimeException ignored) {
            //file is internal when GWT but these files are read only
        }
    }

    public ScoreList readFromFile() {
        Json json = new Json();
        ScoreList scores;
        try {
            scores = json.fromJson(ScoreList.class, Gdx.files.local("scores.json"));
        }
        catch (GdxRuntimeException e) {
            scores = json.fromJson(ScoreList.class, Gdx.files.internal("backup/scores.json"));
        }

        return scores;
    }
}
