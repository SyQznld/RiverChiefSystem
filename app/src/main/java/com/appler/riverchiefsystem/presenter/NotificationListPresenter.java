package com.appler.riverchiefsystem.presenter;


import com.appler.riverchiefsystem.base.BaseMvpPresenter;
import com.appler.riverchiefsystem.model.NotificationListModel;
import com.appler.riverchiefsystem.model.im.INotificationListModel;
import com.appler.riverchiefsystem.view.NotificationListView;


public class NotificationListPresenter extends BaseMvpPresenter {
    private String TAG = this.getClass().getSimpleName();
    private NotificationListModel notificationListModel;
    private NotificationListView notificationListView;


    public NotificationListPresenter(final NotificationListView notificationListView) {
        this.notificationListView = notificationListView;
        notificationListModel = new NotificationListModel(new INotificationListModel() {


            @Override
            public void getPCNotificationList(String string) {  //通知公告
                notificationListView.getPCNotificationList(string);
            }

            @Override
            public void getMessageList(String string) {  //消息列表
                notificationListView.getMessageList(string);
            }

            @Override
            public void deleteNotificationItem(String string) {
                notificationListView.deleteNotificationItem(string);
            }

            @Override
            public void deleteMessageItem(String string) {
                notificationListView.deleteMessageItem(string);
            }

            @Override
            public void updateMessageState(String string) {
                notificationListView.updateMessageState(string);
            }

            @Override
            public void onStart() {
//                notificationListView.setState(Global.LOADING_DOING);
            }

            @Override
            public void onComplete() {
//                notificationListView.setState(Global.LOADING_SUCCESS);
            }

            @Override
            public void onError() {
//                notificationListView.setState(Global.LOADING_FAIL);
            }
        });
    }


    public void getPCNoticeList(String flag, String keyword) {
        notificationListModel.getPCNoticeList(compositeSubscription, flag, keyword);
    }

    public void getMessageList(String flag, int userId) {
        notificationListModel.getMessageList(compositeSubscription, flag, userId);
    }


    public void updateMessageState(String flag, String messageId) {
        notificationListModel.updateMessageState(compositeSubscription, flag, messageId);
    }

    public void deleteSingleMessage(String flag, String messageId) {
        notificationListModel.deleteSingleMessage(compositeSubscription, flag, messageId);
    }


}
