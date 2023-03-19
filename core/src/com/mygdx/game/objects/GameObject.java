package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class GameObject {
    public Rectangle bounds;
    public Image img;
    public String tag;

    public GameObject(float x, float y, float width, float height, Image i, String t) {
        bounds = new Rectangle();
        bounds.x = x;
        bounds.y = y;
        bounds.width = width;
        bounds.height = height;
        img = i;
        img.setPosition(x, y);
        img.setWidth(width);
        img.setHeight(height);
        tag = t;
    }

    public GameObject() {
        bounds = new Rectangle();
        bounds.x = 0;
        bounds.y = 0;
        bounds.width = 1;
        bounds.height = 1;
    }

    public abstract void draw(SpriteBatch batch);
    public abstract void update(float deltaTime);
}
