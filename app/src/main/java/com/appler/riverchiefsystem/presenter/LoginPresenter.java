package com.appler.riverchiefsystem.presenter;


import com.appler.riverchiefsystem.base.BaseMvpPresenter;
import com.appler.riverchiefsystem.model.LoginModel;
import com.appler.riverchiefsystem.model.im.ILoginModel;
import com.appler.riverchiefsystem.view.LoginView;


public class LoginPresenter extends BaseMvpPresenter {
    private String TAG = this.getClass().getSimpleName();
    private LoginModel loginModel;
    private LoginView loginView;


    public LoginPresenter(final LoginView loginView) {
        this.loginView = loginView;

        loginModel = new LoginModel(new ILoginModel() {
            @Override
            public void getLogin(String string) {
                loginView.getLogin(string);
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


    public void getLogin(String flag,String username, String password) {
        loginModel.getLogin(compositeSubscription,flag, username, password);
    }
}
