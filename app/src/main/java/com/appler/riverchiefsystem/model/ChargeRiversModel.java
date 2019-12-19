package com.appler.riverchiefsystem.model;


import android.util.Log;

import com.appler.riverchiefsystem.base.BaseRetrofit;
import com.appler.riverchiefsystem.model.im.IChargeRiversModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ChargeRiversModel {

    private String TAG = this.getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private IChargeRiversModel iChargeRiversModel;

    public ChargeRiversModel(IChargeRiversModel iChargeRiversModel) {
        this.iChargeRiversModel = iChargeRiversModel;
    }


    //获取负责河段
    public void getChargRivers(CompositeSubscription compositeSubscription, String flag, int staffId) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getChargeRivers(flag, staffId)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                iChargeRiversModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                iChargeRiversModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "getChargRivers onError: " + e.getMessage());
                                iChargeRiversModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "getChargRivers onNext: " + string);
                                    iChargeRiversModel.getChargeRivers(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iChargeRiversModel.onError();
                                }

                            }
                        })
        );
    }


    //所有用户
    public void getAllUsers(CompositeSubscription compositeSubscription, String flag, String keyword) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getAllUsers(flag, keyword)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                iChargeRiversModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                iChargeRiversModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                iChargeRiversModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    iChargeRiversModel.getAllUsers(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iChargeRiversModel.onError();
                                }

                            }
                        })
        );
    }


}

