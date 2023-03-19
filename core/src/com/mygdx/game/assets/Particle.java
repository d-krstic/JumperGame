package com.mygdx.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Particle {
    private static ParticleEffect pEffectHit;
    private static Boolean indicatorHit;

    public static void load(TextureAtlas atlas) {
        pEffectHit = new ParticleEffect();
        pEffectHit.load(Gdx.files.internal("particles/hit.pe"), atlas);
        indicatorHit = false;
    }

    public static void update(float deltaTime, float x, float y) {
        pEffectHit.update(deltaTime);
        if(indicatorHit) {
            if (pEffectHit.isComplete()) {
                pEffectHit.reset();
            }
            indicatorHit = !indicatorHit;
        }
        pEffectHit.setPosition(x, y);
    }

    public static void play() {
        indicatorHit = !indicatorHit;
    }

    public static void draw(SpriteBatch batch) {
        pEffectHit.draw(batch);
    }

    public static void dispose() {
        pEffectHit.dispose();
    }
}
