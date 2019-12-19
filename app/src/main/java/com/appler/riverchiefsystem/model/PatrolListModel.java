package com.appler.riverchiefsystem.model;


import android.util.Log;

import com.appler.riverchiefsystem.base.BaseRetrofit;
import com.appler.riverchiefsystem.model.im.IPatrolListModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class PatrolListModel {

    private String TAG = this.getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private IPatrolListModel iPatrolListModel;

    public PatrolListModel(IPatrolListModel iPatrolListModel) {
        this.iPatrolListModel = iPatrolListModel;
    }

    //巡查记录列表
    public void getPatrolList(CompositeSubscription compositeSubscription, String flag, int SysStaffId, String keyword) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getPatrolList(flag, SysStaffId, keyword)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                iPatrolListModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                iPatrolListModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getPatrolList: " + e.getMessage());
                                iPatrolListModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getPatrolList: " + string);
                                    iPatrolListModel.getPatrolList(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iPatrolListModel.onError();
                                }

                            }
                        })
        );
    }


    public void getChargePerson(CompositeSubscription compositeSubscription, String flag, int userId) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getChargeHezhangList(flag, userId)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
//                                iPatrolListModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
//                                iPatrolListModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getChargePerson: " + e.getMessage());
                                iPatrolListModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getChargePerson: " + string);
                                    iPatrolListModel.getChargePerson(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iPatrolListModel.onError();
                                }

                            }
                        })
        );
    }
}

