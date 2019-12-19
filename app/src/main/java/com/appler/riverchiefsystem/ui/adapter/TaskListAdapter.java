package com.appler.riverchiefsystem.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.api.FlagConstant;
import com.appler.riverchiefsystem.bean.TaskData;
import com.appler.riverchiefsystem.dao.AdminDao;
import com.appler.riverchiefsystem.ui.activity.task.TaskDetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public class TaskListAdapter extends BaseQuickAdapter<TaskData.DataBean, BaseViewHolder> {
    private Context context;
    private List<TaskData.DataBean> data;
    private int flag;


    public TaskListAdapter(Context context, @Nullable List<TaskData.DataBean> data, int flag) {
        super(R.layout.task_list_layout, data);
        this.context = context;
        this.data = data;
        this.flag = flag;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final TaskData.DataBean item) {
        String task_title = item.getQuestion();
        String task_launcher = item.getFs_name();
        String accept_name = AdminDao.getAdmin().getName();
        String updatetime = item.getUpdatetime();
        String task_state = item.getState();
        ImageView iv_state = helper.getView(R.id.iv_task_state);
        helper.setText(R.id.tv_task_name, task_title);
        helper.setText(R.id.tv_task_launch, task_launcher);
        helper.setText(R.id.tv_task_accept, accept_name);
        helper.setText(R.id.tv_task_createtime, "时间： " + updatetime);
        helper.setText(R.id.tv_task_address, "地址： " + item.getAddress());
        if (FlagConstant.TASKTYPE_FINISH.equals(task_state)) {
            iv_state.setImageResource(R.drawable.ic_dot_blue);
        } else if (FlagConstant.TASKTYPE_NOBEGIN.equals(task_state)) {
            iv_state.setImageResource(R.drawable.ic_dot_red);
        } else if (FlagConstant.TASKTYPE_BNJJ.equals(task_state)) {
            iv_state.setImageResource(R.drawable.ic_dot_gray);
        } else {
            iv_state.setImageResource(R.drawable.ic_dot_green);
        }


        helper.getView(R.id.cv_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TaskDetailActivity.class);
                intent.putExtra("flag", flag);
                intent.putExtra("msgId", String.valueOf(item.getId()));
                intent.putExtra("task",item);
                context.startActivity(intent);
            }
        });


    }


}