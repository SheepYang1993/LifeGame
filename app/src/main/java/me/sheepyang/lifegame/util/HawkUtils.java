package me.sheepyang.lifegame.util;

import com.orhanobut.hawk.Hawk;

import me.sheepyang.lifegame.app.Config;

/**
 * Created by Administrator on 2016-12-22.
 */

public class HawkUtils {
    public static void putSampleWidth(int width) {
        Hawk.put(Config.HAWK_KEY_SAMPLE_WIDTH, width);
    }

    public static int getSampleWidth() {
        Integer sampleWidth = Hawk.get(Config.HAWK_KEY_SAMPLE_WIDTH);
        return sampleWidth == null ? Config.DEFAULT_SAMPLE_WIDTH : sampleWidth;
    }

    public static void putSampleHeight(int height) {
        Hawk.put(Config.HAWK_KEY_SAMPLE_HEIGHT, height);
    }

    public static int getSampleHeight() {
        Integer sampleHeight = Hawk.get(Config.HAWK_KEY_SAMPLE_HEIGHT);
        return sampleHeight == null ? Config.DEFAULT_SAMPLE_HEIGHT : sampleHeight;
    }

    public static void putGameWidth(int width) {
        Hawk.put(Config.HAWK_KEY_GAME_WIDTH, width);
    }

    public static int getGameWidth() {
        Integer GameWidth = Hawk.get(Config.HAWK_KEY_GAME_WIDTH);
        return GameWidth == null ? Config.DEFAULT_GAME_WIDTH : GameWidth;
    }

    public static void putGameHeight(int height) {
        Hawk.put(Config.HAWK_KEY_GAME_HEIGHT, height);
    }

    public static int getGameHeight() {
        Integer gameHeight = Hawk.get(Config.HAWK_KEY_GAME_HEIGHT);
        return gameHeight == null ? Config.DEFAULT_GAME_HEIGHT : gameHeight;
    }

    public static void putGameSpeed(int speed) {
        Hawk.put(Config.HAWK_KEY_GAME_SPEED, speed);
    }

    public static int getGameSpeed() {
        Integer gameSpeed = Hawk.get(Config.HAWK_KEY_GAME_SPEED);
        return gameSpeed == null ? Config.DEFAULT_GAME_SPEED : gameSpeed;
    }
}
