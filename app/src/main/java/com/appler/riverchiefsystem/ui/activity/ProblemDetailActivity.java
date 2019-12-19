package com.appler.riverchiefsystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
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
import com.appler.riverchiefsystem.bean.ProblemData;
import com.appler.riverchiefsystem.dao.AdminDao;
import com.appler.riverchiefsystem.dao.daoBean.AdminData;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.presenter.ProblemDetailPresenter;
import com.appler.riverchiefsystem.ui.adapter.PhotoShowAdapter;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.appler.riverchiefsystem.view.ProblemDetailView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ProblemDetailActivity extends BaseActivity implements ProblemDetailView {
    @BindView(R.id.iv_toolbar_back)
    ImageView iv_back;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_title;
    @BindView(R.id.iv_toolbar_imageview1)
    ImageView iv_edit;
    @BindView(R.id.iv_toolbar_imageview)
    ImageView iv_addTask;
    @BindView(R.id.sv_search)
    SearchView sv_search;
    @BindView(R.id.tv_problemDetail_reason)
    TextView tv_reason;
    @BindView(R.id.tv_problemDetail_type)
    TextView tv_type;
    @BindView(R.id.tv_problemDetail_describe)
    TextView tv_describe;
    @BindView(R.id.tv_problemDetail_riverName)
    TextView tv_riverName;
    @BindView(R.id.tv_problemDetail_address)
    TextView tv_address;
    @BindView(R.id.tv_problemDetail_acceptName)
    TextView tv_acceptName;
    @BindView(R.id.tv_problemDetail_resove)
    TextView tv_resove;
    @BindView(R.id.tv_problemDetail_reporttime)
    TextView tv_acceptNametime;
    @BindView(R.id.ll_problemDetail_check)
    LinearLayout ll_check;
    @BindView(R.id.tv_problemDetail_solution)
    TextView tv_solution;
    @BindView(R.id.tv_problemDetail_remark)
    TextView tv_remark;
    @BindView(R.id.ll_problemDetail_photos)
    LinearLayout ll_photos;
    @BindView(R.id.rv_problemDetail_photos)
    RecyclerView rv_photos;
    private String TAG = getClass().getSimpleName();
    private ProblemDetailPresenter problemDetailPresenter;
    private ProblemData.DataBean dataBean = new ProblemData.DataBean();
    private String problem_detail = "";//问题描述
    private String msgId;

    @Override
    protected int bindLayout() {
        return R.layout.activity_problem_detail;
    }

    @Override
    protected void doBuissness(final Context context) {
        tv_title.setText("上报详情");
        sv_search.setVisibility(View.GONE);
        problemDetailPresenter = new ProblemDetailPresenter(this);
        msgId = getIntent().getStringExtra("msgId");
    }


    private void initProblemDetail(ProblemData.DataBean dataBean) {
        String sjzt = dataBean.get事件状态();
        if (!"已解决".equals(sjzt)) {
            iv_edit.setVisibility(View.VISIBLE);
            iv_edit.setImageResource(R.drawable.ic_edit);
        } else {
            iv_edit.setVisibility(View.GONE);
        }

        tv_reason.setText(dataBean.get上报理由());
        String problem_type = dataBean.get事件类型();
        tv_type.setText(problem_type);
        problem_detail = dataBean.get问题描述();
        tv_describe.setText(problem_detail);
        tv_riverName.setText(dataBean.get河道名称());
        tv_address.setText(dataBean.get地址());
        tv_acceptName.setText(dataBean.getFz_name());
        String problem_resove = sjzt;
        tv_resove.setText(problem_resove);
        tv_acceptNametime.setText(dataBean.getUpdatetime());
        tv_solution.setText(dataBean.get处理方式());
        tv_remark.setText(dataBean.get备注());


        String problem_photo = dataBean.getPicture();
        String problem_video = dataBean.getVideo();
        List<String> photoList = new ArrayList<>();
        List<String> videoList = new ArrayList<>();
        if (!"".equals(problem_photo)) {
            if (problem_photo.contains(",")) {
                String[] split = problem_photo.split(",");
                for (int i = 0; i < split.length; i++) {
                    String s = split[i];
                    photoList.add(s);
                }
            } else {
                photoList.add(problem_photo);
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

        final List<String> datas = new ArrayList<>();
        datas.addAll(photoList);
        datas.addAll(videoList);

        if (datas.size() > 0) {
            ll_photos.setVisibility(View.VISIBLE);
            rv_photos.setLayoutManager(new GridLayoutManager(ProblemDetailActivity.this, 3));
            PhotoShowAdapter photoShowAdapter = new PhotoShowAdapter(ProblemDetailActivity.this, datas);

            rv_photos.setAdapter(photoShowAdapter);
            photoShowAdapter.setPhotoItemClickListener(new PhotoShowAdapter.PhotoItemClickListener() {
                @Override
                public void toShowPhoto(String item, int position) {
                    Log.i(TAG, "toShowPhoto: " + datas.get(position));
                    if (datas.get(position).endsWith("jpg")
                            || datas.get(position).endsWith("png")
                            || datas.get(position).endsWith("jpeg")
                            || datas.get(position).endsWith("JPG")
                            || datas.get(position).endsWith("PNG")
                            || datas.get(position).endsWith("JPEG")) {
                        Intent intent = new Intent(ProblemDetailActivity.this, PhotoShowActivity.class);
                        intent.putStringArrayListExtra("photoList", (ArrayList<String>) datas);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    } else {
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        Uri data = Uri.parse(datas.get(position));
//                        intent.setDataAndType(data, "video/mp4");
//                        startActivity(intent);
                        Intent intent = new Intent(ProblemDetailActivity.this,VideoShowActivity.class);
                        intent.putExtra("videoPath",Global.URL + datas.get(position));
                        startActivity(intent);
                    }
                }

                @Override
                public void deletePhoto(String item, int position) {

                }
            });
        } else {
            rv_photos.setVisibility(View.GONE);
            ll_photos.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.iv_toolbar_back, R.id.iv_toolbar_imageview1, R.id.iv_toolbar_imageview, R.id.iv_toolbar_imageview2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                finish();
                break;
            case R.id.iv_toolbar_imageview1: //编辑问题
                Intent editIntent = new Intent(ProblemDetailActivity.this, AddProblemActivity.class);
                editIntent.putExtra("problem", dataBean);
                editIntent.putExtra("flag", 2);
                startActivityForResult(editIntent,Global.PROBLEM_EDIT_CODE);
                break;
            case R.id.iv_toolbar_imageview:  //将问题转化成任务
                break;
        }
    }


    @Override
    public void getProblemDetail(String string) {
        Log.i(TAG, "getProblemDetail: " + string);
        try {
            JSONObject jsonObject = new JSONObject(string);
            String msg = jsonObject.getString("msg");
            if ("success".equals(msg)) {
                ProblemData data = new Gson().fromJson(string, new TypeToken<ProblemData>() {
                }.getType());
                int size = data.getData().size();
                if (size > 0) {
                    dataBean = data.getData().get(0);
                    initProblemDetail(dataBean);
                }
            } else {
                ToastUtils.showShortToast(ProblemDetailActivity.this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode  == RESULT_OK){
            switch (requestCode){
                case Global.PROBLEM_EDIT_CODE:
                    requestProDetail();
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestProDetail();
    }

    private void requestProDetail() {
        AdminData admin = AdminDao.getAdmin();
        if (null != admin) {
            problemDetailPresenter.getProblemDetail(FlagConstant.FLAG_MESSAGEITEM_DETAIL, msgId, Global.MESSAGE_TYPE_SHANGBAO);
        }
    }

}
