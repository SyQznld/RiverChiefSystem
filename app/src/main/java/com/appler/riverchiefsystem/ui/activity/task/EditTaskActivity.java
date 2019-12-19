package com.appler.riverchiefsystem.ui.activity.task;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.api.FlagConstant;
import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.bean.FileData;
import com.appler.riverchiefsystem.bean.TaskData;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.presenter.TaskListPresenter;
import com.appler.riverchiefsystem.ui.adapter.FileChooseAdapter;
import com.appler.riverchiefsystem.utils.CommonUtils;
import com.appler.riverchiefsystem.utils.CountingRequestBody;
import com.appler.riverchiefsystem.utils.MainHandler;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.appler.riverchiefsystem.utils.customView.UploadDialogUtils;
import com.appler.riverchiefsystem.view.TaskListView;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 编辑任务
 */
public class EditTaskActivity extends BaseActivity implements TaskListView {
    @BindView(R.id.iv_toolbar_tv_back)
    ImageView iv_back;
    @BindView(R.id.tv_toolbar_tv_title)
    TextView tv_title;
    @BindView(R.id.tv_toolbar_tv_textview)
    TextView tv_submit;
    @BindView(R.id.tv_addTask_title)
    TextView tv_taskTitle;
    @BindView(R.id.et_addTask_describe)
    EditText et_describe;
    @BindView(R.id.tv_addTask_launchName)
    TextView tv_launchName;
    @BindView(R.id.tv_addTask_address)
    TextView tv_riverName;
    @BindView(R.id.tv_addTask_state)
    TextView tv_state;
    @BindView(R.id.iv_addTask_file)
    ImageView iv_file;
    @BindView(R.id.ll_addTask_files)
    LinearLayout ll_files;
    @BindView(R.id.rv_addTask_file)
    RecyclerView rv_file;
    private String TAG = getClass().getSimpleName();
    private List<String> stateList;
    private ArrayList<FileData> fileList = new ArrayList<>();
    private FileChooseAdapter fileAdapter;

    private Dialog uploadDialog;

    private String taskId = "";
    private String accept_staff_id = "";
    private String launch_staff_id = "";
//    private String

    private TaskData.DataBean taskData;
    private TaskListPresenter taskPresenter;

    @Override
    protected int bindLayout() {
        return R.layout.activity_edit_task;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void doBuissness(Context context) {
        taskPresenter = new TaskListPresenter(this);
        taskData = getIntent().getParcelableExtra("task");

        tv_submit.setText("提交");
        tv_title.setText("编辑任务");

        stateList = new ArrayList<>();
        stateList.add("正在解决");
        stateList.add("待解决");
        stateList.add("不能解决");
        stateList.add("已解决");
        tv_state.setText(stateList.get(0));

        initView(taskData);


        //文件
        rv_file.setLayoutManager(new LinearLayoutManager(EditTaskActivity.this));
        fileAdapter = new FileChooseAdapter(EditTaskActivity.this, fileList, 1);
        rv_file.setAdapter(fileAdapter);
        fileAdapter.setOnFileItemLongClick(new FileChooseAdapter.OnFileItemLongClick() {
            @Override
            public void deleteFile(final int position) {
                new MaterialDialog.Builder(EditTaskActivity.this)
                        .title("删除文件！")
                        .content("确定删除当前文件！")
                        .positiveText("确定")
                        .negativeText("取消")
                        .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {

                                    String s = fileList.get(position).getFilePath();
                                    fileList.remove(position);
                                    new File(s).delete();  //从文件管理保存文件夹中删除
                                    fileAdapter.notifyDataSetChanged();

                                    dialog.dismiss();

                                } else if (which == DialogAction.NEGATIVE) {
                                    dialog.dismiss();
                                }
                            }
                        }).show();
            }
        });
    }

    @OnClick({R.id.iv_toolbar_tv_back, R.id.tv_toolbar_tv_textview, R.id.tv_addTask_state,
            R.id.iv_addTask_file})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_tv_back:
                finish();
                break;
            case R.id.tv_toolbar_tv_textview: //提交任务，请求接口
                uploadDialog = UploadDialogUtils.createLoadingDialog(EditTaskActivity.this, "正在提交");
                submitTask();
                break;
            case R.id.tv_addTask_state:   //选择任务状态
                CommonUtils.SetSinnper(stateList, tv_state, EditTaskActivity.this, true);
                break;
            case R.id.iv_addTask_file:   //选择附件
                new LFilePicker()
                        .withActivity(EditTaskActivity.this)
                        .withTitle("文件选择")
                        .withRequestCode(Global.ADDTASK_FILECHOOSE_CODE)
                        .withIconStyle(Constant.ICON_STYLE_BLUE)
                        .withMutilyMode(true)   //多选
                        .withFileFilter(new String[]{".jpg", ".png",".jpeg", ".mp4",".MP4", ".wav"})
                        .withBackgroundColor("#3F51B5")
                        .start();
                break;
        }
    }


    /**
     * 编辑任务提交
     */
    @SuppressLint("SimpleDateFormat")
    private void submitTask() {
        final OkHttpClient mOkHttpClient = new OkHttpClient();
        final MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        requestBody.addFormDataPart("flag", FlagConstant.FLAG_TASK_EDIT);
        requestBody.addFormDataPart("question", tv_title.getText().toString());
        requestBody.addFormDataPart("questionxq", et_describe.getText().toString());
        requestBody.addFormDataPart("state", tv_state.getText().toString());
        requestBody.addFormDataPart("updatetime", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        requestBody.addFormDataPart("fs_id", taskData.getFuzeren());
        requestBody.addFormDataPart("id", taskId);


        final List<String> uploadList = new ArrayList<>();
        List<String> PhotoList = new ArrayList<>();
        List<String> Mp4List = new ArrayList<>();
        List<String> FileList = new ArrayList<>();
        if (tv_title.getText().toString().equals("编辑任务")) {
            for (int i = 0; i < fileList.size(); i++) {
                String s1 = fileList.get(i).getFilePath();
                if (s1.contains(Global.UPLOAD_FILEPATH)) {
                    if (s1.contains(".jpg")
                            || s1.contains(".JPG")
                            || s1.contains(".png")
                            || s1.contains(".PNG")
                            || s1.contains(".jpeg")
                            || s1.contains(".JPEG")) {
                        PhotoList.add(s1);
                    } else if (s1.contains(".mp4") || s1.contains(".MP4") || s1.contains(".wav")) {
                        Mp4List.add(s1);
                    } else {
                        FileList.add(s1);
                    }
                } else {
                    uploadList.add(s1);
                }
            }
            String beforimg = "";
            String beforvideo = "";
            for (int i = 0; i < PhotoList.size(); i++) {
                if (i < PhotoList.size() - 1) {
                    String s = PhotoList.get(i);
                    beforimg = beforimg + s + ",";
                } else {
                    String s = PhotoList.get(i);
                    beforimg = beforimg + s;
                }
            }

            for (int i = 0; i < Mp4List.size(); i++) {
                if (i < Mp4List.size() - 1) {
                    String s = Mp4List.get(i);
                    beforvideo = beforvideo + s + ",";
                } else {
                    String s = Mp4List.get(i);
                    beforvideo = beforvideo + s;
                }
            }
            Log.i(TAG, "submitTask  beforimg: " + beforimg);
            Log.i(TAG, "submitTask  beforvideo: " + beforvideo);
            requestBody.addFormDataPart("beforimg", beforimg);
            requestBody.addFormDataPart("beforvideo", beforvideo);
            requestBody.addFormDataPart("beforfile", "");
        }
// else {
//            List<String> preList = new ArrayList<>();
//            for (int i = 0; i < fileList.size(); i++) {
//                String filePath = fileList.get(i).getFilePath();
//                preList.add(filePath);
//            }
//            uploadList.addAll(preList);
//            requestBody.addFormDataPart("beforimg", "");
//            requestBody.addFormDataPart("beforvideo", "");
//            requestBody.addFormDataPart("beforfile", "");
//        }


        for (int i = 0; i < uploadList.size(); i++) {
            String s1 = uploadList.get(i);
            if (!"".equals(s1) && !s1.contains(Global.URL)) {
                File file = new File(s1);
                RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                CountingRequestBody countingRequestBody = new CountingRequestBody(body, new CountingRequestBody.Listener() {
                    @Override
                    public void onRequestProgress(long byteWritted, long contentLength) {
                        //打印进度
                    }
                });
                String filename = file.getName();
                requestBody.addFormDataPart("File", filename, countingRequestBody);
            }
        }


        Request request = new Request.Builder()
                .url(Global.Upload_Url)
                .post(requestBody.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String string = e.getMessage();
                MainHandler.getInstance().post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShortToast(EditTaskActivity.this, string);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                MainHandler.getInstance().post(new Runnable() {
                    @Override
                    public void run() {
                        if ("Success".equals(string)) {
                            showToast("编辑成功");
                            UploadDialogUtils.closeDialog(uploadDialog);

                            Intent intent = new Intent();
                            setResult(Global.TASK_EDIT_CODE, intent);
                            finish();
                        } else {
                            showToast("编辑失败");
                            UploadDialogUtils.closeDialog(uploadDialog);
                        }
                    }
                });

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Global.ADDTASK_FILECHOOSE_CODE) {
                List<String> list = data.getStringArrayListExtra("paths");
                for (int i = 0; i < list.size(); i++) {
                    String s = list.get(i);
                    FileData fileBean = new FileData();
                    File file = new File(s);
                    fileBean.setFilename(file.getName());
                    fileBean.setFilePath(file.getAbsolutePath());
                    fileBean.setChooseTime(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
                    fileList.add(fileBean);

                }
                fileAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (uploadDialog != null) {
            uploadDialog.dismiss();
        }
    }


    /**
     * 初始化界面
     */
    private void initView(TaskData.DataBean taskData) {
        tv_taskTitle.setText(taskData.getQuestion());
        et_describe.setText(taskData.getQuestionxq());
        taskId = taskData.getId();
        accept_staff_id = taskData.getFuzeren();
        launch_staff_id = taskData.getFasongren();
        tv_riverName.setText(taskData.getAddress());
        tv_launchName.setText(taskData.getFs_name());
        tv_state.setText(taskData.getState());


        String task_attachment = taskData.getPicture();
        String task_video = taskData.getVideo();
        if (!"".equals(task_attachment) && null != task_attachment && task_attachment.contains(".")) {
            String[] split = task_attachment.split(",");
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                FileData fileData = new FileData();
                fileData.setFilename(s);
                fileData.setFilePath(s);
                fileList.add(fileData);
            }

        }
        if (!"".equals(task_video) && null != task_video && task_video.contains(".")) {
            String[] split = task_video.split(",");
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                FileData fileData = new FileData();
                fileData.setFilename(s);
                fileData.setFilePath(s);
                fileList.add(fileData);
            }
        }
        if (fileList.size() > 0) {
            ll_files.setVisibility(View.VISIBLE);
            //文件
            rv_file.setLayoutManager(new LinearLayoutManager(EditTaskActivity.this));
            fileAdapter = new FileChooseAdapter(EditTaskActivity.this, fileList, 2);
            rv_file.setAdapter(fileAdapter);

            fileAdapter.setOnFileItemLongClick(new FileChooseAdapter.OnFileItemLongClick() {
                @Override
                public void deleteFile(final int position) {
                    new MaterialDialog.Builder(EditTaskActivity.this)
                            .title("删除文件！")
                            .content("确定删除当前文件！")
                            .positiveText("确定")
                            .negativeText("取消")
                            .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                            .onAny(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if (which == DialogAction.POSITIVE) {

                                        String s = fileList.get(position).getFilePath();
                                        fileList.remove(position);
                                        new File(s).delete();  //从文件管理保存文件夹中删除
                                        fileAdapter.notifyDataSetChanged();


                                        dialog.dismiss();

                                    } else if (which == DialogAction.NEGATIVE) {
                                        dialog.dismiss();
                                    }
                                }
                            }).show();
                }
            });
        } else {
            ll_files.setVisibility(View.GONE);
        }


    }

    @Override
    public void getTaskList(String string) {

    }

    @Override
    public void getChargePerson(String string) {

    }

    @Override
    public void updateTask(String string) {
        Log.i(TAG, "updateTask: " + string);
        UploadDialogUtils.closeDialog(uploadDialog);
        try {
            JSONObject jsonObject = new JSONObject(string);
            String msg = jsonObject.getString("msg");
            if ("修改成功".equals(msg)) {
                finish();
            } else {
                ToastUtils.showShortToast(EditTaskActivity.this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
