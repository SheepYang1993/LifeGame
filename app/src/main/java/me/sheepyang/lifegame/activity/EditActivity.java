package me.sheepyang.lifegame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.orhanobut.hawk.Hawk;

import butterknife.BindView;
import butterknife.OnClick;
import me.sheepyang.lifegame.R;
import me.sheepyang.lifegame.adapter.tcontributisview.PointArraysContributionsViewAdapter;
import me.sheepyang.lifegame.app.Config;
import me.sheepyang.lifegame.entity.Point;
import me.sheepyang.lifegame.widget.TContributionsView;

/**
 * Created by Administrator on 2016-12-20.
 */

public class EditActivity extends BaseActivity implements View.OnClickListener {
    private static final int TO_DEMO_LIST = 1321;
    @BindView(R.id.sample_view)
    TContributionsView mSampleView;
    private Point[][] mSampleData;
    private PointArraysContributionsViewAdapter mSampleAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBarTitle("编辑生物");
        showBack(true);
        initView();
        initListener();
        initData();
    }

    private void initListener() {
        mSampleView.setOnItemClickListener(new TContributionsView.onItemClickListener() {
            @Override
            public void onItemClick(int itemIndexX, int itemIndexY) {
                if (itemIndexX >= 0 && itemIndexY >= 0) {
                    mSampleData[itemIndexY][itemIndexX].setAlive(!mSampleData[itemIndexY][itemIndexX].isAlive());
//                    if (mSampleData[itemIndexY][itemIndexX].getLevel() == 4) {
//                        mSampleData[itemIndexY][itemIndexX].setLevel(0);
//                    } else {
//                        mSampleData[itemIndexY][itemIndexX].setLevel(mSampleData[itemIndexY][itemIndexX].getLevel() + 1);
//                    }
                    mSampleAdapter.updata(mSampleData);
                }
            }
        });
    }

    private void initData() {
        mSampleData = Hawk.get(Config.HAWK_KEY_POINT_LIST);
        mSampleAdapter = new PointArraysContributionsViewAdapter();
        mSampleAdapter.setArrays(mSampleData);
        mSampleView.setAdapter(mSampleAdapter);
    }

    private void initView() {

    }

    public void clearData() {
        //        mSampleData = new Integer[Config.DEFAULT_SAMPLE_HEIGHT][Config.DEFAULT_SAMPLE_WIDTH];
//        for (int i = 0; i < Config.DEFAULT_SAMPLE_HEIGHT; i++) {
//            for (int j = 0; j < Config.DEFAULT_SAMPLE_WIDTH; j++) {
//                mSampleData[i][j] = 0;
//            }
//        }
    }

    @Override
    @OnClick({R.id.btn_ok, R.id.btn_demo_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_demo_list:
                startActivityForResult(new Intent(mContext, DemoListActivity.class), TO_DEMO_LIST);
                break;
            case R.id.btn_ok:
                Hawk.put(Config.HAWK_KEY_POINT_LIST, mSampleData);
                setResult(RESULT_OK);
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TO_DEMO_LIST:
                if (resultCode == RESULT_OK) {

                }
                break;
            default:
                break;
        }
    }
}
