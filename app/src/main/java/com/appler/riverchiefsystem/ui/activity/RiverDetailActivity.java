package com.appler.riverchiefsystem.ui.activity;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.bean.ChargeRiversData;

import butterknife.BindView;
import butterknife.OnClick;

public class RiverDetailActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_tv_back)
    ImageView ivToolbarTvBack;
    @BindView(R.id.tv_toolbar_tv_title)
    TextView tvToolbarTvTitle;
    @BindView(R.id.tv_riverDetail_riverName)
    TextView tvRiverDetailRiverName;
    @BindView(R.id.tv_riverDetail_hdmc)
    TextView tvRiverDetailHdmc;
    @BindView(R.id.tv_riverDetail_cdhl)
    TextView tvRiverDetailCdhl;
    @BindView(R.id.tv_riverDetail_xiangzhen)
    TextView tvRiverDetailXiangzhen;
    @BindView(R.id.tv_riverDetail_sjcm)
    TextView tvRiverDetailSjcm;
    @BindView(R.id.tv_riverDetail_chz)
    TextView tvRiverDetailChz;
    @BindView(R.id.tv_riverDetail_zhz)
    TextView tvRiverDetailZhz;
    @BindView(R.id.tv_riverDetail_clxfs)
    TextView tvRiverDetailClxfs;
    @BindView(R.id.tv_riverDetail_zlxfs)
    TextView tvRiverDetailZlxfs;
    @BindView(R.id.tv_tariverDetail_sjhlm)
    TextView tvTariverDetailSjhlm;
    @BindView(R.id.tv_riverDetail_hydd)
    TextView tvRiverDetailHydd;
    @BindView(R.id.tv_riverDetail_hkdd)
    TextView tvRiverDetailHkdd;
    @BindView(R.id.tv_riverDetail_xhy)
    TextView tv_riverDetail_xhy;
    @BindView(R.id.tv_riverDetail_xhylxfs)
    TextView tv_riverDetail_xhylxfs;
    @BindView(R.id.tv_riverDetail_qjy)
    TextView tv_riverDetail_qjy;
    @BindView(R.id.tv_riverDetail_qjylxfs)
    TextView tv_riverDetail_qjylxfs;
    private String TAG = getClass().getSimpleName();

    private ChargeRiversData.DataBean dataBean;


    @Override
    protected int bindLayout() {
        return R.layout.activity_river_detail;
    }

    @Override
    protected void doBuissness(Context context) {
        tvToolbarTvTitle.setText("河湖详情");
        dataBean = getIntent().getParcelableExtra("river");
        initData(dataBean);

    }

    private void initData(ChargeRiversData.DataBean dataBean) {
        tvRiverDetailZhz.setText(dataBean.get镇河长());
        tvRiverDetailRiverName.setText(dataBean.get河流名称());
        tvRiverDetailCdhl.setText(dataBean.get村段河流());
        tvRiverDetailXiangzhen.setText(dataBean.get所在乡镇());
        tvRiverDetailSjcm.setText(dataBean.get所经村名());
        tvRiverDetailZlxfs.setText(dataBean.get镇联系方式());
        tvRiverDetailClxfs.setText(dataBean.get村联系方式());
        tvRiverDetailChz.setText(dataBean.get村河长());
        tvTariverDetailSjhlm.setText(dataBean.get上级河流名());
        tvRiverDetailHydd.setText(dataBean.get河源地点());
        tvRiverDetailHkdd.setText(dataBean.get河口地点());
        tvRiverDetailHdmc.setText(dataBean.get河段名称());
        tv_riverDetail_qjy.setText(dataBean.get清洁员());
        tv_riverDetail_qjylxfs.setText(dataBean.get清洁员联系方式());
        tv_riverDetail_xhy.setText(dataBean.get巡河员());
        tv_riverDetail_xhylxfs.setText(dataBean.get巡河员联系方式());
    }


    @OnClick(R.id.iv_toolbar_tv_back)
    public void onViewClicked() {
        finish();
    }
}
