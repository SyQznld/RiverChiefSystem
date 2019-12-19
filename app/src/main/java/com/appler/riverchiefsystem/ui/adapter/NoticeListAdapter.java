package com.appler.riverchiefsystem.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.bean.NoticeData;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 通知公告列表 适配器
 */

public class NoticeListAdapter extends BaseQuickAdapter<NoticeData.DataBean, BaseViewHolder> {
    private String TAG = getClass().getSimpleName();
    private Context context;
    private List<NoticeData.DataBean> data;
    private NoticeItemClickListener noticeItemClickListener;

    public NoticeListAdapter(Context context, @Nullable List<NoticeData.DataBean> data) {
        super(R.layout.notice_item_layout, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final NoticeData.DataBean item) {
        CardView cv_notice = helper.getView(R.id.cv_notice);
        TextView tv_notice_content = helper.getView(R.id.tv_notice_content);
        TextView tv_notice_brief = helper.getView(R.id.tv_notice_brief);
        TextView tv_notice_updateTime = helper.getView(R.id.tv_notice_updateTime);
        tv_notice_content.setText(item.getName());

        tv_notice_brief.setText(item.getBrief());
        tv_notice_updateTime.setText(item.getUpdatetime());

        cv_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noticeItemClickListener.noticeItemClick(helper.getLayoutPosition(), item);
            }
        });


    }

    public void setNoticeItemClickListener(NoticeItemClickListener noticeItemClickListener) {
        this.noticeItemClickListener = noticeItemClickListener;
    }

    public interface NoticeItemClickListener {
        void noticeItemClick(int position, NoticeData.DataBean noticeData);
    }
}
