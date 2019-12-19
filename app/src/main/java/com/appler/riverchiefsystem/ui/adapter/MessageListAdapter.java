package com.appler.riverchiefsystem.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.bean.MessageData;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;


public class MessageListAdapter extends BaseQuickAdapter<MessageData.DataBean, BaseViewHolder> {
    private Context context;
    private List<MessageData.DataBean> data;
    private OnNotificationItemClickListener notificationItemClickListener;

    public MessageListAdapter(Context context, @Nullable List<MessageData.DataBean> data) {
        super(R.layout.message_item_layout, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MessageData.DataBean item) {

        String message_name = item.getMessage_name();
        helper.setText(R.id.tv_message_title, message_name);
        String message_type = item.getMessage_type();
        helper.setText(R.id.tv_message_classify, message_type);
        String create_time = item.getCreate_time();
        helper.setText(R.id.tv_message_time, create_time);


        // 0表示未读 1表示已读
        String messageState = item.getMessage_state();
        ImageView iv_state = helper.getView(R.id.iv_message_dot);
        if ("否".equals(messageState)) {
            iv_state.setImageResource(R.drawable.ic_dot_red);
        } else if ("是".equals(messageState)) {
            iv_state.setImageResource(R.drawable.ic_dot_blue);

        }
        LinearLayout ll_message_item = helper.getView(R.id.ll_message_item);
        ll_message_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationItemClickListener.checkMessageDetail(helper.getLayoutPosition(), item);
            }
        });

        final SwipeMenuLayout sml_message = helper.getView(R.id.sml_message);
        final Button btn_delete = helper.getView(R.id.btn_message_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationItemClickListener.deleteMessageItem(helper.getLayoutPosition(), item);
                notifyDataSetChanged();
                sml_message.quickClose();
            }
        });


    }

    public void setNotificationItemClickListener(OnNotificationItemClickListener notificationItemClickListener) {
        this.notificationItemClickListener = notificationItemClickListener;
    }

    public interface OnNotificationItemClickListener {
        void checkNotificationDetail(int position, MessageData.DataBean dataBean);

        void checkMessageDetail(int position, MessageData.DataBean dataBean);

        void deleteNotificationItem(int position, MessageData.DataBean dataBean);

        void deleteMessageItem(int position, MessageData.DataBean dataBean);

    }
}