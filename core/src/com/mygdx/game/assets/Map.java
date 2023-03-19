package com.mygdx.game.assets;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.config.GameConfig;

public class Map {
    private static TiledMap map;
    private static OrthogonalTiledMapRenderer renderer;

    public static void load() {
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("tileset/map.tmx");

        renderer = new OrthogonalTiledMapRenderer(map, 1f/32f);
    }

    public static void render(OrthographicCamera c) {
        renderer.setView(c);
        renderer.render();
    }
}
