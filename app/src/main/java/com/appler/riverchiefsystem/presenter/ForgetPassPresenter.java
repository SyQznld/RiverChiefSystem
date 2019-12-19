package com.appler.riverchiefsystem.presenter;


import com.appler.riverchiefsystem.base.BaseMvpPresenter;
import com.appler.riverchiefsystem.model.ForgetPassModel;
import com.appler.riverchiefsystem.model.im.IForgetPassModel;
import com.appler.riverchiefsystem.view.ForgetPassView;


public class ForgetPassPresenter extends BaseMvpPresenter {
    private String TAG = this.getClass().getSimpleName();
    private ForgetPassModel forgetPassModel;
    private ForgetPassView forgetPassView;


    public ForgetPassPresenter(final ForgetPassView forgetPassView) {
        this.forgetPassView = forgetPassView;

        forgetPassModel = new ForgetPassModel(new IForgetPassModel() {
            @Override
            public void forgetPass(String string) {
                forgetPassView.forgetPass(string);
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


    public void forgetPass(String flag,
                           String username,
                           String condition,
                           String value,
                           String password) {
        forgetPassModel.forgetPass(compositeSubscription, flag, username, condition, value, password);
    }
}
