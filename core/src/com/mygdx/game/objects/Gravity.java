package com.mygdx.game.objects;

public class Gravity {
    public static final float GRAVITY = 15f;
    public static float DY = -12f; //controls "gravity" - indicates change of Y per sec
    public static float JUMP_HEIGHT = 12f;

    public static void reset() {
        DY = -12f;
    }
}
