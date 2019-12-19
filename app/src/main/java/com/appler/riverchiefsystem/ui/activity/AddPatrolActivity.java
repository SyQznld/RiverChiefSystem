package com.appler.riverchiefsystem.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.api.FlagConstant;
import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.bean.ChargeRiversData;
import com.appler.riverchiefsystem.dao.AdminDao;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.presenter.ChargeRiversPresenter;
import com.appler.riverchiefsystem.ui.adapter.PhotoShowAdapter;
import com.appler.riverchiefsystem.utils.CommonUtils;
import com.appler.riverchiefsystem.utils.CountingRequestBody;
import com.appler.riverchiefsystem.utils.MainHandler;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.appler.riverchiefsystem.utils.customView.UploadDialogUtils;
import com.appler.riverchiefsystem.view.ChargeRiversView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
 * 添加巡查
 */
public class AddPatrolActivity extends BaseActivity implements ChargeRiversView {
    @BindView(R.id.iv_toolbar_tv_back)
    ImageView iv_back;
    @BindView(R.id.tv_toolbar_tv_title)
    TextView tv_title;
    @BindView(R.id.tv_toolbar_tv_textview)
    TextView tv_submit;

    @BindView(R.id.rv_addPatrol_images)
    RecyclerView rv_images;
    @BindView(R.id.tv_addPatrol_river)
    TextView tv_river;
    @BindView(R.id.tv_addPatrol_time_start)
    TextView tv_start;
    @BindView(R.id.tv_addPatrol_time_end)
    TextView tv_end;
    private String TAG = getClass().getSimpleName();
    private PhotoShowAdapter photoShowAdapter;
    private List<String> patrolPhotoList;

    private String patrolRecordBegintime = "";
    private String patrolRiverGps = "";  //巡查过程中GPS坐标
    private Dialog uploadDialog;
    private List<String> riverReachList = new ArrayList<>();
    private String partolId = "";  //巡查id
    private String riverId = "";//河流id
    private String sjcm = "";//所经村名
    private String heduanName = "";//所经村名

    private ChargeRiversPresenter chargeRiversPresenter;

    @Override
    protected int bindLayout() {
        return R.layout.activity_add_patrol;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void doBuissness(final Context context) {
        chargeRiversPresenter = new ChargeRiversPresenter(this);
        chargeRiversPresenter.getChargeRivers(FlagConstant.FLAG_CHARGE_RIVERS_LIST, AdminDao.getAdminID());
        tv_title.setText("添加巡查");
        tv_submit.setText("提交");

        patrolPhotoList = getIntent().getStringArrayListExtra("patrolPhotoList");
        patrolRecordBegintime = getIntent().getStringExtra("patrolRecordBegintime");
        patrolRiverGps = getIntent().getStringExtra("patrolRecordGps");
        partolId = getIntent().getStringExtra("partolId");
        tv_start.setText(patrolRecordBegintime);
        tv_end.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));

        rv_images.setLayoutManager(new GridLayoutManager(AddPatrolActivity.this, 3));
        photoShowAdapter = new PhotoShowAdapter(context, patrolPhotoList);
        rv_images.setAdapter(photoShowAdapter);
        photoShowAdapter.setPhotoItemClickListener(new PhotoShowAdapter.PhotoItemClickListener() {
            @Override
            public void toShowPhoto(String item, int position) {
                //点击查看图片或视频
                if (patrolPhotoList.get(position).endsWith("jpg")
                        || patrolPhotoList.get(position).endsWith("png")
                        || patrolPhotoList.get(position).endsWith("jpeg")
                        || patrolPhotoList.get(position).endsWith("JPG")
                        || patrolPhotoList.get(position).endsWith("PNG")
                        || patrolPhotoList.get(position).endsWith("JPEG")) {
                    Intent intent = new Intent(AddPatrolActivity.this, PhotoShowActivity.class);
                    intent.putStringArrayListExtra("photoList", (ArrayList<String>) patrolPhotoList);
                    intent.putExtra("position", position);
                    startActivity(intent);
                } else {
                    try {
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    Uri data = Uri.parse(patrolPhotoList.get(position));
//                    intent.setDataAndType(data, "video/mp4");
//                    startActivity(intent);
                        CommonUtils.openFile(new File(patrolPhotoList.get(position)), AddPatrolActivity.this);

                    } catch (ActivityNotFoundException a) {
                        Log.i(TAG, "onItemClick: " + a.getMessage());
                    }
                }
            }

            @Override
            public void deletePhoto(String item, final int position) {
                //长按删除图片
                new MaterialDialog.Builder(AddPatrolActivity.this)
                        .title("删除文件！")
                        .content("确定删除当前文件！")
                        .positiveText("确定")
                        .negativeText("取消")
                        .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {

                                    String s = patrolPhotoList.get(position);
                                    patrolPhotoList.remove(position);
                                    new File(s).delete();
                                    photoShowAdapter.notifyDataSetChanged();

                                    dialog.dismiss();

                                } else if (which == DialogAction.NEGATIVE) {
                                    dialog.dismiss();
                                }
                            }
                        }).show();


            }
        });

    }

    @OnClick({R.id.iv_toolbar_tv_back, R.id.tv_addPatrol_river, R.id.tv_toolbar_tv_textview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_tv_back:
//                finish();
                new MaterialDialog.Builder(AddPatrolActivity.this)
                        .title("退出保存")
                        .content("确定退出当前编辑页面")
                        .positiveText("确定")
                        .negativeText("取消")
                        .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {
                                    Intent intent = new Intent();
                                    setResult(RESULT_OK, intent);
                                    finish();
                                } else if (which == DialogAction.NEGATIVE) {
                                    dialog.dismiss();
                                }
                            }
                        }).show();
                break;
            case R.id.tv_addPatrol_river: //选择河段
                CommonUtils.SetSinnper(riverReachList, tv_river, AddPatrolActivity.this, true);
                break;
            case R.id.tv_toolbar_tv_textview: //提交巡查记录，请求接口
                uploadDialog = UploadDialogUtils.createLoadingDialog(AddPatrolActivity.this, "正在上传");
                String s = tv_river.getText().toString();
                if (!"".equals(s)) {
                    submitPatrol();
                } else {
                    ToastUtils.showShortToast(AddPatrolActivity.this, "请选择巡查河段");
                }
                break;
        }
    }


    /**
     * 结束巡查时，更新巡查记录
     */
    @SuppressLint("SimpleDateFormat")
    private void submitPatrol() {
        String riverStr = tv_river.getText().toString();
        if (riverStr.contains(",")) {
            String[] split = riverStr.split(",");
            riverId = split[1];
            String s = split[0];
            if (s.contains("-")) {
                String[] split1 = s.split("-");
                sjcm = split1[0];
                heduanName = split1[1];
            }
        }

        //LINESTRING (113.029102558474 33.7355714111158, 113.047023412 33.7382687900001, 113.050901919019 33.7388171135183)
        final OkHttpClient mOkHttpClient = new OkHttpClient();
        final MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        requestBody.addFormDataPart("flag", FlagConstant.FLAG_PATROL_ADDOREDIT);
        requestBody.addFormDataPart("sys_staff_id", String.valueOf(AdminDao.getAdminID()));
        requestBody.addFormDataPart("patrol_record_begintime", patrolRecordBegintime);
        requestBody.addFormDataPart("patrol_record_endtime", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        requestBody.addFormDataPart("patrol_record_gps", "LINESTRING (" + patrolRiverGps + ")");
        requestBody.addFormDataPart("sys_patrol_record_id", partolId);
        requestBody.addFormDataPart("sys_river_id", riverId);
        requestBody.addFormDataPart("heduanname", heduanName);   //河段名称
        requestBody.addFormDataPart("sjc_name", sjcm);            //地址要所经村名
        for (int i = 0; i < patrolPhotoList.size(); i++) {
            String s1 = patrolPhotoList.get(i);
            Log.i(TAG, "submitPatrol: " + s1);
            if (!"".equals(s1)) {
                File file = new File(s1);
                RequestBody body = RequestBody.create(MediaType.parse("text/x-markdown"), file);
                CountingRequestBody countingRequestBody = new CountingRequestBody(body, new CountingRequestBody.Listener() {
                    @Override
                    public void onRequestProgress(long byteWritted, long contentLength) {
                        //打印进度
                    }
                });
                String filename = file.getName();
                requestBody.addFormDataPart("pic_path", filename, countingRequestBody);
            }
        }
        Request request = new Request.Builder()
                .url(Global.Upload_Url)
                .post(requestBody.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String message = e.getMessage();
                Log.i(TAG, "submitPatrol onFailure: " + message);
                e.printStackTrace();
                MainHandler.getInstance().post(new Runnable() {
                    @Override
                    public void run() {
                        UploadDialogUtils.closeDialog(uploadDialog);
                        ToastUtils.showShortToast(AddPatrolActivity.this, message);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                Log.i(TAG, "submitPatrol onResponse: " + string);
                MainHandler.getInstance().post(new Runnable() {
                    @Override
                    public void run() {
                        if ("Success".equals(string)) {
                            UploadDialogUtils.closeDialog(uploadDialog);
                            //保存成功后 返回
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                });

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (uploadDialog != null) {
            uploadDialog.dismiss();
        }
    }


    @Override
    public void getChargeRivers(String string) {
        Log.i(TAG, "getChargeRivers: " + string);
        riverReachList.clear();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String msg = jsonObject.getString("msg");
            if ("success".equals(msg)) {
                ChargeRiversData chargeRiversData = new Gson().fromJson(string, new TypeToken<ChargeRiversData>() {
                }.getType());
                final List<ChargeRiversData.DataBean> data = chargeRiversData.getData();
                if (null != data && data.size() > 0) {

                    for (int i = 0; i < data.size(); i++) {
                        String heduanName = data.get(i).get河段名称();
                        String ogr_fid = data.get(i).getOgr_fid();
                        String sjcm = data.get(i).get所经村名();
                        riverReachList.add(sjcm + "-" + heduanName + "," + ogr_fid);
                    }
                } else {
                    riverReachList.add("暂无负责河段");
                }
            } else {
                ToastUtils.showShortToast(AddPatrolActivity.this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAllUsers(String string) {

    }
}

