package me.sheepyang.lifegame.activity;

import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import me.sheepyang.lifegame.R;
import me.sheepyang.lifegame.adapter.tcontributisview.PointArraysContributionsViewAdapter;
import me.sheepyang.lifegame.app.Config;
import me.sheepyang.lifegame.entity.Point;
import me.sheepyang.lifegame.widget.TContributionsView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.game_view)
    TContributionsView mGameView;
    @BindView(R.id.sample_view)
    TContributionsView mSampleView;
    private boolean isStart;// 是否开始游戏了
    private boolean isPause;
    private Point[][] mSampleData;
    private PointArraysContributionsViewAdapter mSampleAdapter;
    private Point[][] mGameData;
    private PointArraysContributionsViewAdapter mGameAdapter;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            log("run ----------------------------------");
            int count;
            if (mGameData != null && mGameData.length > 0) {
                for (int i = 0; i < Config.DEFAULT_GAME_HEIGHT; i++) {
                    for (int j = 0; j < Config.DEFAULT_GAME_WIDTH; j++) {
                        Point point = mGameData[i][j];
                        if (point != null) {
                            count = 0;
                            for (int k = i - 1; k < i + 1; k++) {
                                for (int l = j - 1; l < j + 1; l++) {
                                    if (k < Config.DEFAULT_GAME_HEIGHT && l < Config.DEFAULT_GAME_WIDTH && k >= 0 && l >= 0 && k != i && l != j) {
                                        if (mGameData[k][l].isAlive()) {
                                            count++;
                                        }
                                    }
                                }
                            }

                                /*人口过少：当周围低于2个（不包含2个）存活细胞时， 本单元活细胞死亡。
                                稳定：当周围有2个或3个存活细胞时， 本单元细胞保持原样。
                                人口过剩：当周围有3个以上的存活细胞时，本单元活细胞死亡。
                                繁殖：当周围有3个存活细胞时，本单元细胞存活/活化。*/
                            log("count:" + count);
                            if (count >= 2 || count <= 3) {// 死亡
                                mGameData[i][j].setAlive(true);
                            } else {// 存活
                                mGameData[i][j].setAlive(false);
                            }

                            /////////////////////////////////////////////////////////////
                            // 有九种情况，分别是四个顶点，四条边，以及中间部分。
//                                if (i == 0) {// 第一行
//                                    if (j == 0) {// 一、左上角
//
//                                    } else if (j == Config.DEFAULT_GAME_WIDTH - 1) {// 二、右上角
//
//                                    } else {// 三、上边界
//
//                                    }
//                                } else if (i == Config.DEFAULT_GAME_HEIGHT - 1) {// 最后一行
//                                    if (j == 0) {// 四、左下角
//
//                                    } else if (j == Config.DEFAULT_GAME_WIDTH - 1) {// 五、右下角
//
//                                    } else {// 六、下边界
//
//                                    }
//                                } else {
//                                    if (j == 0) {// 七、左边界
//
//                                    } else if (j == Config.DEFAULT_GAME_WIDTH - 1) {// 八、右边界
//
//                                    } else {// 九、中间部分
//
//                                    }
//                                }
                        }
                    }
                }
                showToast("刷新");
                mGameAdapter.updata(mGameData);
            }
            mGameView.postDelayed(mRunnable, Config.GAME_SPEED);
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    private void initListener() {
        mSampleView.setOnClickListener(new TContributionsView.onClickListener() {
            @Override
            public void onClick() {
                showToast("跳转至编辑界面");
            }
        });
        mGameView.setOnItemClickListener(new TContributionsView.onItemClickListener() {
            @Override
            public void onItemClick(int itemIndexX, int itemIndexY) {
                showToast("x:" + itemIndexX + ", y:" + itemIndexY);
            }
        });
    }

    private void initView() {

    }

    private void initData() {
        initSampleData();
        initGameData();
    }

    private void initSampleData() {
//        mSampleData = new Integer[Config.DEFAULT_SAMPLE_HEIGHT][Config.DEFAULT_SAMPLE_WIDTH];
//        for (int i = 0; i < Config.DEFAULT_SAMPLE_HEIGHT; i++) {
//            for (int j = 0; j < Config.DEFAULT_SAMPLE_WIDTH; j++) {
//                mSampleData[i][j] = 0;
//            }
//        }
        mSampleData = new Point[][]{
                {new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(true), new Point(true), new Point(true), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false)},
                {new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false)},
        };
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
    @OnClick({R.id.btn_stop, R.id.btn_speed_up, R.id.btn_speed_down, R.id.btn_start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:// 开始游戏
                if (isStart) {
                    showToast("游戏已经开始了");
                    if (isPause) {
                        isPause = false;
                        mGameView.postDelayed(mRunnable, Config.GAME_SPEED);
                    }
                } else {
                    showToast("开始游戏");
                    initSampleData();
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
                        isStart = true;
                        isPause = false;
                        mGameView.postDelayed(mRunnable, Config.GAME_SPEED);
                    }
                }
                break;
            case R.id.btn_stop:// 暂停游戏
                showToast("暂停游戏");
                isStart = false;
                mGameView.removeCallbacks(mRunnable);
                break;
            case R.id.btn_speed_up:// 加速
                break;
            case R.id.btn_speed_down:// 减速
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isStart = false;
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
