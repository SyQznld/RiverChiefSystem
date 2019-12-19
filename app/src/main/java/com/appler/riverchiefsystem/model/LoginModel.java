package com.appler.riverchiefsystem.model;


import android.util.Log;

import com.appler.riverchiefsystem.base.BaseRetrofit;
import com.appler.riverchiefsystem.model.im.ILoginModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class LoginModel {

    private String TAG = this.getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private ILoginModel iLoginModel;

    public LoginModel(ILoginModel iLoginModel) {
        this.iLoginModel = iLoginModel;
    }


    public void getLogin(CompositeSubscription compositeSubscription, String flag, String username, String password) {
        compositeSubscription.add(
                baseRetrofit.getApiService().appLogin(flag,username, password)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }
                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getLogin: " + e.getMessage());
                                iLoginModel.getLogin(e.getMessage());
                            }
                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getLogin: " + string);
                                    iLoginModel.getLogin(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })
        );
    }
}
