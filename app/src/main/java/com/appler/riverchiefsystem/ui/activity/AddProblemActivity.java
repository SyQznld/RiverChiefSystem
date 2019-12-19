package com.appler.riverchiefsystem.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.api.FlagConstant;
import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.bean.ChargeRiversData;
import com.appler.riverchiefsystem.bean.ChooseUserData;
import com.appler.riverchiefsystem.bean.ProblemData;
import com.appler.riverchiefsystem.dao.AdminDao;
import com.appler.riverchiefsystem.dao.daoBean.AdminData;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.presenter.ChargeRiversPresenter;
import com.appler.riverchiefsystem.ui.adapter.GatherPhotoAdapter;
import com.appler.riverchiefsystem.utils.CommonUtils;
import com.appler.riverchiefsystem.utils.CountingRequestBody;
import com.appler.riverchiefsystem.utils.ImageSelectUtils;
import com.appler.riverchiefsystem.utils.MainHandler;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.appler.riverchiefsystem.utils.customView.MyExpandableListView;
import com.appler.riverchiefsystem.utils.customView.MyGridView;
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

public class AddProblemActivity extends BaseActivity implements ChargeRiversView {
    @BindView(R.id.iv_toolbar_tv_back)
    ImageView iv_back;
    @BindView(R.id.tv_toolbar_tv_title)
    TextView tv_title;
    @BindView(R.id.tv_toolbar_tv_textview)
    TextView tv_submit;
    @BindView(R.id.tv_toolbar_tv_save)
    TextView tv_save;
    @BindView(R.id.ll_problem_type)
    LinearLayout ll_type;
    @BindView(R.id.elv_problem_type)
    MyExpandableListView elv_type;
    @BindView(R.id.tv_addProblem_type)
    TextView tv_reason;
    @BindView(R.id.et_addProblem_describe)
    EditText et_describe;
    @BindView(R.id.tv_addProblem_report)
    TextView tv_report;
    @BindView(R.id.tv_addProblem_state)
    TextView tv_state;  //点击选择解决状态
    @BindView(R.id.tv_addProblem_receiver)
    TextView tv_receiver;
    @BindView(R.id.et_addProblem_solution)
    EditText et_solution;
    @BindView(R.id.tv_addProblem_position)
    TextView tv_riverName;
    @BindView(R.id.et_addProblem_remark)
    EditText et_remark;
    @BindView(R.id.gv_addProblem_photo)
    MyGridView gv_photo;
    private String TAG = getClass().getSimpleName();
    private List<String> stateList;
    private List<String> riverReachList = new ArrayList<>();
    private GatherPhotoAdapter gatherPhotoAdapter;
    private AlertDialog show;
    private String defaultPhotoAddress;
    private String photoFolderAddress;
    private List<String> photoList;
    private int CRAEMA_REQUEST_CODE = 10;
    private int ALBUM_REQUEST_CODE = 0x00000011;
    private File file;
    private String fileName;
    private Dialog uploadDialog;
    private String patrol_id = "";
    private String riverId = "";
    private String report_id;//发起问题人id
    private String heduanName = "";  //河段名称
    private String sjcm = "";  //所经村名
    private String juese = "";
    private ChargeRiversPresenter chargeRiversPresenter;
    private ProblemData.DataBean dataBean;
    private int flag;
    private String problem_id = "";
    private LocationManager lm;
    private List<String> riverTypeList;
    private String longitude = "";
    private String latitude = "";

    @Override
    protected int bindLayout() {
        return R.layout.activity_add_problem;
    }

    @Override
    protected void doBuissness(Context context) {
        chargeRiversPresenter = new ChargeRiversPresenter(this);
        tv_title.setText("上报问题");
        tv_submit.setText("提交");
        tv_save.setVisibility(View.GONE);

        if (initGpsSetting()) return;


        AdminData admin = AdminDao.getAdmin();
        if (null != admin) {
            chargeRiversPresenter.getChargeRivers(FlagConstant.FLAG_CHARGE_RIVERS_LIST, AdminDao.getAdminID());
        }

        riverTypeList = new ArrayList<>();
        riverTypeList.add("公示牌是否设置完整");
        riverTypeList.add("水质是否清洁");
        riverTypeList.add("岸边有无乱堆乱放现象");
        riverTypeList.add("有无漂浮物");
        riverTypeList.add("有无排污口");


        stateList = new ArrayList<>();
        stateList.add("待处理");
        stateList.add("正在处理");
        stateList.add("已解决");
        tv_state.setText(stateList.get(0));


        photoList = new ArrayList<>();
        defaultPhotoAddress = CommonUtils.getSDPath() + File.separator + ".jpg";
        photoFolderAddress = CommonUtils.getSDPath() + File.separator + Global.APPNAME + File.separator + "添加问题";
//        file = videoRename();


        gatherPhotoAdapter = new GatherPhotoAdapter(context, photoList);
        gv_photo.setAdapter(gatherPhotoAdapter);


        gv_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                if ("否".equals(tv_hecha.getText().toString())) {
                if (gatherPhotoAdapter.getmMaxPositon() <= 9) {
                    if (position == gatherPhotoAdapter.getmMaxPositon() - 1) {
                        new MaterialDialog.Builder(AddProblemActivity.this)
                                .items(R.array.take_photo)
                                .itemsCallback(new MaterialDialog.ListCallback() {
                                    @Override
                                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                        switch (position) {
                                            case 0://拍照
                                                takePhotoOrVideo();
                                                break;
                                            case 1: //本地相册
                                                ImageSelectUtils.openPhoto(AddProblemActivity.this, ALBUM_REQUEST_CODE, false, 9);
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                })
                                .show();
//                    }

                    } else {
                        if (photoList.get(position).endsWith("jpg")
                                || photoList.get(position).endsWith("JPG")
                                || photoList.get(position).endsWith("png")
                                || photoList.get(position).endsWith("PNG")
                                || photoList.get(position).endsWith("jpeg")
                                || photoList.get(position).endsWith("JPEG")
                        ) {
                            Intent intent = new Intent(AddProblemActivity.this, PhotoShowActivity.class);
                            intent.putStringArrayListExtra("photoList", (ArrayList<String>) photoList);
                            intent.putExtra("position", position);
                            startActivity(intent);
                        } else {
                            try{
//                                Intent intent = new Intent(Intent.ACTION_VIEW);
//                                Uri data = Uri.parse(photoList.get(position));
//                                intent.setDataAndType(data, "video/mp4");
//                                startActivity(intent);

                                CommonUtils.openFile(new File(photoList.get(position)),AddProblemActivity.this);
                            }catch (ActivityNotFoundException a){
                                Log.i(TAG, "onItemClick: " + a.getMessage());
                            }

                        }
                    }
                }
            }
        });


        if (gatherPhotoAdapter.getmMaxPositon() < 10) {
            gv_photo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
//                if ("否".equals(tv_hecha.getText().toString())) {
                    if (position == gatherPhotoAdapter.getmMaxPositon() - 1) {
                    } else {
                        new MaterialDialog.Builder(AddProblemActivity.this)
                                .title("删除文件！")
                                .content("确定删除当前文件！")
                                .positiveText("确定")
                                .negativeText("取消")
                                .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                                .onAny(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        if (which == DialogAction.POSITIVE) {

                                            String s = photoList.get(position);
                                            photoList.remove(position);
                                            new File(s).delete();
                                            gatherPhotoAdapter.notifyDataSetChanged();

                                            dialog.dismiss();

                                        } else if (which == DialogAction.NEGATIVE) {
                                            dialog.dismiss();
                                        }
                                    }
                                }).show();

                    }
//                }
                    return true;
                }
            });
        }


        flag = getIntent().getIntExtra("flag", 0);
        if (flag == 1) {      //巡查关联 添加问题
            patrol_id = getIntent().getStringExtra("patrol_id");
        } else if (flag == 2) {  //编辑问题
            tv_title.setText("编辑问题");
            //初始化编辑问题页面字段值
            initEditProblem();
        } else {   //单独新增问题，与巡查无关
        }


    }

    //编辑问题 初始化相关字段值
    private void initEditProblem() {
        dataBean = getIntent().getParcelableExtra("problem");
        problem_id = dataBean.getId();
        patrol_id = dataBean.getPatrol_record_id();
        tv_reason.setText(dataBean.get上报理由());
        et_describe.setText(dataBean.get问题描述());
        String problem_resove = dataBean.get事件状态();
        tv_state.setText(problem_resove);
        et_remark.setText(dataBean.get备注());
        riverId = dataBean.getRiver_id();
        sjcm = dataBean.get地址();
        heduanName = dataBean.get河道名称();
        tv_riverName.setText(sjcm + "-" + heduanName);


        String problem_photo = dataBean.getPicture();
        String problem_video = dataBean.getVideo();
        List<String> problem_photoList = new ArrayList<>();
        List<String> videoList = new ArrayList<>();
        if (!"".equals(problem_photo)) {
            if (problem_photo.contains(",")) {
                String[] split = problem_photo.split(",");
                for (int i = 0; i < split.length; i++) {
                    String s = split[i];
                    problem_photoList.add(s);
                }
            } else {
                problem_photoList.add(problem_photo);
            }
        }
        if (!"".equals(problem_video)) {
            if (problem_video.contains(",")) {
                String[] split = problem_video.split(",");
                for (int i = 0; i < split.length; i++) {
                    String s = split[i];
                    videoList.add(s);
                }
            } else {
                videoList.add(problem_video);
            }
        }

        photoList = new ArrayList<>();
        photoList.addAll(problem_photoList);
        photoList.addAll(videoList);
        gatherPhotoAdapter = new GatherPhotoAdapter(AddProblemActivity.this, photoList);//如果已经核查过 不能新增或删除
        gv_photo.setAdapter(gatherPhotoAdapter);
        gv_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (gatherPhotoAdapter.getmMaxPositon() <= 9) {
                    if (position == gatherPhotoAdapter.getmMaxPositon() - 1) {
                        new MaterialDialog.Builder(AddProblemActivity.this)
                                .items(R.array.take_photo)
                                .itemsCallback(new MaterialDialog.ListCallback() {
                                    @Override
                                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {

                                        switch (position) {
                                            case 0://拍照
                                                takePhotoOrVideo();
                                                break;
                                            case 1: //本地相册
                                                ImageSelectUtils.openPhoto(AddProblemActivity.this, ALBUM_REQUEST_CODE, false, 9);
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                })
                                .show();

                    } else {
                        if (photoList.get(position).endsWith("jpg")
                                || photoList.get(position).endsWith("JPG")
                                || photoList.get(position).endsWith("png")
                                || photoList.get(position).endsWith("PNG")
                                || photoList.get(position).endsWith("jpeg")
                                || photoList.get(position).endsWith("JPEG")) {
                            Intent intent = new Intent(AddProblemActivity.this, PhotoShowActivity.class);
                            intent.putStringArrayListExtra("photoList", (ArrayList<String>) photoList);
                            intent.putExtra("position", position);
                            startActivity(intent);
                        } else {
//                            Intent intent = new Intent(Intent.ACTION_VIEW);
//                            Uri data = Uri.parse(photoList.get(position));
//                            intent.setDataAndType(data, "video/mp4");
//                            startActivity(intent);

                            CommonUtils.openFile(new File(photoList.get(position)), AddProblemActivity.this);
                        }
                    }
                }
                if (gatherPhotoAdapter.getmMaxPositon() > 9 && position != gatherPhotoAdapter.getmMaxPositon() - 1) {
                    if (photoList.get(position).endsWith("jpg")
                            || photoList.get(position).endsWith("JPG")
                            || photoList.get(position).endsWith("png")
                            || photoList.get(position).endsWith("PNG")
                            || photoList.get(position).endsWith("jpeg")
                            || photoList.get(position).endsWith("JPEG")) {
                        Intent intent = new Intent(AddProblemActivity.this, PhotoShowActivity.class);
                        intent.putStringArrayListExtra("photoList", (ArrayList<String>) photoList);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        Uri data = Uri.parse(photoList.get(position));
                        intent.setDataAndType(data, "video/mp4");
                        startActivity(intent);
                    }
                }
            }
        });


        if (gatherPhotoAdapter.getmMaxPositon() < 9) {
            gv_photo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                    if (position == gatherPhotoAdapter.getmMaxPositon() - 1) {
                    } else {
                        new MaterialDialog.Builder(AddProblemActivity.this)
                                .title("删除文件！")
                                .content("确定删除当前文件！")
                                .positiveText("确定")
                                .negativeText("取消")
                                .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                                .onAny(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        if (which == DialogAction.POSITIVE) {

                                            String s = photoList.get(position);
                                            photoList.remove(position);
                                            new File(s).delete();
                                            gatherPhotoAdapter.notifyDataSetChanged();

                                            dialog.dismiss();

                                        } else if (which == DialogAction.NEGATIVE) {
                                            dialog.dismiss();
                                        }
                                    }
                                }).show();

                    }
                    return true;
                }
            });

        }

    }

    private File videoRename() {
        fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".mp4";
        //创建文件夹
        File out = new File(photoFolderAddress);
        if (!out.exists()) {
            out.mkdirs();
        }
        return new File(photoFolderAddress + File.separator + fileName);
    }

    private void takePhotoOrVideo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddProblemActivity.this);
        View view = View.inflate(AddProblemActivity.this, R.layout.photo_or_video, null);
        builder.setView(view);
        TextView tv_photo = view.findViewById(R.id.tv_photo);
        TextView tv_video = view.findViewById(R.id.tv_video);
        tv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri imageUri;
                //验证sd卡是否可用
                if (CommonUtils.getSDPath() == null) {
                    Toast.makeText(AddProblemActivity.this, "请安装SD卡", Toast.LENGTH_SHORT).show();
                    return;
                }
                File outputImage = new File(defaultPhotoAddress);
                //调用系统拍照
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(AddProblemActivity.this,
                            "com.appler.riverchiefsystem.fileprovider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CRAEMA_REQUEST_CODE);
                show.dismiss();
            }
        });
        tv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.media.action.VIDEO_CAPTURE");
                intent.addCategory("android.intent.category.DEFAULT");
//                if (file.exists()) {
//                    file.delete();
//                }
                file = videoRename();
                Uri uri2;
                if (Build.VERSION.SDK_INT >= 24) {
                    uri2 = FileProvider.getUriForFile(AddProblemActivity.this,
                            "com.appler.riverchiefsystem.fileprovider", file);
                } else {
                    uri2 = Uri.fromFile(file);
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri2);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 60);
//		                 intent.putExtra(MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED);
                startActivityForResult(intent, 3);
                show.dismiss();
            }
        });
        show = builder.show();
    }

    @OnClick({R.id.iv_toolbar_tv_back, R.id.tv_toolbar_tv_textview,
            R.id.tv_addProblem_type,
            R.id.tv_addProblem_report, R.id.tv_addProblem_state, R.id.tv_addProblem_receiver,
            R.id.tv_addProblem_position})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_tv_back:
                finish();
                break;
            case R.id.iv_toolbar_tv_imageview:  //问题转化成任务

                break;
            case R.id.tv_addProblem_receiver:
                Intent intent = new Intent(AddProblemActivity.this, ChooseUserActivity.class);
                intent.putExtra("type", Global.MESSAGE_TYPE_SHANGBAO);
                startActivityForResult(intent, Global.ADDPROBLEM_ACCEPT_CODE);
                break;
            case R.id.tv_toolbar_tv_textview: //提交问题，请求接口
                if ("".equals(tv_reason.getText().toString())) {
                    showToast("上报理由不能为空");
                } else if ("".equals(tv_riverName.getText().toString())) {
                    showToast("河段名称不能为空");
                } else {
                    uploadDialog = UploadDialogUtils.createLoadingDialog(AddProblemActivity.this, "正在提交");

                    //添加问题
                    submitProblem();
                }
                break;
            case R.id.tv_addProblem_type: //问题类型
                CommonUtils.SetSinnper(riverTypeList, tv_reason, AddProblemActivity.this, true);
                break;
            case R.id.tv_addProblem_state: //解决状态
                CommonUtils.SetSinnper(stateList, tv_state, AddProblemActivity.this, true);
                break;
            case R.id.tv_addProblem_position: //问题地点 选择负责河段
                CommonUtils.SetSinnper(riverReachList, tv_riverName, AddProblemActivity.this, true);
                break;
        }
    }


    /**
     * 新增问题提交
     */
    @SuppressLint("SimpleDateFormat")
    private void submitProblem() {
        Log.i(TAG, "submitProblem: " + longitude + "  " + latitude);
        String riverStr = tv_riverName.getText().toString();
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
        final OkHttpClient mOkHttpClient = new OkHttpClient();
        final MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        requestBody.addFormDataPart("flag", FlagConstant.FLAG_PROBLEM_ADD);
        requestBody.addFormDataPart("sys_problem_id", problem_id);
        requestBody.addFormDataPart("question", tv_reason.getText().toString());
        requestBody.addFormDataPart("sj_type", "河长上报");
        requestBody.addFormDataPart("name", AdminDao.getAdmin().getName());
        requestBody.addFormDataPart("river_id", riverId);
        requestBody.addFormDataPart("heduanname", heduanName);   //河段名称
        requestBody.addFormDataPart("sjc_name", sjcm);            //地址要所经村名
        requestBody.addFormDataPart("state", tv_state.getText().toString());
        requestBody.addFormDataPart("phone", AdminDao.getAdmin().getTelephone());
        requestBody.addFormDataPart("problem_miaoshu", et_describe.getText().toString());
        requestBody.addFormDataPart("updatetime", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        requestBody.addFormDataPart("fuzeren", "");
        requestBody.addFormDataPart("sb_id", String.valueOf(AdminDao.getAdminID()));
        requestBody.addFormDataPart("beizhu", et_remark.getText().toString());
        requestBody.addFormDataPart("patrol_record_id", patrol_id);
        requestBody.addFormDataPart("_x", longitude);
        requestBody.addFormDataPart("_y", latitude);


        final List<String> uploadList = new ArrayList<>();
        List<String> PhotoList = new ArrayList<>();
        List<String> Mp4List = new ArrayList<>();
        List<String> FileList = new ArrayList<>();
        if (tv_title.getText().toString().equals("编辑问题")) {
            for (int i = 0; i < photoList.size(); i++) {
                String s1 = photoList.get(i);
                if (s1.contains(Global.UPLOAD_FILEPATH)) {
                    if (s1.contains(".jpg")
                            || s1.contains(".JPG")
                            || s1.contains(".png")
                            || s1.contains(".PNG")
                            || s1.contains(".jpeg")
                            || s1.contains(".JPEG")) {
                        PhotoList.add(s1);
                    } else if (s1.contains(".mp4") || s1.contains(".MP4")) {
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
            requestBody.addFormDataPart("beforimg", beforimg);
            requestBody.addFormDataPart("beforvideo", beforvideo);
            requestBody.addFormDataPart("beforfile", "");
        } else {
            uploadList.addAll(photoList);
            requestBody.addFormDataPart("beforimg", "");
            requestBody.addFormDataPart("beforvideo", "");
            requestBody.addFormDataPart("beforfile", "");
        }


        for (int i = 0; i < uploadList.size(); i++) {
            String s1 = uploadList.get(i);
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
                Log.i(TAG, "submitProblem onFailure: " + message);
                e.printStackTrace();
                MainHandler.getInstance().post(new Runnable() {
                    @Override
                    public void run() {
                        UploadDialogUtils.closeDialog(uploadDialog);
                        ToastUtils.showShortToast(AddProblemActivity.this, message);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                Log.i(TAG, "submitProblem onResponse: " + string);
                MainHandler.getInstance().post(new Runnable() {
                    @Override
                    public void run() {
                        if ("Success".equals(string)) {
                            UploadDialogUtils.closeDialog(uploadDialog);

                            if (flag == 2) {  //编辑
                                showToast("编辑成功");
                                Intent intent = new Intent();
                                setResult(Global.PROBLEM_EDIT_CODE, intent);
                                finish();
                            } else {
                                showToast("上报成功");
                                finish();
                            }
                        } else {

                            UploadDialogUtils.closeDialog(uploadDialog);
                            if (flag == 2) {    //编辑
                                showToast("编辑失败");
                            } else {
                                showToast("上报失败");
                            }
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
            if (requestCode == CRAEMA_REQUEST_CODE) {

                //将原图片压缩拷贝到指定目录
                String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                final String targetPath = photoFolderAddress + File.separator + time + ".jpg";
                Log.i(TAG, " 拍照 onActivityResult: " + targetPath);
                CommonUtils.dealImage(defaultPhotoAddress, targetPath);
                //删除原图
                new File(defaultPhotoAddress).delete();
                if (photoList == null) {
                    photoList = new ArrayList<>();
                }
                photoList.add(targetPath);
                gatherPhotoAdapter.notifyDataSetChanged();
            } else if (requestCode == 3) {
                if (photoList == null) {
                    photoList = new ArrayList<>();
                }
//                fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".mp4";
//                photoList.add(photoFolderAddress + File.separator + fileName);
//                Log.i(TAG, "onActivityResult: " + photoFolderAddress + File.separator + fileName);
                photoList.add(photoFolderAddress + File.separator + fileName);
                Log.i(TAG, " 视频 onActivityResult: " + photoFolderAddress + File.separator + fileName);
                gatherPhotoAdapter.notifyDataSetChanged();
            } else if (requestCode == Global.ADDPROBLEM_ACCEPT_CODE) {
                ChooseUserData.DataBean dataBean = data.getParcelableExtra("chooseUserData");
                if (null != dataBean) {
                    juese = dataBean.getStaff_name() + " / " + dataBean.getStaff_hierarchy();
                    tv_receiver.setText(juese);
//                    accept_id = dataBean.getSys_staff_id();
                }
            } else if (requestCode == ALBUM_REQUEST_CODE) {//添加附件  从系统图库选择图片
                ArrayList<String> images = data.getStringArrayListExtra(ImageSelectUtils.SELECT_RESULT);
                photoList.addAll(images);
                gatherPhotoAdapter.notifyDataSetChanged();
            }
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
                ToastUtils.showShortToast(AddProblemActivity.this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAllUsers(String string) {

    }


    /**
     * 定位基本设置
     */
    private boolean initGpsSetting() {
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!isGpsAble(lm)) {
            Toast.makeText(this, "请打开GPS~", Toast.LENGTH_SHORT).show();
            openGPS2();
        }
        //从GPS获取最近的定位信息
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddProblemActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        Location lc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateShow(lc);
        //设置间隔两秒获得一次GPS定位信息
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // 当GPS定位信息发生改变时，更新定位
                updateShow(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                // 当GPS LocationProvider可用时，更新定位
                if (ActivityCompat.checkSelfPermission(AddProblemActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddProblemActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                updateShow(lm.getLastKnownLocation(provider));
            }

            @Override
            public void onProviderDisabled(String provider) {
                updateShow(null);
            }
        });
        return false;
    }

    //定义一个更新显示的方法
    private void updateShow(Location location) {
        if (location != null) {
            longitude = String.valueOf(location.getLongitude());
            latitude = String.valueOf(location.getLatitude());
        }
    }

    private boolean isGpsAble(LocationManager lm) {
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    //打开设置页面让用户自己设置
    private void openGPS2() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (uploadDialog != null) {
            uploadDialog.dismiss();
        }
    }


}