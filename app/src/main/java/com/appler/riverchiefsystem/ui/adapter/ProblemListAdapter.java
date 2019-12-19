package com.appler.riverchiefsystem.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.appler.riverchiefsystem.MainActivity;
import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.bean.ProblemData;
import com.appler.riverchiefsystem.ui.activity.ProblemDetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public class ProblemListAdapter extends BaseQuickAdapter<ProblemData.DataBean, BaseViewHolder> {
    private Context context;
    private List<ProblemData.DataBean> data;


    public ProblemListAdapter(Context context, @Nullable List<ProblemData.DataBean> data) {
        super(R.layout.problem_list_layout, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ProblemData.DataBean item) {

        final String problem_remark = item.get问题描述();
        final String problem_reason = item.get上报理由();
        String problem_position = item.get地址();
        String problem_resove = item.get事件状态();
        String accept_name = item.getFz_name();
        String report_name = item.get上报人姓名();
        String person = report_name + " --->" + accept_name;
        helper.setText(R.id.tv_problem_riverName, problem_remark);
        helper.setText(R.id.tv_problem_describe, problem_reason);
        helper.setText(R.id.tv_problem_position, problem_position);
        helper.setText(R.id.tv_problem_rosove, problem_resove);
        helper.setText(R.id.tv_problem_person, person);


        helper.getView(R.id.cv_problem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProblemDetailActivity.class);
                intent.putExtra("msgId", item.getId());
                context.startActivity(intent);
            }
        });

        //显示经纬度位置信息
        ImageView iv_location = helper.getView(R.id.iv_problem_location);
        String lng = item.get经度();
        String lat = item.get纬度();
        if (!"".equals(lng) && !"".equals(lat)) {
            iv_location.setVisibility(View.VISIBLE);
            final String lnglat = lng + "," + lat;
            //点击定位，跳转到主页 添加marker点
            iv_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("flag", 1);
                    intent.putExtra("problemLnglat", lnglat);
                    intent.putExtra("problem_reason", problem_reason);   //上报理由
                    intent.putExtra("problem_remark", problem_remark);    //问题描述
                    intent.putExtra("problem_id", item.getId());      //问题id
                    intent.putExtra("type", "上报定位");
                    context.startActivity(intent);
                }
            });
        }else {
            iv_location.setVisibility(View.GONE);
        }
    }


}