package com.appler.riverchiefsystem.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.bean.SYQ_SKSWData;
import com.appler.riverchiefsystem.ui.activity.SYQSkDetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 水库水位
 */
public class SYQ_SkswInfoAdapter extends BaseQuickAdapter<SYQ_SKSWData.DataBean, BaseViewHolder> {
    private Context context;
    private List<SYQ_SKSWData.DataBean> data;

    public SYQ_SkswInfoAdapter(Context context, @Nullable List<SYQ_SKSWData.DataBean> data) {
        super(R.layout.syq_sk_item, data);
        this.context = context;
        this.data = data;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(final BaseViewHolder helper, final SYQ_SKSWData.DataBean item) {
        CardView cv_sk = helper.getView(R.id.cv_sk);
        TextView tv_sk_name = helper.getView(R.id.tv_sk_name);
        TextView tv_sk_kushuiwei = helper.getView(R.id.tv_sk_kushuiwei);
        TextView tv_sk_xxsw = helper.getView(R.id.tv_sk_xxsw);
        TextView tv_sk_cxxsw = helper.getView(R.id.tv_sk_cxxsw);
        TextView tv_sk_time = helper.getView(R.id.tv_sk_time);

        tv_sk_name.setText(item.get站名() + "-" + item.get政区());
        String ksw = item.get库水位();
        tv_sk_kushuiwei.setText("".equals(ksw) ? ksw : ksw + "m");
        String xxsw = item.get汛限水位();
        tv_sk_xxsw.setText("".equals(xxsw) ? xxsw : xxsw + "m");
        String cxxsw = item.get超汛限水位();
        tv_sk_cxxsw.setText("".equals(cxxsw) ? cxxsw : cxxsw + "m");
        if (Double.parseDouble(cxxsw) > 0) {    //超汛限水位值大于0，字体颜色显示红色
            tv_sk_cxxsw.setTextColor(Color.RED);
        }
        tv_sk_time.setText(item.get时间());

        cv_sk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SYQSkDetailActivity.class);
                intent.putExtra("sk", item);
                context.startActivity(intent);
            }
        });
    }
}