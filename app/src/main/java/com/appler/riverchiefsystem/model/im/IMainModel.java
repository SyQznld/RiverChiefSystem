package com.appler.riverchiefsystem.model.im;


import com.appler.riverchiefsystem.base.IModel;

public interface IMainModel extends IModel {
    void getUnreadMessageList(String string);

    void onlyLoginMessage(String string);

}
