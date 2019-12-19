package com.appler.riverchiefsystem.model;


import android.util.Log;

import com.appler.riverchiefsystem.base.BaseRetrofit;
import com.appler.riverchiefsystem.model.im.IForgetPassModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class ForgetPassModel {

    private String TAG = this.getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private IForgetPassModel iForgetPassModel;

    public ForgetPassModel(IForgetPassModel iForgetPassModel) {
        this.iForgetPassModel = iForgetPassModel;
    }


    public void forgetPass(CompositeSubscription compositeSubscription, String flag, String username,
                           String condition,
                           String value,
                           String password) {
        compositeSubscription.add(
                baseRetrofit.getApiService().forgetPass(flag, username, condition, value, password)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError forgetPass: " + e.getMessage());
                                iForgetPassModel.forgetPass(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext forgetPass: " + string);
                                    iForgetPassModel.forgetPass(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })
        );
    }
}
