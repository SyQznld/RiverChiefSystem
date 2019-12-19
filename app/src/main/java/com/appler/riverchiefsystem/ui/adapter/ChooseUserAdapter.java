package com.appler.riverchiefsystem.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.bean.ChooseData;
import com.appler.riverchiefsystem.bean.ChooseUserData;
import com.appler.riverchiefsystem.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseUserAdapter extends RecyclerView.Adapter<ChooseUserAdapter.ViewHolder> {

    private Context context;
    private List<ChooseData> datas;
    private OnItemClickListener onItemClickListener;


    public ChooseUserAdapter(Context context, List<ChooseData> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_user_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ChooseData chooseData = datas.get(holder.getAdapterPosition());
        ChooseUserData.DataBean dataBean = chooseData.getChooseUserData();
        GlideUtils.loadNet(holder.iv_member_img, dataBean.getStaff_photo(), R.mipmap.jiaojing);


        holder.tv_member_name.setText(dataBean.getStaff_name());
        holder.tv_member_hierarchy.setText(dataBean.getStaff_hierarchy());
        holder.tv_member_section.setText(dataBean.getSection_name());
        if (chooseData.isSelect()) {
            holder.mCheckBox.setImageResource(R.mipmap.ic_checked);
        } else {
            holder.mCheckBox.setImageResource(R.mipmap.ic_uncheck);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClickListener(holder.getAdapterPosition(), datas);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void notifyAdapter(List<ChooseData> datas, boolean isAdd) {
        if (!isAdd) {
            this.datas = datas;
        } else {
            this.datas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public List<ChooseData> getDatas() {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        return datas;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, List<ChooseData> datas);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_member_img)
        ImageView iv_member_img;
        @BindView(R.id.tv_member_name)
        TextView tv_member_name;
        @BindView(R.id.tv_member_hierarchy)
        TextView tv_member_hierarchy;
        @BindView(R.id.tv_member_section)
        TextView tv_member_section;
        @BindView(R.id.root_view)
        RelativeLayout mRootView;
        @BindView(R.id.check_box)
        ImageView mCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
