package com.appler.riverchiefsystem.view;


import com.appler.riverchiefsystem.base.BaseView;

public interface MainView extends BaseView {
    void getUnreadMessageList(String string);

    void onlyLoginMessage(String string);

}
