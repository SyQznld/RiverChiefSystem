package com.appler.riverchiefsystem.ui.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.appler.riverchiefsystem.global.Global;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;


public class MyPhotoShowAdapter extends PagerAdapter {
    public static final String TAG = MyPhotoShowAdapter.class.getSimpleName();
    private List<String> imageUrls;
    private Activity activity;

    public MyPhotoShowAdapter(List<String> imageUrls, Activity activity) {
        this.imageUrls = imageUrls;
        this.activity = activity;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String url = imageUrls.get(position);
        PhotoView photoView = new PhotoView(activity);
        if (!url.contains(Global.URL)) {
            if (url.contains(Global.UPLOAD_FILEPATH)) {
                Glide.with(activity).load(Global.URL + url).into(photoView);
            } else {
                Glide.with(activity).load(url).into(photoView);
            }
        } else {
            Glide.with(activity).load(url).into(photoView);
        }
        container.addView(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
        return photoView;
    }

    @Override
    public int getCount() {
        return imageUrls != null ? imageUrls.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}