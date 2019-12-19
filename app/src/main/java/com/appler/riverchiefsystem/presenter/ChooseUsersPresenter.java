package com.appler.riverchiefsystem.presenter;


import com.appler.riverchiefsystem.base.BaseMvpPresenter;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.model.ChooseUsersModel;
import com.appler.riverchiefsystem.model.im.IChooseUsersModel;
import com.appler.riverchiefsystem.view.ChooseUsersView;


public class ChooseUsersPresenter extends BaseMvpPresenter {
    private String TAG = this.getClass().getSimpleName();
    private ChooseUsersModel chooseUsersModel;
    private ChooseUsersView chooseUsersView;


    public ChooseUsersPresenter(final ChooseUsersView chooseUsersView) {
        this.chooseUsersView = chooseUsersView;

        chooseUsersModel = new ChooseUsersModel(new IChooseUsersModel() {
            @Override
            public void getAllUsers(String string) {
                chooseUsersView.getAllUsers(string);
            }

            @Override
            public void getChargeUsers(String string) {
                chooseUsersView.getChargeUsers(string);
            }

            @Override
            public void getSuperiorPerson(String string) {
                chooseUsersView.getSuperiorPerson(string);
            }

            @Override
            public void getAllZongluzhang(String string) {
                chooseUsersView.getAllZongluzhang(string);
            }

            @Override
            public void getYiErJiLuzhang(String string) {
                chooseUsersView.getYiErJiLuzhang(string);
            }

            @Override
            public void onStart() {
                chooseUsersView.setState(Global.LOADING_DOING);
            }

            @Override
            public void onComplete() {
                chooseUsersView.setState(Global.LOADING_SUCCESS);
            }

            @Override
            public void onError() {
                chooseUsersView.setState(Global.LOADING_FAIL);
            }
        });
    }


//    public void getAllUsers(String flag) {
//        chooseUsersModel.getAllUsers(compositeSubscription, flag);
//    }


    public void getAllZongluzhang(String flag) {
        chooseUsersModel.getAllZongluzhang(compositeSubscription, flag);
    }

    public void getSuperiorPerson(String flag, String sys_staff_id) {
        chooseUsersModel.getSuperiorPerson(compositeSubscription, flag, sys_staff_id);
    }

    public void getChargeUsers(String flag, int userId) {
        chooseUsersModel.getChargeUsers(compositeSubscription, flag, userId);
    }

    public void getYiErJiLuzhang(String flag, String section_name) {
        chooseUsersModel.getYiErJiLuzhang(compositeSubscription, flag, section_name);
    }
}
