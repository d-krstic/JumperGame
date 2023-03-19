package com.mygdx.game.objects;

import java.util.ArrayList;

public class ScoreList {
    private ArrayList<Score> list;

    public ScoreList() {
        list = new ArrayList<Score>();
    }

    public void add(String name, int score) {
        list.add(new Score(name, score));
    }

    public void remove(int index) {
        list.remove(index);
    }

    public int size() {
        return list.size();
    }

    public Score get(int index) {
        return list.get(index);
    }

    public void sort() {
        for(int i=0; i<list.size(); i++) {
            for(int j=0; j<i; j++) {
                if(list.get(j).getScore() < list.get(j+1).getScore()) {
                    Score tmp = new Score(list.get(j).getName(), list.get(j).getScore());
                    list.get(j).set(list.get(j+1).getName(), list.get(j+1).getScore());
                    list.get(j+1).set(tmp.getName(), tmp.getScore());
                }
            }
        }
    }
}
