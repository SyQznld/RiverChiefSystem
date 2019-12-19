package com.appler.riverchiefsystem.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.bean.SYQ_SKSWData;

import butterknife.BindView;
import butterknife.OnClick;

public class SYQSkDetailActivity extends BaseActivity {
    @BindView(R.id.tv_skDetail_zhanName)
    TextView tvSkDetailZhanName;
    @BindView(R.id.tv_skDetail_zhengqu)
    TextView tvSkDetailZhengqu;
    @BindView(R.id.tv_skDetail_zhanNum)
    TextView tvSkDetailZhanNum;
    @BindView(R.id.tv_skDetail_ksw)
    TextView tvSkDetailKsw;
    @BindView(R.id.tv_skDetail_xsl)
    TextView tvSkDetailXsl;
    @BindView(R.id.tv_skDetail_kysl)
    TextView tvSkDetailKysl;
    @BindView(R.id.tv_skDetail_skr)
    TextView tvSkDetailSkr;
    @BindView(R.id.tv_skDetail_xxsw)
    TextView tvSkDetailXxsw;
    @BindView(R.id.tv_taskDetail_cxxsw)
    TextView tvTaskDetailCxxsw;
    @BindView(R.id.tv_skDetail_rkll)
    TextView tvSkDetailRkll;
    @BindView(R.id.tv_skDetail_ckll)
    TextView tvSkDetailCkll;
    @BindView(R.id.tv_skDetail_fjylz)
    TextView tvSkDetailFjylz;
    @BindView(R.id.tv_skDetail_shuishi)
    TextView tvSkDetailShuishi;
    @BindView(R.id.tv_skDetail_time)
    TextView tvSkDetailTime;
    @BindView(R.id.tv_skDetail_hxsw)
    TextView tvSkDetailHxsw;
    @BindView(R.id.tv_skDetail_dhxsw)
    TextView tvSkDetailDhxsw;
    @BindView(R.id.tv_skDetail_lszgsw)
    TextView tvSkDetailLszgsw;
    @BindView(R.id.tv_skDetail_clszgsw)
    TextView tvSkDetailClszgsw;
    @BindView(R.id.tv_skDetail_lszdsw)
    TextView tvSkDetailLszdsw;
    @BindView(R.id.tv_skDetail_dlszdsw)
    TextView tvSkDetailDlszdsw;
    private String TAG = getClass().getSimpleName();
    @BindView(R.id.iv_toolbar_tv_back)
    ImageView iv_back;
    @BindView(R.id.tv_toolbar_tv_title)
    TextView tv_title;
    private SYQ_SKSWData.DataBean skswData;

    @Override
    protected int bindLayout() {
        return R.layout.activity_syqsk_detail;
    }

    @Override
    protected void doBuissness(Context context) {
        tv_title.setText("水位详情");
        skswData = getIntent().getParcelableExtra("sk");
        initData(skswData);
    }


    @OnClick({R.id.iv_toolbar_tv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_tv_back:
                finish();
                break;
        }
    }


    @SuppressLint("SetTextI18n")
    private void initData(SYQ_SKSWData.DataBean skswData) {
        tvSkDetailZhanName.setText(skswData.get站名());
        tvSkDetailZhengqu.setText(skswData.get政区());
        tvSkDetailZhanNum.setText(skswData.get站码());
        String ksw = skswData.get库水位();
        tvSkDetailKsw.setText("".equals(ksw) ? ksw : ksw + "m");
        tvSkDetailXsl.setText(skswData.get蓄水量() + "百万m³");
        tvSkDetailKysl.setText(skswData.get可用水量() + "百万m³");
        tvSkDetailSkr.setText(skswData.get死库容() + "百万m³");
        String xxsw = skswData.get汛限水位();
        tvSkDetailXxsw.setText("".equals(xxsw) ? xxsw : xxsw + "m");
        String cxxsw = skswData.get超汛限水位();
        tvTaskDetailCxxsw.setText("".equals(cxxsw) ? cxxsw : cxxsw + "m");
        String hxsw = skswData.get旱限水位();
        tvSkDetailHxsw.setText("".equals(hxsw) ? hxsw : hxsw + "m");
        String dhxsw = skswData.get低旱限水位();
        tvSkDetailDhxsw.setText("".equals(dhxsw) ? dhxsw : dhxsw + "m");
        tvSkDetailLszgsw.setText(skswData.get历史最高水位() + "m");
        tvSkDetailClszgsw.setText(skswData.get超历史最高水位() + "m");
        String lszdsw = skswData.get历史最低水位();
        if (!"".equals(lszdsw)) {
            lszdsw += "m";
        }
        tvSkDetailLszdsw.setText(lszdsw);
        String dlszdsw = skswData.get低历史最低水位();
        if (!"".equals(dlszdsw)) {
            dlszdsw += "m";
        }
        tvSkDetailDlszdsw.setText(dlszdsw);
        String rkll = skswData.get入库流量();
        tvSkDetailRkll.setText("".equals(rkll) ? rkll : rkll + "m³/s");
        tvSkDetailCkll.setText(skswData.get出库流量() + "m³/s");
        tvSkDetailFjylz.setText(skswData.get附近雨量站());
        tvSkDetailShuishi.setText(skswData.get水势());
        tvSkDetailTime.setText(skswData.get时间());
    }


}
