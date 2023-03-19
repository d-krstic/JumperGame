package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.config.GameConfig;

import java.util.Random;

public class Platform extends GameObject implements Pool.Poolable {

    public static float NEW_HEIGHT = 0;
    public static int HIGHEST_PLATFORM = 4; //index of currently highest platform
    public static int LOWEST_PLATFORM = 0; //index of currently lowest platform
    private static final Random rnd = new Random();

    public Platform(Image i) {
        super(0 + rnd.nextFloat() * (GameConfig.WORLD_WIDTH - 1), NEW_HEIGHT, 1, 0.5f, i, "pl");
        NEW_HEIGHT += 3;
    }

    public void update(float deltaTime) {
        bounds.y += (Gravity.DY * deltaTime);
        img.setPosition(bounds.x, bounds.y); //updates img position to match bounds pos
    }

    @Override
    public void draw(SpriteBatch batch) {
        img.draw(batch, 1f);
    }

    @Override
    public void reset() {
        bounds.x = rnd.nextFloat() * (GameConfig.WORLD_WIDTH - 1);
        bounds.y = NEW_HEIGHT;
        bounds.width = 1;
        bounds.height = 0.5f;
    }
}
