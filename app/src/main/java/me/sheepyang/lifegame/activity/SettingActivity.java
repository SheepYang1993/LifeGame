package me.sheepyang.lifegame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import me.sheepyang.lifegame.R;
import me.sheepyang.lifegame.app.Config;
import me.sheepyang.lifegame.util.HawkUtils;

/**
 * Created by Administrator on 2016-12-20.
 */

public class SettingActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.tv_game_width)
    TextView tvGameWidth;
    @BindView(R.id.sb_game_width)
    SeekBar sbGameWidth;
    @BindView(R.id.tv_game_height)
    TextView tvGameHeight;
    @BindView(R.id.sb_game_height)
    SeekBar sbGameHeight;
    @BindView(R.id.tv_sample_width)
    TextView tvSampleWidth;
    @BindView(R.id.sb_sample_width)
    SeekBar sbSampleWidth;
    @BindView(R.id.tv_sample_height)
    TextView tvSampleHeight;
    @BindView(R.id.sb_sample_height)
    SeekBar sbSampleHeight;
    @BindView(R.id.tv_game_speed)
    TextView tvGameSpeed;
    @BindView(R.id.sb_game_speed)
    SeekBar sbGameSpeed;
    private int mGameWidth;
    private int mGameHeight;
    private int mSampleWidth;
    private int mSampleHeight;
    private int mGameSpeed;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBarTitle("设置");
        showBack(true);
        initView();
        initListener();
        initData();
    }

    private void initListener() {
        sbGameWidth.setOnSeekBarChangeListener(this);
        sbGameHeight.setOnSeekBarChangeListener(this);
        sbSampleWidth.setOnSeekBarChangeListener(this);
        sbSampleHeight.setOnSeekBarChangeListener(this);
        sbGameSpeed.setOnSeekBarChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ok:
                toSubmit();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toSubmit() {
        int gameWidth = (mGameWidth + Config.MIN_GAME_WIDTH);
        int gameHeight = (mGameHeight + Config.MIN_GAME_HEIGHT);
        int sampleWidth = (mSampleWidth + Config.MIN_SAMPLE_WIDTH);
        int sampleHeight = (mSampleHeight + Config.MIN_SAMPLE_HEIGHT);
        int gameSpeed = (mGameSpeed * 50 + Config.MIN_GAME_SPEED);
        log("gameWidth:" + gameWidth);
        log("gameHeight:" + gameHeight);
        log("sampleWidth:" + sampleWidth);
        log("sampleHeight:" + sampleHeight);
        log("gameSpeed:" + gameSpeed);
        if (sampleWidth > gameWidth) {
            showToast("编辑界面宽度不能 大于 游戏界面宽度");
            return;
        }
        if (sampleHeight > gameHeight) {
            showToast("编辑界面高度不能 大于 游戏界面高度");
            return;
        }
        HawkUtils.putGameWidth(gameWidth);
        HawkUtils.putGameHeight(gameHeight);
        HawkUtils.putSampleWidth(sampleWidth);
        HawkUtils.putSampleHeight(sampleHeight);
        HawkUtils.putGameSpeed(gameSpeed);
        setResult(RESULT_OK);
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mContext, MainActivity.class));
    }

    private void initData() {
        tvGameWidth.setText("游戏界面宽度：" + HawkUtils.getGameWidth());
        tvGameHeight.setText("游戏界面高度：" + HawkUtils.getGameHeight());
        tvSampleWidth.setText("编辑界面宽度：" + HawkUtils.getSampleWidth());
        tvSampleHeight.setText("编辑界面高度：" + HawkUtils.getSampleHeight());
        tvGameSpeed.setText("默认游戏速度：" + HawkUtils.getGameSpeed());
        sbGameWidth.setProgress(HawkUtils.getGameWidth() - Config.MIN_GAME_WIDTH);
        sbGameHeight.setProgress(HawkUtils.getGameHeight() - Config.MIN_GAME_HEIGHT);
        sbSampleWidth.setProgress(HawkUtils.getSampleWidth() - Config.MIN_SAMPLE_WIDTH);
        sbSampleHeight.setProgress(HawkUtils.getSampleHeight() - Config.MIN_SAMPLE_HEIGHT);
        sbGameSpeed.setProgress((HawkUtils.getGameSpeed() - Config.MIN_GAME_SPEED) / 50);
    }

    private void initView() {
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        switch (seekBar.getId()) {
            case R.id.sb_game_width:// 游戏界面宽度
                mGameWidth = progress;
                tvGameWidth.setText("游戏界面宽度：" + (progress + Config.MIN_GAME_WIDTH));
                break;
            case R.id.sb_game_height:// 游戏界面高度
                mGameHeight = progress;
                tvGameHeight.setText("游戏界面高度：" + (progress + Config.MIN_GAME_HEIGHT));
                break;
            case R.id.sb_sample_width:// 编辑界面宽度
                mSampleWidth = progress;
                tvSampleWidth.setText("编辑界面宽度：" + (progress + Config.MIN_SAMPLE_WIDTH));
                break;
            case R.id.sb_sample_height:// 编辑界面高度
                mSampleHeight = progress;
                tvSampleHeight.setText("编辑界面高度：" + (progress + Config.MIN_SAMPLE_HEIGHT));
                break;
            case R.id.sb_game_speed:// 默认游戏速度
                mGameSpeed = progress;
                tvGameSpeed.setText("默认游戏速度：" + (progress * 50 + Config.MIN_GAME_SPEED));
                break;
            default:
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
