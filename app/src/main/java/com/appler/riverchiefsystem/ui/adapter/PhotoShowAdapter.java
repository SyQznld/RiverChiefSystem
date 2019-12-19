package com.appler.riverchiefsystem.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.utils.GlideUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class PhotoShowAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public PhotoItemClickListener photoItemClickListener;
    private String TAG = "PhotoShowAdapter";
    private Context context;
    private List<String> data;

    public PhotoShowAdapter(Context context, @Nullable List<String> data) {
        super(R.layout.photo_item, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        ImageView iv_photo = helper.getView(R.id.iv_photo_item);
        ImageView iv_video = helper.getView(R.id.iv_video);
        if (item.contains(".mp4") || item.contains(".MP4") || item.contains(".wav")) {
            iv_video.setVisibility(View.VISIBLE);
        } else {
            iv_video.setVisibility(View.GONE);
        }

        iv_photo.setScaleType(ImageView.ScaleType.FIT_XY);
        if (!item.contains(Global.URL)) {
            if (item.contains(Global.UPLOAD_FILEPATH)) {  //接口请求
                GlideUtils.loadNet(iv_photo, Global.URL + item, R.mipmap.ic_launcher_round);
            } else {   //本地拍照
                GlideUtils.loadNet(iv_photo, item, R.mipmap.ic_launcher_round);
            }
        } else {
            GlideUtils.loadNet(iv_photo, item, R.mipmap.ic_launcher_round);
        }
        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoItemClickListener.toShowPhoto(item, helper.getLayoutPosition());
            }
        });

        iv_photo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                photoItemClickListener.deletePhoto(item, helper.getLayoutPosition());
                return false;
            }
        });
    }

    public void setPhotoItemClickListener(PhotoItemClickListener photoItemClickListener) {
        this.photoItemClickListener = photoItemClickListener;
    }

    public interface PhotoItemClickListener {

        void toShowPhoto(String item, int position);

        void deletePhoto(String item, int position);
    }


}
