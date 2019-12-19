package com.appler.riverchiefsystem.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.appler.riverchiefsystem.MainActivity;
import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.bean.PatrolData;
import com.appler.riverchiefsystem.ui.activity.PatrolDetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class PatrolListAdapter extends BaseQuickAdapter<PatrolData.DataBean, BaseViewHolder> {
    private Context context;
    private List<PatrolData.DataBean> data;


    public PatrolListAdapter(Context context, @Nullable List<PatrolData.DataBean> data) {
        super(R.layout.patrol_item_layout, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final PatrolData.DataBean item) {
        final String sjcm = item.getAddress();
        final String heduan = item.getRivername();

        String begintime = item.getPatrol_record_begintime();
        String endtime = item.getPatrol_record_endtime();
        final String patrolGps = item.getPatrol_record_gps();

        helper.setText(R.id.tv_patrol_riverName, heduan);
        helper.setText(R.id.tv_patrol_begintime, begintime);
        helper.setText(R.id.tv_patrol_endtime, endtime);
        helper.setText(R.id.tv_patrol_sjcm, sjcm);
        ImageView iv_guiji = helper.getView(R.id.iv_patrol_guiji);

        //LINESTRING (113.029102558474 33.7355714111158, 113.047023412 33.7382687900001, 113.050901919019 33.7388171135183)
        if (!"".equals(patrolGps) && !"null".equals(patrolGps) && null != patrolGps) {
            iv_guiji.setVisibility(View.VISIBLE);
            iv_guiji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String restr = null;
                    if (patrolGps.contains("LINESTRING")) {
                        if (patrolGps.contains("(") && patrolGps.contains(")")) {
                            restr = patrolGps.substring(patrolGps.indexOf("(") + 1, patrolGps.indexOf(")"));
                        }
                    } else {
                        restr = patrolGps;
                    }
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("flag", 1);
                    intent.putExtra("patrolGps", restr);
                    intent.putExtra("heduan", heduan);
                    intent.putExtra("riverName", sjcm);
                    intent.putExtra("type", "巡查");
                    context.startActivity(intent);
                }
            });
        } else {
            iv_guiji.setVisibility(View.GONE);
        }


        helper.getView(R.id.cv_patrol).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PatrolDetailActivity.class);
                intent.putExtra("patrol", item);
                context.startActivity(intent);
            }
        });


    }


    private PatrolItemClickListener patrolItemClickLiatener;

    public void setPatrolItemClickListener(PatrolItemClickListener patrolItemClickLiatener) {
        this.patrolItemClickLiatener = patrolItemClickLiatener;
    }

    public interface PatrolItemClickListener {
        void clickPatrolItem(int position, PatrolData.DataBean item);
    }
}
