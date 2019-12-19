package com.appler.riverchiefsystem.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.bean.SYQ_RainData;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 雨情
 * */
public class SYQ_RainInfoAdapter extends BaseQuickAdapter<SYQ_RainData.DataBean, BaseViewHolder> {
    private Context context;
    private List<SYQ_RainData.DataBean> data;

    public SYQ_RainInfoAdapter(Context context, @Nullable List<SYQ_RainData.DataBean> data) {
        super(R.layout.syq_rain_item, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final SYQ_RainData.DataBean item) {
        TextView tv_rain_zhanName = helper.getView(R.id.tv_rain_zhanName);
        TextView tv_rain_xianName = helper.getView(R.id.tv_rain_xianName);
        TextView tv_rain_rainNum = helper.getView(R.id.tv_rain_rainNum);
        TextView tv_rain_date = helper.getView(R.id.tv_rain_date);

        tv_rain_zhanName.setText(item.get站名());
        tv_rain_xianName.setText(item.get县());
        tv_rain_rainNum.setText("累计雨量：" + item.get累计雨量() + "mm");
        tv_rain_date.setText(item.getUploadtime());
    }
}