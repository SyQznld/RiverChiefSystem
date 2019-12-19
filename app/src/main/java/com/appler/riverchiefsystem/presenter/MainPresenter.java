package com.appler.riverchiefsystem.presenter;


import com.appler.riverchiefsystem.base.BaseMvpPresenter;
import com.appler.riverchiefsystem.model.MainModel;
import com.appler.riverchiefsystem.model.im.IMainModel;
import com.appler.riverchiefsystem.view.MainView;


public class MainPresenter extends BaseMvpPresenter {
    private String TAG = this.getClass().getSimpleName();
    private MainModel mainModel;
    private MainView mainView;


    public MainPresenter(final MainView mainView) {
        this.mainView = mainView;

        mainModel = new MainModel(new IMainModel() {
            @Override
            public void getUnreadMessageList(String string) {
                mainView.getUnreadMessageList(string);
            }

            @Override
            public void onlyLoginMessage(String string) {
                mainView.onlyLoginMessage(string);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError() {

            }
        });
    }


    public void getUnreadMessageList(String flag, int userId) {
        mainModel.getUnreadMessageList(compositeSubscription, flag, userId);
    }

    public void onlyLoginMessage(String flag, String username, String IMEI) {
        mainModel.onlyLoginMessage(compositeSubscription, flag, username, IMEI);
    }


}
