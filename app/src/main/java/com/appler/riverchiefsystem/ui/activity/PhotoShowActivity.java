package com.appler.riverchiefsystem.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.ui.adapter.MyPhotoShowAdapter;
import com.appler.riverchiefsystem.utils.customView.MyPhotoViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 图片显示
 */
public class PhotoShowActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView iv_back;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_title;
    @BindView(R.id.sv_search)
    SearchView sv_search;
    @BindView(R.id.vp_show_photo)
    MyPhotoViewPager myPhotoViewPager;
    @BindView(R.id.tv_photo_position)
    TextView tv_position;
    @BindView(R.id.tv_photo_count)
    TextView tv_count;
    private String TAG = getClass().getSimpleName();
    private int currentPosition;
    private MyPhotoShowAdapter adapter;
    private List<String> data = new ArrayList<>(); //
    private List<String> photoList = new ArrayList<>(); //当前选择的所有图片

    @Override
    protected int bindLayout() {
        return R.layout.activity_photo_show;
    }

    @Override
    protected void doBuissness(Context context) {
        tv_title.setText("图片查看");
        sv_search.setVisibility(View.GONE);
        currentPosition = getIntent().getIntExtra("position", 0);
        data = getIntent().getStringArrayListExtra("photoList");
        for (int i = 0; i < data.size(); i++) {
            String s = data.get(i);
            if (s.endsWith(".jpg") || s.endsWith(".png") || s.endsWith(".jpeg")) {
                photoList.add(s);
            }
        }

        adapter = new MyPhotoShowAdapter(photoList, this);
        myPhotoViewPager.setAdapter(adapter);
        myPhotoViewPager.setCurrentItem(currentPosition, false);
        tv_position.setText(currentPosition + 1 + "");
        tv_count.setText(photoList.size() + "");

        myPhotoViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                tv_position.setText(currentPosition + 1 + "");
                tv_count.setText(photoList.size() + "");
            }
        });

    }

    @OnClick({R.id.iv_toolbar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                finish();
                break;
        }
    }
}
