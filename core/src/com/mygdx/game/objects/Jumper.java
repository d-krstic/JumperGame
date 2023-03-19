package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.config.GameConfig;

public class Jumper extends GameObject {
    public static final float SPEED = 5f;

    public Jumper(Image i) {
        super(GameConfig.WORLD_WIDTH / 2, 3, 1, 1, i, "ju");
    }

    public void move(Boolean dir, float deltaTime) {
        //false - left, true - right
        if(dir) {
            bounds.x += (SPEED * deltaTime);
        }
        else {
            bounds.x -= (SPEED * deltaTime);
        }
    }

    public void update(float deltaTime) {
        img.setPosition(bounds.x, bounds.y); //updates img position to match bounds pos
    }

    public float getY() {
        return img.getY();
    }

    public void draw(SpriteBatch batch) {
        img.draw(batch, 1f);
    }
}
