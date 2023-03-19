package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.config.GameConfig;

import java.util.Random;

public class Enemy extends GameObject {
    private int type; //description below
    private Vector2 centerPoint; //used for type 4
    private float currentOrbitDegrees;

    private static final Random rnd = new Random();
    public static long lastEnemyTime;
    public static final long CREATE_ENEMY_TIME = 10000000000L;

    public Enemy(Image i, int t) {
        super(-1, -1, 1, 1 ,i ,"en");
        type = t;
        centerPoint = new Vector2();
        currentOrbitDegrees = 0;
    }

    public void update(float deltaTime) {
        bounds.y += (Gravity.DY * deltaTime);

        //updates x and y depending on type
        if(type == 3) {
            bounds.y -= 2f * deltaTime;
        }
        else if(type == 4) {
            centerPoint.y += (Gravity.DY * deltaTime);
            currentOrbitDegrees += 2f;
            if(currentOrbitDegrees > 360) currentOrbitDegrees = 0;
            float radians = (float) Math.toRadians(currentOrbitDegrees);

            bounds.x = (float) ((Math.cos(radians) * 2f) + centerPoint.x);
            bounds.y = (float) ((Math.sin(radians) * 2f) + centerPoint.y);
        }
        else if(type == 5) {
            bounds.y -= 1.5f * deltaTime;
            bounds.x -= 1.5f * deltaTime;
        }
        else if(type == 6) {
            bounds.y += 8f * deltaTime;
        }
        else if(type == 7) {
            bounds.x -= 4f * deltaTime;
        }

        img.setPosition(bounds.x, bounds.y); //updates img position to match bounds pos
    }

    public void setSpawnPoint(float x, float y) { //each type of enemy has a different spawn point dependent of their movement
        if(type == 1) {
            bounds.x = x;
            bounds.y = y;
        }
        else if(type == 2 || type == 3) {
            bounds.x = 0 + rnd.nextFloat() * (GameConfig.WORLD_WIDTH - 1);
            bounds.y = GameConfig.WORLD_HEIGHT;
        }
        else if(type == 4) {
            centerPoint.x = 0 + rnd.nextFloat() * (GameConfig.WORLD_WIDTH - 1);
            centerPoint.y = GameConfig.WORLD_HEIGHT;
        }
        else if(type == 5) {
            bounds.x = GameConfig.WORLD_WIDTH;
            bounds.y = GameConfig.WORLD_HEIGHT;
        }
        else if(type == 6) {
            bounds.x = 0 + rnd.nextFloat() * (GameConfig.WORLD_WIDTH - 1);
            bounds.y = -1;
        }
        else if(type == 7) {
            bounds.x = GameConfig.WORLD_WIDTH;
            bounds.y = 4 + rnd.nextFloat() * (GameConfig.WORLD_HEIGHT - 4);
        }
        else if(type == 8) {
            this.type = rnd.nextInt(8);
            this.setSpawnPoint(x, y);
        }
    }

    public void setImage(Image i) {
        img = i;
    }

    public void setType(int t) {
        type = t;
    }

    public static Boolean isTimeToCreateNew() {
        if(TimeUtils.nanoTime() - lastEnemyTime > CREATE_ENEMY_TIME) {
            return true;
        }
        return false;
    }

    @Override
    public void draw(SpriteBatch batch) {
        img.draw(batch, 1f);
    }
}

//1 - on platform (needs to be jumped on)
//2 - random static spot
//3 - down speed
//4 - circular motion
//5 - diagnol moving
//6 - from below
//7 - from side
//8 - totally random (copies one moveset) :)