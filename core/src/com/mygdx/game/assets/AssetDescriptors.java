package com.mygdx.game.assets;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetDescriptors {
    public static final com.badlogic.gdx.assets.AssetDescriptor<BitmapFont> UI_FONT =
            new com.badlogic.gdx.assets.AssetDescriptor<BitmapFont>(AssetPaths.UI_FONT, BitmapFont.class);

    public static final com.badlogic.gdx.assets.AssetDescriptor<Skin> UI_SKIN =
            new com.badlogic.gdx.assets.AssetDescriptor<Skin>(AssetPaths.UI_SKIN, Skin.class);

    public static final com.badlogic.gdx.assets.AssetDescriptor<TextureAtlas> GAMEPLAY =
            new com.badlogic.gdx.assets.AssetDescriptor<TextureAtlas>(AssetPaths.GAMEPLAY, TextureAtlas.class);

    private void AssetDescriptors() {
    }
}
