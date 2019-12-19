package com.appler.riverchiefsystem.model.im;


import com.appler.riverchiefsystem.base.IModel;

public interface INotificationListModel extends IModel {

    void getPCNotificationList(String string);

    void getMessageList(String string);

    void deleteNotificationItem(String string);

    void deleteMessageItem(String string);
    void updateMessageState(String string);
}
