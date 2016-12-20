package me.sheepyang.lifegame.app;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

import me.sheepyang.lifegame.entity.Point;

/**
 * Created by Administrator on 2016-12-20.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
        if (Hawk.get(Config.HAWK_KEY_POINT_LIST) == null) {
            initSampleData();
        }
    }

    private void initSampleData() {
        Hawk.put(Config.HAWK_KEY_POINT_LIST, new Point[][]{
                {new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(true), new Point(true), new Point(false), new Point(false), new Point(false), new Point(true), new Point(true), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(true), new Point(true), new Point(false), new Point(false), new Point(false), new Point(true), new Point(true), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(true), new Point(true), new Point(false), new Point(false), new Point(false), new Point(true), new Point(true), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(true), new Point(true), new Point(false), new Point(false), new Point(false), new Point(true), new Point(true), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false)}
        });
    }
}
