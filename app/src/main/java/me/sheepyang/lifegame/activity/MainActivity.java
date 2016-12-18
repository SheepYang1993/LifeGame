package me.sheepyang.lifegame.activity;

import android.os.Bundle;

import butterknife.BindView;
import me.sheepyang.lifegame.R;
import me.sheepyang.lifegame.widget.GameView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.game_view)
    GameView mGameView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {

    }
}
