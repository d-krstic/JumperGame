package com.mygdx.game.pools;

import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.assets.Atlas;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.objects.Platform;

public class PlatformPool extends Pool<Platform> {
    public PlatformPool(int init, int max){
        super(init, max);
    }

    public PlatformPool(){
        super();
    }

    @Override
    protected Platform newObject() {
        return new Platform(Atlas.getImage(RegionNames.PLATFORM_1));
    }
}
