package me.sheepyang.lifegame.entity;

/**
 * Created by SheepYang on 2016/12/18 21:11.
 */

public class Point {
    private boolean isAlive;
    private int level;

    public Point(boolean isAlive) {
        setAlive(isAlive);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
        if (alive) {
            level = 1;
        } else {
            level = 0;
        }
    }
}
