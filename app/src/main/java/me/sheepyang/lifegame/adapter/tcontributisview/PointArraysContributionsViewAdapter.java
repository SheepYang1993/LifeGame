package me.sheepyang.lifegame.adapter.tcontributisview;

import me.sheepyang.lifegame.entity.Point;

/**
 * https://github.com/barryhappy
 * Created by Barry on 2016/11/22
 */

public class PointArraysContributionsViewAdapter extends AbstractArraysContributionsViewAdapter<Point> {
    public PointArraysContributionsViewAdapter() {
        super();
    }

    public PointArraysContributionsViewAdapter(Point[][] mArrays) {
        super(mArrays);
    }

    @Override
    protected int mapLevel(Point from) {
        return from == null ? 0 : from.getLevel();
    }
}
