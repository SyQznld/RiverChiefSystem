package com.appler.riverchiefsystem.model;


import android.util.Log;

import com.appler.riverchiefsystem.base.BaseRetrofit;
import com.appler.riverchiefsystem.model.im.IMainModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainModel {

    private String TAG = this.getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private IMainModel iMainModel;

    public MainModel(IMainModel iMainModel) {
        this.iMainModel = iMainModel;
    }


    //消息列表
    public void getUnreadMessageList(CompositeSubscription compositeSubscription, String flag,int userId) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getMessageList(flag,userId)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getUnreadMessageList: " + e.getMessage());
                                iMainModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getUnreadMessageList: " + string);
                                    iMainModel.getUnreadMessageList(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iMainModel.onError();
                                }

                            }
                        })
        );
    }


    //单点登录判断
    public void onlyLoginMessage(CompositeSubscription compositeSubscription, String flag, String username, String IMEI) {
        compositeSubscription.add(
                baseRetrofit.getApiService().updateRegiser(username, IMEI)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError onlyLoginMessage: " + e.getMessage());
                                iMainModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext onlyLoginMessage: " + string);
                                    iMainModel.onlyLoginMessage(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iMainModel.onError();
                                }

                            }
                        })
        );
    }
}

