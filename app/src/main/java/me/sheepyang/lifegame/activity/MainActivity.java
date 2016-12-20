package me.sheepyang.lifegame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.orhanobut.hawk.Hawk;

import butterknife.BindView;
import butterknife.OnClick;
import me.sheepyang.lifegame.R;
import me.sheepyang.lifegame.adapter.tcontributisview.PointArraysContributionsViewAdapter;
import me.sheepyang.lifegame.app.Config;
import me.sheepyang.lifegame.entity.Point;
import me.sheepyang.lifegame.util.AppUtil;
import me.sheepyang.lifegame.widget.TContributionsView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final int TO_EDIT = 12345;
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
    private long mGameSpeed = Config.GAME_SPEED;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int count;
            if (mGameData != null && mGameData.length > 0) {
                Point[][] tempData = new Point[Config.DEFAULT_GAME_HEIGHT][Config.DEFAULT_GAME_WIDTH];

                for (int i = 0; i < Config.DEFAULT_GAME_HEIGHT; i++) {
                    for (int j = 0; j < Config.DEFAULT_GAME_WIDTH; j++) {
                        Point point = mGameData[i][j];
                        if (point != null) {
                            count = 0;
                            for (int k = i - 1; k < i + 2; k++) {
                                for (int l = j - 1; l < j + 2; l++) {
                                    if (k >= 0 && l >= 0 && k < Config.DEFAULT_GAME_HEIGHT && l < Config.DEFAULT_GAME_WIDTH) {
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
            mGameView.postDelayed(mRunnable, mGameSpeed);
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBarTitle("生命游戏");
        initView();
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

    private void initView() {
        mSampleView.setItemHeight(AppUtil.dip2px(mContext, (150 - Config.DEFAULT_GAME_WIDTH) / Config.DEFAULT_GAME_WIDTH));
        mSampleView.setItemWidth(AppUtil.dip2px(mContext, (150 - Config.DEFAULT_GAME_WIDTH) / Config.DEFAULT_GAME_WIDTH));
    }

    private void initData() {
        getSampleData();
        initGameData();
    }

    private void getSampleData() {
        mSampleData = Hawk.get(Config.HAWK_KEY_POINT_LIST);
        mSampleAdapter = new PointArraysContributionsViewAdapter();
        mSampleAdapter.setArrays(mSampleData);
        mSampleView.setAdapter(mSampleAdapter);
    }

    private void initGameData() {
        mGameData = new Point[Config.DEFAULT_GAME_HEIGHT][Config.DEFAULT_GAME_WIDTH];
        for (int i = 0; i < Config.DEFAULT_GAME_HEIGHT; i++) {
            for (int j = 0; j < Config.DEFAULT_GAME_WIDTH; j++) {
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
                            for (int i = 0; i < Config.DEFAULT_SAMPLE_HEIGHT; i++) {
                                for (int j = 0; j < Config.DEFAULT_SAMPLE_WIDTH; j++) {
                                    if (i < Config.DEFAULT_GAME_HEIGHT && j < Config.DEFAULT_GAME_WIDTH) {
                                        mGameData[i][j] = mSampleData[i][j];
                                    }
                                }
                            }
                            mGameAdapter.updata(mGameData);
                            isFirstStart = false;
                        }
                    }
                    isStart = true;
                    mGameView.postDelayed(mRunnable, mGameSpeed);
                }
                break;
            case R.id.btn_stop:// 停止游戏
                stopGame();
                break;
            case R.id.btn_reset:
                mGameSpeed = Config.GAME_SPEED;
                break;
            case R.id.btn_speed_up:// 加速
                if (mGameSpeed > 50) {
                    mGameSpeed -= Config.EACH_SPEED;
                }
                mGameView.removeCallbacks(mRunnable);
                mGameView.postDelayed(mRunnable, mGameSpeed);
                break;
            case R.id.btn_speed_down:// 减速
                if (mGameSpeed < 3000) {
                    mGameSpeed += Config.EACH_SPEED;
                }
                mGameView.removeCallbacks(mRunnable);
                mGameView.postDelayed(mRunnable, mGameSpeed);
                break;
            case R.id.btn_setting:
                startActivity(new Intent(mContext, SettingActivity.class));
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
            default:
                break;
        }
    }

    private void pauseGame() {
        isStart = false;
        mGameView.removeCallbacks(mRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isStart) {
            mGameView.postDelayed(mRunnable, mGameSpeed);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGameView.removeCallbacks(mRunnable);
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
