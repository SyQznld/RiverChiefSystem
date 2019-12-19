package com.appler.riverchiefsystem.presenter;


import com.appler.riverchiefsystem.base.BaseMvpPresenter;
import com.appler.riverchiefsystem.model.PersonelInfoModel;
import com.appler.riverchiefsystem.model.im.IPersonelInfoModel;
import com.appler.riverchiefsystem.view.PersonelInfoView;


public class PersonelInfoPresenter extends BaseMvpPresenter {
    private String TAG = this.getClass().getSimpleName();
    private PersonelInfoModel personelInfoModel;
    private PersonelInfoView personelInfoView;


    public PersonelInfoPresenter(final PersonelInfoView personelInfoView) {
        this.personelInfoView = personelInfoView;

        personelInfoModel = new PersonelInfoModel(new IPersonelInfoModel() {
            @Override
            public void editPassword(String string) {
                personelInfoView.editPassword(string);
            }

            @Override
            public void versionUpdate(String string) {
                personelInfoView.versionUpdate(string);
            }

            @Override
            public void exitApp(String string) {
                personelInfoView.exitApp(string);
            }

            @Override
            public void editPersonelInfo(String string) {
                personelInfoView.editPersonelInfo(string);
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


    public void editPersonelInfo(String flag, int userId, String tel) {
        personelInfoModel.editPersonelInfo(compositeSubscription, flag, userId, tel);
    }


    public void editPassword(String flag, int userId, String oldPwd, String newPwd) {
        personelInfoModel.editPassword(compositeSubscription, flag, userId, oldPwd, newPwd);
    }


    public void versionUpdate(String flag) {
        personelInfoModel.versionUpdate(compositeSubscription, flag);
    }


    public void exitApp(String user, String pass) {
        personelInfoModel.exitApp(compositeSubscription, user, pass);
    }


}
