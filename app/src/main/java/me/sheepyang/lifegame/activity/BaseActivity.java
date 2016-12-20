package me.sheepyang.lifegame.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;

import butterknife.ButterKnife;
import me.sheepyang.lifegame.util.LogUtils;
import me.sheepyang.lifegame.util.ToastUtils;


/**
 * Created by SheepYang on 2016/12/18 21:18.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = this;
        ButterKnife.bind(this);
    }

    public abstract
    @LayoutRes
    int getLayoutId();

    public void showBack(boolean isShow) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShow);
    }

    public void setBarTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            getSupportActionBar().setTitle(title);
        }
    }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void log(Object msg) {
        LogUtils.i(msg);
    }
}
