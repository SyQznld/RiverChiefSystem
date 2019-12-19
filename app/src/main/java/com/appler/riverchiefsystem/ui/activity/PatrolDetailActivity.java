package com.appler.riverchiefsystem.ui.activity;

import android.content.Context;
import android.content.Intent;
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
import com.appler.riverchiefsystem.bean.PatrolData;
import com.appler.riverchiefsystem.bean.ProblemData;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.presenter.PatrolDetailPresenter;
import com.appler.riverchiefsystem.ui.adapter.PhotoShowAdapter;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.appler.riverchiefsystem.view.PatrolDetailView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PatrolDetailActivity extends BaseActivity implements PatrolDetailView {

    @BindView(R.id.iv_toolbar_back)
    ImageView iv_back;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_title;
    @BindView(R.id.iv_toolbar_imageview1)
    ImageView iv_problemList;
    @BindView(R.id.sv_search)
    SearchView sv_search;
    @BindView(R.id.tv_patrolDetail_heduanName)
    TextView tv_heduanName;
    @BindView(R.id.tv_patrolDetail_sjcm)
    TextView tv_sjcm;
    @BindView(R.id.tv_patrolDetail_begintime)
    TextView tv_begintime;
    @BindView(R.id.tv_patrolDetail_endtime)
    TextView tv_endtime;
    @BindView(R.id.ll_patrolDetail_photos)
    LinearLayout ll_photos;
    @BindView(R.id.rv_patrolDetail_photos)
    RecyclerView rv_photos;
    private String TAG = getClass().getSimpleName();
    private PatrolData.DataBean dataBean;
    private PatrolDetailPresenter patrolDetailPresenter;


    @Override
    protected int bindLayout() {
        return R.layout.activity_patrol_detail;
    }

    @Override
    protected void doBuissness(final Context context) {
        patrolDetailPresenter = new PatrolDetailPresenter(this);
        tv_title.setText("巡查详情");
        dataBean = getIntent().getParcelableExtra("patrol");
        sv_search.setVisibility(View.GONE);

        //巡查过程中关联问题
        patrolDetailPresenter.getPatrolProblemList(FlagConstant.FLAG_PATROL_PROBLEM_LIST, dataBean.getSys_patrol_record_id(), "", "", "");
        tv_heduanName.setText(dataBean.getRivername());
        tv_sjcm.setText(dataBean.getAddress());
        tv_begintime.setText(dataBean.getPatrol_record_begintime());
        tv_endtime.setText(dataBean.getPatrol_record_endtime());

        final List<String> datas = new ArrayList<>();
        String problem_photo = dataBean.getPatrol_record_photo();
        String problem_video = dataBean.getPatrol_record_video();
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


        datas.addAll(photoList);
        datas.addAll(videoList);
        Log.i(TAG, "doBuissness: " + datas.size() + datas);
        if (datas.size() > 0) {
            ll_photos.setVisibility(View.VISIBLE);
            rv_photos.setLayoutManager(new GridLayoutManager(PatrolDetailActivity.this, 3));
            PhotoShowAdapter photoShowAdapter = new PhotoShowAdapter(PatrolDetailActivity.this, datas);
            rv_photos.setAdapter(photoShowAdapter);
            photoShowAdapter.setPhotoItemClickListener(new PhotoShowAdapter.PhotoItemClickListener() {
                @Override
                public void toShowPhoto(String item, int position) {
                    if (datas.get(position).endsWith("jpg")
                            || datas.get(position).endsWith("png")
                            || datas.get(position).endsWith("jpeg")
                            || datas.get(position).endsWith("JPG")
                            || datas.get(position).endsWith("PNG")
                            || datas.get(position).endsWith("JPEG")) {
                        Intent intent = new Intent(PatrolDetailActivity.this, PhotoShowActivity.class);
                        intent.putStringArrayListExtra("photoList", (ArrayList<String>) datas);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    } else {
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        Uri data = Uri.parse(datas.get(position));
//                        intent.setDataAndType(data, "video/mp4");
//                        startActivity(intent);
                        Intent intent = new Intent(PatrolDetailActivity.this, VideoShowActivity.class);
                        intent.putExtra("videoPath", Global.URL + datas.get(position));
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

    @OnClick({R.id.iv_toolbar_back, R.id.iv_toolbar_imageview, R.id.iv_toolbar_imageview1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                finish();
                break;
            case R.id.iv_toolbar_imageview1://关联巡查记录  问题列表
                Intent proListIntent = new Intent(PatrolDetailActivity.this, ProblemListActivity.class);
                proListIntent.putExtra("flag", 1);
                proListIntent.putExtra("patrol_id", dataBean.getSys_patrol_record_id());
                startActivity(proListIntent);
                break;
        }
    }


    @Override
    public void getPrtaolProList(String string) {
        Log.i(TAG, "getPrtaolProList: " + string);
        try {
            JSONObject jsonObject = new JSONObject(string);
            String msg = jsonObject.getString("msg");
            if ("success".equals(msg)) {
                ProblemData problemData = new Gson().fromJson(string, new TypeToken<ProblemData>() {
                }.getType());
                List<ProblemData.DataBean> data = problemData.getData();
                if (data.size() > 0) {
                    iv_problemList.setVisibility(View.VISIBLE);
                    iv_problemList.setImageResource(R.drawable.ic_problemlist);
                } else {
                    iv_problemList.setVisibility(View.GONE);
                }
            } else {
                ToastUtils.showShortToast(PatrolDetailActivity.this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
