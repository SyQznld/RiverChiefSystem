package com.appler.riverchiefsystem.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.global.Global;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class GatherPhotoAdapter extends BaseAdapter {
    private Context context;
    private List<String> datas = new ArrayList<>();
    private LayoutInflater inflater;
    private int mMaxPositon;

    public GatherPhotoAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        mMaxPositon = datas.size() + 1;
        return mMaxPositon;
    }

    public int getmMaxPositon() {
        return mMaxPositon;
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.media_item_carema, parent, false);
            holder.iv_gridView_item = view.findViewById(R.id.iv_gridView_item);
            holder.rl_photo = view.findViewById(R.id.rl_photo);
            holder.iv_video = view.findViewById(R.id.iv_video);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (position == mMaxPositon - 1) {

                if (mMaxPositon > 9) {
                    holder.iv_gridView_item.setVisibility(View.GONE);
                    holder.iv_video.setVisibility(View.GONE);
                } else {
                    Glide.with(context).load(R.mipmap.image_photo).into(holder.iv_gridView_item);
                    holder.iv_gridView_item.setVisibility(View.VISIBLE);
                    holder.iv_video.setVisibility(View.INVISIBLE);
                }

        } else {
            String path = datas.get(position);
            if (!path.contains(Global.URL) ) {
                if (path.contains(Global.UPLOAD_FILEPATH)) {
                    Glide.with(context).load(Global.URL + path).into(holder.iv_gridView_item);
                } else {
                    Glide.with(context).load(path).into(holder.iv_gridView_item);
                }
            }
            String geshi = path;
            if (geshi.endsWith("jpg")) {
                holder.iv_video.setVisibility(View.INVISIBLE);
            } else if (geshi.endsWith("mp4")) {
                holder.iv_video.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }

    class ViewHolder {
        ImageView iv_gridView_item;
        ImageView iv_video;
        RelativeLayout rl_photo;

    }


}
