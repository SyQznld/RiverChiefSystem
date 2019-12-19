package com.appler.riverchiefsystem.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.bean.ProblemTypeData;

import java.util.List;

//添加问题 类型 二级列表
public class ProblemTypeAdapter extends BaseExpandableListAdapter {

    public ChildItemClickListener childItemClickListener;
    private Context context;
    private LayoutInflater layoutInflater;
    private List<ProblemTypeData.DataBean> dataBeanList;

    public ProblemTypeAdapter(Context context, List<ProblemTypeData.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return dataBeanList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return dataBeanList.get(i).getChild().size();
//        return dataBeanList.get(i).getChild().size() > 0  ? dataBeanList.get(i).getChild().size() : 0;
    }

    @Override
    public Object getGroup(int i) {
        return dataBeanList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return dataBeanList.get(i).getChild().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_protype_group, viewGroup, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tv_groupname = view.findViewById(R.id.tv_group);
            groupViewHolder.ll_group = view.findViewById(R.id.ll_group);
            view.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }

        ProblemTypeData.DataBean dataBean = dataBeanList.get(groupPosition);
        groupViewHolder.tv_groupname.setText(dataBean.getParents());

        return view;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean b, View view, ViewGroup viewGroup) {
        final ChildViewHolder childViewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_protype_child, viewGroup, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tv_child = view.findViewById(R.id.tv_child);
            childViewHolder.ll_child = view.findViewById(R.id.ll_child);

            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        ProblemTypeData.DataBean dataBean = dataBeanList.get(groupPosition);
        List<ProblemTypeData.DataBean.ChildBean> root = dataBean.getChild();
        final ProblemTypeData.DataBean.ChildBean childBean = root.get(childPosition);
        String typeName = childBean.getProbledetail();
        if ("".equals(typeName)) {
            childViewHolder.ll_child.setVisibility(View.GONE);
        } else {
            childViewHolder.ll_child.setVisibility(View.VISIBLE);
            childViewHolder.tv_child.setText(typeName);
            childViewHolder.ll_child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    childItemClickListener.childItemClick(childBean);
                }
            });

        }


        return view;
    }

    public void setChildItemClickListener(ChildItemClickListener childItemClickListener) {
        this.childItemClickListener = childItemClickListener;
    }


    public interface ChildItemClickListener {
        void childItemClick(ProblemTypeData.DataBean.ChildBean childBean);
    }

    class GroupViewHolder {
        TextView tv_groupname;
        LinearLayout ll_group;
    }

    class ChildViewHolder {
        TextView tv_child;
        LinearLayout ll_child;
    }

}
