package com.appler.riverchiefsystem.ui.activity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.bean.ChargeRoadsData;

import butterknife.BindView;
import butterknife.OnClick;

public class RoadDetailActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_tv_back)
    ImageView iv_back;
    @BindView(R.id.tv_toolbar_tv_title)
    TextView tv_title;
    @BindView(R.id.tv_roadDetail_name)
    TextView tv_name;
    @BindView(R.id.tv_roadDetail_urban)
    TextView tv_urban;
    @BindView(R.id.tv_roadDetail_begin)
    TextView tv_begin;
    @BindView(R.id.tv_roadDetail_end)
    TextView tv_end;
    @BindView(R.id.tv_roadDetail_type)
    TextView tv_type;
    private String TAG = getClass().getSimpleName();
    private ChargeRoadsData.DataBean dataBean;


    @Override
    protected int bindLayout() {
        return R.layout.activity_road_detail;
    }

    @Override
    protected void doBuissness(Context context) {
        dataBean = getIntent().getParcelableExtra("road");

        tv_title.setText("道路详情");
        tv_name.setText(dataBean.getRoad_name());
//        tv_urban.setText(dataBean.get());
        tv_begin.setText(dataBean.getRoad_begin());
        tv_end.setText(dataBean.getRoad_over());
        tv_type.setText(dataBean.getRoad_type());
    }

    @OnClick({R.id.iv_toolbar_tv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_tv_back:
                finish();
                break;
        }
    }
}
