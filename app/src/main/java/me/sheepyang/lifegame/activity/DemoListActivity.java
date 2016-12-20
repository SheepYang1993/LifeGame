package me.sheepyang.lifegame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import me.sheepyang.lifegame.R;

/**
 * Created by Administrator on 2016-12-20.
 */

public class DemoListActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_demo_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBarTitle("生物图鉴");
        showBack(true);
    }
}
