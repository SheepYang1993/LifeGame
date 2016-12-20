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

    public Point(int level) {
        setLevel(level);
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        if (level <= 0) {
            this.level = 0;
        } else {
            this.level = level % 4 == 0 ? 4 : level % 4;
        }
        this.isAlive = (this.level > 0);
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public void setAlive(boolean alive) {
        this.isAlive = alive;
        if (alive) {
            this.level = 1;
        } else {
            this.level = 0;
        }
    }
}
