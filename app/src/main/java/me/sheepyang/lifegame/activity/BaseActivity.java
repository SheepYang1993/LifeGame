package me.sheepyang.lifegame.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import me.sheepyang.lifegame.util.LogUtils;
import me.sheepyang.lifegame.util.ToastUtils;


/**
 * Created by SheepYang on 2016/12/18 21:18.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

    public abstract
    @LayoutRes
    int getLayoutId();

    public void showToast(int resId) {
        if (!this.isFinishing()) {
            ToastUtils.showShortToast(this, resId);
        }
    }

    public void showToast(String msg) {
        if (!this.isFinishing()) {
            ToastUtils.showShortToast(this, msg);
        }
    }

    public void log(Object msg) {
        LogUtils.i(msg);
    }
}
