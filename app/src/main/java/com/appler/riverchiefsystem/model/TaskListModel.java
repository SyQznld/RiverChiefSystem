package com.appler.riverchiefsystem.model;


import android.util.Log;

import com.appler.riverchiefsystem.base.BaseRetrofit;
import com.appler.riverchiefsystem.model.im.ITaskListModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class TaskListModel {

    private String TAG = this.getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private ITaskListModel iMyTaskListModel;

    public TaskListModel(ITaskListModel iMyTaskListModel) {
        this.iMyTaskListModel = iMyTaskListModel;
    }


    //我的任务 列表
    public void getTaskList(CompositeSubscription compositeSubscription, String flag,int userId, String type,String fzren,String keyword) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getTaskList(flag,userId,type,fzren,keyword)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getTaskList: " + e.getMessage());
                                iMyTaskListModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getTaskList: " + string);
                                    iMyTaskListModel.getTaskList(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iMyTaskListModel.onError();
                                }

                            }
                        })
        );
    }


    //下属人员
    public void getChargePerson(CompositeSubscription compositeSubscription, String flag, int userId) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getChargeHezhangList(flag, userId)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                iMyTaskListModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                iMyTaskListModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getChargePerson: " + e.getMessage());
                                iMyTaskListModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getChargePerson: " + string);
                                    iMyTaskListModel.getChargePerson(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iMyTaskListModel.onError();
                                }

                            }
                        })
        );
    }



}

