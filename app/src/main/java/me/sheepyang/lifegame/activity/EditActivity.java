package me.sheepyang.lifegame.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import me.sheepyang.lifegame.R;
import me.sheepyang.lifegame.adapter.tcontributisview.PointArraysContributionsViewAdapter;
import me.sheepyang.lifegame.app.Config;
import me.sheepyang.lifegame.entity.Point;
import me.sheepyang.lifegame.util.HawkUtils;
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
    private EditText edtName;
    private AlertDialog.Builder mSaveDialog;

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

    private void initView() {
        edtName = new EditText(mContext);
        mSaveDialog = new AlertDialog.Builder(mContext)
                .setView(edtName)
                .setMessage("请输入生物名称")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (TextUtils.isEmpty(edtName.getText().toString())) {
                            showToast("生物名称不能为空");
                            return;
                        }
                        toSave(edtName.getText().toString());
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
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
        mSampleData = new Point[1][1];
        mSampleAdapter = new PointArraysContributionsViewAdapter();
        mSampleAdapter.setArrays(mSampleData);
        mSampleView.setAdapter(mSampleAdapter);
        getSampleData();
    }

    private void getSampleData() {
        Point[][] tempData = Hawk.get(Config.HAWK_KEY_POINT_LIST);
        mSampleData = new Point[HawkUtils.getSampleHeight()][HawkUtils.getSampleWidth()];
        for (int i = 0; i < HawkUtils.getSampleHeight(); i++) {
            for (int j = 0; j < HawkUtils.getSampleWidth(); j++) {
                if (tempData != null && i < tempData.length && tempData.length > 0 && tempData[i] != null && j < tempData[i].length && tempData[i].length > 0) {
                    mSampleData[i][j] = tempData[i][j];
                } else {
                    mSampleData[i][j] = new Point(false);
                }
            }
        }
        mSampleAdapter.updata(mSampleData);
    }

    public void clearData() {
        mSampleData = new Point[HawkUtils.getSampleHeight()][HawkUtils.getSampleWidth()];
        for (int i = 0; i < HawkUtils.getSampleHeight(); i++) {
            for (int j = 0; j < HawkUtils.getSampleWidth(); j++) {
                mSampleData[i][j] = new Point(false);
            }
        }
        mSampleAdapter.updata(mSampleData);
    }

    @Override
    @OnClick({R.id.btn_save, R.id.btn_demo1, R.id.btn_clear, R.id.btn_ok, R.id.btn_demo_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                mSaveDialog.show();
                break;
            case R.id.btn_demo_list:
                startActivityForResult(new Intent(mContext, DemoListActivity.class), TO_DEMO_LIST);
                break;
            case R.id.btn_ok:
                Hawk.put(Config.HAWK_KEY_POINT_LIST, mSampleData);
                setResult(RESULT_OK);
                onBackPressed();
                break;
            case R.id.btn_clear:
                clearData();
                break;
            case R.id.btn_demo1:
                mSampleData = new Point[][]{
                        {new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false)},
                        {new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false)},
                        {new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(true), new Point(true), new Point(false), new Point(false), new Point(false), new Point(true), new Point(true), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false)},
                        {new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false)},
                        {new Point(false), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(false)},
                        {new Point(false), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(false)},
                        {new Point(false), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(false)},
                        {new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(true), new Point(true), new Point(false), new Point(false), new Point(false), new Point(true), new Point(true), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false)},
                        {new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false)},
                        {new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(true), new Point(true), new Point(false), new Point(false), new Point(false), new Point(true), new Point(true), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false)},
                        {new Point(false), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(false)},
                        {new Point(false), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(false)},
                        {new Point(false), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(false), new Point(false)},
                        {new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false)},
                        {new Point(false), new Point(false), new Point(false), new Point(false), new Point(true), new Point(true), new Point(true), new Point(false), new Point(false), new Point(false), new Point(true), new Point(true), new Point(true), new Point(false), new Point(false), new Point(false), new Point(false)},
                        {new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false)},
                        {new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false), new Point(false)}
                };
                mSampleAdapter.updata(mSampleData);
                break;
            default:
                break;
        }
    }

    private void toSave(String name) {
        Map<String, Point[][]> pointMap = new HashMap<>();
        pointMap.put(name, mSampleData);
        Hawk.put("PointMap", pointMap);
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
