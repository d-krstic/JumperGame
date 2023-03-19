package com.mygdx.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Atlas {
    private static TextureAtlas atlas;

    public static void setAtlas(AssetManager manager){
        atlas = manager.get(AssetDescriptors.GAMEPLAY);
    }

    public static TextureAtlas getAtlas() {
        return atlas;
    }

    public static Image getImage(String name) {
        return new Image(atlas.findRegion(name));
    }
}
