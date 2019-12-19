package com.appler.riverchiefsystem.ui.activity.task;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.api.FlagConstant;
import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.dao.daoBean.AdminData;
import com.appler.riverchiefsystem.bean.FileData;
import com.appler.riverchiefsystem.bean.TaskData;
import com.appler.riverchiefsystem.dao.AdminDao;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.presenter.TaskDetailPresenter;
import com.appler.riverchiefsystem.ui.adapter.FileChooseAdapter;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.appler.riverchiefsystem.view.TaskDetailView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class TaskDetailActivity extends BaseActivity implements TaskDetailView {
    @BindView(R.id.iv_toolbar_back)
    ImageView iv_back;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_title;
    @BindView(R.id.iv_toolbar_imageview1)
    ImageView iv_edit;
    @BindView(R.id.iv_toolbar_imageview2)
    ImageView iv_overtime;
    @BindView(R.id.sv_search)
    SearchView sv_search;
    @BindView(R.id.tv_taskDetail_title)
    TextView tv_taskTitle;
    @BindView(R.id.tv_taskDetail_describe)
    TextView tv_describe;
    @BindView(R.id.tv_taskDetail_launch)
    TextView tv_launch;
    @BindView(R.id.tv_taskDetail_accept)
    TextView tv_accept;
    @BindView(R.id.tv_taskDetail_createtime)
    TextView tv_createtime;
    @BindView(R.id.tv_taskDetail_expecttime)
    TextView tv_expecttime;
    @BindView(R.id.tv_taskDetail_schedule)
    TextView tv_schedule;
    @BindView(R.id.tv_taskDetail_state)
    TextView tv_state;
    @BindView(R.id.tv_taskDetail_riverName)
    TextView tv_riverName;
    @BindView(R.id.tv_taskDetail_address)
    TextView tv_address;
    @BindView(R.id.ll_taskDetail_files)
    LinearLayout ll_files;
    @BindView(R.id.rv_taskDetail_files)
    RecyclerView rv_files;
    private String TAG = getClass().getSimpleName();
    private TaskData.DataBean taskData;
    private ArrayList<FileData> fileList;
    private FileChooseAdapter fileAdapter;

    private TaskDetailPresenter taskDetailPresenter;
    private String msgId;


    @Override
    protected int bindLayout() {
        return R.layout.activity_task_detail;
    }

    @Override
    protected void doBuissness(Context context) {
        taskDetailPresenter = new TaskDetailPresenter(this);
        tv_title.setText("任务详情");

        msgId = getIntent().getStringExtra("msgId");


        sv_search.setVisibility(View.GONE);
    }

    private void initTaskDetail(TaskData.DataBean taskData) {
        if (null != taskData) {
            tv_taskTitle.setText(taskData.getQuestion());
            tv_describe.setText(taskData.getQuestionxq());
            tv_launch.setText(taskData.getFs_name());
            tv_accept.setText(AdminDao.getAdmin().getName());
            tv_createtime.setText(taskData.getUpdatetime());
            String task_state = taskData.getState();
            tv_state.setText(task_state);
            tv_riverName.setText(taskData.getRivername());
            tv_address.setText(taskData.getAddress());

            if ("已解决".equals(task_state)) {
                iv_edit.setVisibility(View.GONE);
            } else {
                iv_edit.setVisibility(View.VISIBLE);
                iv_edit.setImageResource(R.drawable.ic_edit);
            }


            fileList = new ArrayList<>();
            String picture = taskData.getPicture();
            String video = taskData.getVideo();
            if (!"".equals(picture) && null != picture && !"null".equals(picture)) {
                String[] split = picture.split(",");
                for (int i = 0; i < split.length; i++) {
                    String s = split[i];
                    FileData fileData = new FileData();
                    fileData.setFilename(s);
                    fileData.setFilePath(s);
                    fileList.add(fileData);
                }
            }

            if (!"".equals(video) && null != video && !"null".equals(video)) {
                String[] split1 = video.split(",");
                for (int i = 0; i < split1.length; i++) {
                    String s = split1[i];
                    FileData fileData = new FileData();
                    fileData.setFilename(s);
                    fileData.setFilePath(s);
                    fileList.add(fileData);
                }
            }

                if (fileList.size() > 0) {
                    ll_files.setVisibility(View.VISIBLE);
                    //文件
                    rv_files.setLayoutManager(new LinearLayoutManager(TaskDetailActivity.this));
                    fileAdapter = new FileChooseAdapter(TaskDetailActivity.this, fileList, 2);
                    rv_files.setAdapter(fileAdapter);
                } else {
                    ll_files.setVisibility(View.GONE);
                }

        }
    }


    @OnClick({R.id.iv_toolbar_back, R.id.iv_toolbar_imageview1, R.id.iv_toolbar_imageview, R.id.iv_toolbar_imageview2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:

                finish();
                break;
            case R.id.iv_toolbar_imageview1:   //编辑任务详情
                Intent taskIntent = new Intent(TaskDetailActivity.this, EditTaskActivity.class);
                taskIntent.putExtra("task", taskData);
//                startActivity(taskIntent);
                startActivityForResult(taskIntent, Global.TASK_EDIT_CODE);
                break;
            case R.id.iv_toolbar_imageview2:    //逾期任务上报 或打分
                if ("已解决".equals(taskData.getState())) {
                } else {
                    //逾期上报
                    postOverTime();
                }
                break;
        }
    }

    @Override
    public void editTaskDetail(String string) {
        Log.i(TAG, "editTaskDetail: " + string);
    }

    @Override
    public void getTaskDetail(String string) {
        Log.i(TAG, "getTaskDetail: " + string);
        try {
            JSONObject jsonObject = new JSONObject(string);
            String msg = jsonObject.getString("msg");
            if ("success".equals(msg)) {
                TaskData data = new Gson().fromJson(string, new TypeToken<TaskData>() {
                }.getType());
                taskData = data.getData().get(0);
                initTaskDetail(taskData);
            } else {
                ToastUtils.showShortToast(TaskDetailActivity.this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void postOverTime() {
        AdminData admin = AdminDao.getAdmin();
        if (null != admin) {
//            taskDetailPresenter.postOverTime(FlagConstant.FLAG_TASK_OVERTIME_REPORT, String.valueOf(admin.getSysStaffId()), taskId, taskData.getTaskDetail(), String.valueOf(taskData.getAcceptStaffId()));
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Global.TASK_EDIT_CODE:
                    requstTaskDetail();
                    break;
            }
        }
    }

    private void requstTaskDetail() {
        AdminData admin = AdminDao.getAdmin();
        if (null != admin) {
            taskDetailPresenter.getTaskDetail(FlagConstant.FLAG_MESSAGEITEM_DETAIL, msgId, Global.MESSAGE_TYPE_TASK);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requstTaskDetail();
    }

}
