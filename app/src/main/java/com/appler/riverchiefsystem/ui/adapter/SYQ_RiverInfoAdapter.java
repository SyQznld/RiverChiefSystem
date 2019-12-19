package com.appler.riverchiefsystem.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.bean.SYQ_RiverData;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 水情
 */
public class SYQ_RiverInfoAdapter extends BaseQuickAdapter<SYQ_RiverData.DataBean, BaseViewHolder> {
    private Context context;
    private List<SYQ_RiverData.DataBean> data;

    public SYQ_RiverInfoAdapter(Context context, @Nullable List<SYQ_RiverData.DataBean> data) {
        super(R.layout.syq_river_item, data);
        this.context = context;
        this.data = data;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(final BaseViewHolder helper, final SYQ_RiverData.DataBean item) {
        TextView tv_river_name = helper.getView(R.id.tv_river_name);
        TextView tv_river_flow = helper.getView(R.id.tv_river_flow);
        TextView tv_river_shuishi = helper.getView(R.id.tv_river_shuishi);
        TextView tv_river_time = helper.getView(R.id.tv_river_time);
        TextView tv_river_shuiwei = helper.getView(R.id.tv_river_shuiwei);
        TextView tv_river_jjsw = helper.getView(R.id.tv_river_jjsw);

        tv_river_name.setText(item.get所在河道() + "-" + item.get站名());
        tv_river_flow.setText("流量：" + item.get流量() + " m³/s");
        tv_river_shuishi.setText("水势：" + item.get水势());
        tv_river_time.setText("时间：" + item.get时间());
        String sw = item.get水位();
        String s = "".equals(sw) ? sw : sw + " m";
        tv_river_shuiwei.setText("水位：" + s);
        String jjsw = item.get警戒水位();
        tv_river_jjsw.setText("警戒水位：" + ("".equals(jjsw) ? jjsw : jjsw + " m"));
    }
}