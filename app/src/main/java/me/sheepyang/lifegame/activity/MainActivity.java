package me.sheepyang.lifegame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.orhanobut.hawk.Hawk;

import butterknife.BindView;
import butterknife.OnClick;
import me.sheepyang.lifegame.R;
import me.sheepyang.lifegame.adapter.tcontributisview.PointArraysContributionsViewAdapter;
import me.sheepyang.lifegame.app.Config;
import me.sheepyang.lifegame.entity.Point;
import me.sheepyang.lifegame.util.AppManager;
import me.sheepyang.lifegame.util.HawkUtils;
import me.sheepyang.lifegame.widget.TContributionsView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final int TO_EDIT = 12345;
    private static final int TO_SETTING = 12312;
    @BindView(R.id.game_view)
    TContributionsView mGameView;
    @BindView(R.id.sample_view)
    TContributionsView mSampleView;
    @BindView(R.id.btn_start)
    Button btnStart;
    private boolean isStart;// 是否开始游戏了
    private boolean isFirstStart = true;
    private Point[][] mSampleData;
    private PointArraysContributionsViewAdapter mSampleAdapter;
    private Point[][] mGameData;
    private PointArraysContributionsViewAdapter mGameAdapter;
    private long mGameSpeed;
    private long mCurrentTime;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int count;
            if (mGameData != null && mGameData.length > 0) {
                Point[][] tempData = new Point[mGameHeight][mGameWidth];

                for (int i = 0; i < mGameHeight; i++) {
                    for (int j = 0; j < mGameWidth; j++) {
                        Point point = mGameData[i][j];
                        if (point != null) {
                            count = 0;
                            for (int k = i - 1; k < i + 2; k++) {
                                for (int l = j - 1; l < j + 2; l++) {
                                    if (k >= 0 && l >= 0 && k < mGameHeight && l < mGameWidth) {
                                        if (!(k == i && l == j)) {
                                            if (mGameData[k][l].getLevel() > 0) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                            }

                            /*人口过少：当周围低于2个（不包含2个）存活细胞时， 本单元活细胞死亡。
                            稳定：当周围有2个或3个存活细胞时， 本单元细胞保持原样。
                            人口过剩：当周围有3个以上的存活细胞时，本单元活细胞死亡。
                            繁殖：当周围有3个存活细胞时，本单元细胞存活/活化。*/
                            if (count >= 2 && count <= 3) {
                                if (point.isAlive()) {// 保持稳定
                                    tempData[i][j] = new Point(point.getLevel() + 1);
                                } else {
                                    if (count == 3) {// 繁殖
                                        tempData[i][j] = new Point(true);
                                    } else {// 保持稳定
                                        tempData[i][j] = new Point(false);
                                    }
                                }
                            } else {// 人口过少/过剩 -> 死亡
                                tempData[i][j] = new Point(false);
                            }
                        }
                    }
                }
                mGameData = tempData;
                mGameAdapter.updata(mGameData);
            }
            log("mGameSpeed:" + mGameSpeed);
            mHandler.postDelayed(mRunnable, mGameSpeed);
        }
    };
    private int mGameHeight;
    private int mGameWidth;
    private int mSampleWidth;
    private int mSampleHeight;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBarTitle("康威生命游戏");
        initListener();
        initData();
    }

    private void initListener() {
        mSampleView.setOnClickListener(new TContributionsView.onClickListener() {
            @Override
            public void onClick() {
                startActivityForResult(new Intent(mContext, EditActivity.class), TO_EDIT);
            }
        });
    }

    private void initHawkData() {
        mGameWidth = HawkUtils.getGameWidth();
        mGameHeight = HawkUtils.getGameHeight();
        mSampleWidth = HawkUtils.getSampleWidth();
        mSampleHeight = HawkUtils.getSampleHeight();
        mGameSpeed = HawkUtils.getGameSpeed();
    }

    private void initData() {
        initHawkData();
        getSampleData();
        initGameData();
    }

    private void getSampleData() {
        Point[][] tempData = Hawk.get(Config.HAWK_KEY_POINT_LIST);
        mSampleData = new Point[mSampleHeight][mSampleWidth];
        for (int i = 0; i < mSampleHeight; i++) {
            for (int j = 0; j < mSampleWidth; j++) {
                if (tempData != null && i < tempData.length && tempData.length > 0 && tempData[i] != null && j < tempData[i].length && tempData[i].length > 0) {
                    mSampleData[i][j] = tempData[i][j];
                } else {
                    mSampleData[i][j] = new Point(false);
                }
            }
        }
        mSampleAdapter = new PointArraysContributionsViewAdapter();
        mSampleAdapter.setArrays(mSampleData);
        mSampleView.setAdapter(mSampleAdapter);
    }

    private void initGameData() {
        mGameData = new Point[mGameHeight][mGameWidth];
        for (int i = 0; i < mGameHeight; i++) {
            for (int j = 0; j < mGameWidth; j++) {
                mGameData[i][j] = new Point(false);
            }
        }
        mGameAdapter = new PointArraysContributionsViewAdapter();
        mGameAdapter.setArrays(mGameData);
        mGameView.setAdapter(mGameAdapter);
    }

    @Override
    @OnClick({R.id.btn_setting, R.id.btn_reset, R.id.btn_stop, R.id.btn_speed_up, R.id.btn_speed_down, R.id.btn_start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:// 开始游戏
                if (isStart) {
                    btnStart.setText("开始");
                    pauseGame();
                } else {
                    btnStart.setText("暂停");
                    if (isFirstStart) {
                        if (mSampleData != null && mSampleData.length > 0) {
                            initGameData();
                            for (int i = 0; i < mSampleHeight; i++) {
                                for (int j = 0; j < mSampleWidth; j++) {
                                    if (i < mGameHeight && j < mGameWidth && i < mSampleHeight && j < mSampleWidth) {
                                        mGameData[i][j] = mSampleData[i][j];
                                    }
                                }
                            }
                            mGameAdapter.updata(mGameData);
                            isFirstStart = false;
                        }
                    }
                    isStart = true;
                    mHandler.postDelayed(mRunnable, mGameSpeed);
                }
                break;
            case R.id.btn_stop:// 停止游戏
                stopGame();
                break;
            case R.id.btn_reset:
                mGameSpeed = HawkUtils.getGameSpeed();
                break;
            case R.id.btn_speed_up:// 加速
                if (mGameSpeed > 50) {
                    mGameSpeed -= Config.EACH_SPEED;
                }
                break;
            case R.id.btn_speed_down:// 减速
                if (mGameSpeed < 3000) {
                    mGameSpeed += Config.EACH_SPEED;
                }
                break;
            case R.id.btn_setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TO_EDIT:
                if (resultCode == RESULT_OK) {
                    stopGame();
                    getSampleData();
                }
                break;
            case TO_SETTING:
                if (resultCode == RESULT_OK) {
                    stopGame();
                    getSampleData();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mCurrentTime < 2000) {
            mCurrentTime = 0;
            AppManager.getAppManager().AppExit(mContext);
        } else {
            mCurrentTime = System.currentTimeMillis();
            showToast("再次点击退出APP");
        }
    }

    private void pauseGame() {
        isStart = false;
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isStart) {
            mHandler.postDelayed(mRunnable, mGameSpeed);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopGame();
    }

    private void stopGame() {
        btnStart.setText("开始");
        isFirstStart = true;
        pauseGame();
        initGameData();
    }
//    private void useIntegerArraysContributionsAdapterMineCraft(TContributionsView contributionsView) {
//        IntArraysContributionsViewAdapter adapter = new IntArraysContributionsViewAdapter();
//        Integer arrays[][] = {
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 0,},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 2, 3, 4, 0,},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 2, 3, 2, 4, 0,},
//                {0, 0, 0, 0, 0, 0, 0, 0, 4, 2, 3, 2, 4, 0, 0,},
//                {0, 0, 4, 4, 0, 0, 0, 4, 2, 3, 2, 4, 0, 0, 0,},
//                {0, 0, 4, 3, 4, -1, 4, 2, 3, 2, 4, 0, 0, 0, 0,},
//                {0, 0, 0, 4, 3, 4, 2, 3, 2, 4, 0, 0, 0, 0, 0,},
//                {0, 0, 0, 4, 3, 4, 3, 2, 4, 0, 0, 0, 0, 0, 0,},
//                {0, 0, 0, 0, 4, 3, 4, 4, 0, 0, 0, 0, 0, 0, 0,},
//                {0, 0, 0, 1, 1, 4, 3, 3, 4, 0, 0, 0, 0, 0, 0,},
//                {0, 0, 1, 1, 1, 0, 4, 4, 3, 4, 0, 0, 0, 0, 0,},
//                {4, 4, 1, 1, 0, 0, 0, 0, 4, 4, 0, 0, 0, 0, 0,},
//                {4, 1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
//                {4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
//        };
//        adapter.setArrays(arrays);
//        contributionsView.setAdapter(adapter);
//    }
}
