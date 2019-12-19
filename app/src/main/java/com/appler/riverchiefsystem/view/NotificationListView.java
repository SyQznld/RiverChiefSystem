package com.appler.riverchiefsystem.view;


import com.appler.riverchiefsystem.base.BaseView;

public interface NotificationListView extends BaseView {

    void getPCNotificationList(String string);  //通知公告


    void deleteNotificationItem(String string);

    void getMessageList(String string);  //消息列表
    void deleteMessageItem(String string);
    void updateMessageState(String string);
}
