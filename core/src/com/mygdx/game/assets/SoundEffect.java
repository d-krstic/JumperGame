package com.mygdx.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundEffect {
    private Sound soundEffectBounce;

    public SoundEffect(String fileName) {
        soundEffectBounce = Gdx.audio.newSound(Gdx.files.internal("sounds/" + fileName));
    }

    public void play() {
        soundEffectBounce.play();
    }
}
