package me.sheepyang.lifegame.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import me.sheepyang.lifegame.R;

/**
 * Created by SheepYang on 2016/12/18 21:05.
 */

public class GameView extends LinearLayout {
    private Context mContext;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mScreenOldWidth;
    private int mScreenOldHeight;

    public GameView(Context context) {
        this(context, null);
    }

    public GameView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        //设置背景颜色
        setBackgroundColor(mContext.getResources().getColor(R.color.background_white));
    }

    @Override
    //view大小变化，这个方法就被执行
    //将屏幕固定为垂直显示以后，就只会执行一次，在第一次创建Activity的时候执行
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("SheepYang", "onSizeChanged");
        mScreenWidth = w;
        mScreenHeight = h;
        mScreenOldWidth = oldw;
        mScreenOldHeight = oldh;
    }
}
