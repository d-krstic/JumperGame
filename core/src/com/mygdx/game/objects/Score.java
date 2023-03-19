package com.mygdx.game.objects;

public class Score {
    private String name;
    private int score;

    public Score(String n, int s) {
        name = n;
        score = s;
    }

    public Score() {
        name = "";
        score = -1;
    }

    public void set(String n, int s) {
        name = n;
        score = s;
    }

    @Override
    public String toString() {
        return name + ": " + score;
    }

    public int getScore() {return score;}
    public String getName() {return name;}
}
