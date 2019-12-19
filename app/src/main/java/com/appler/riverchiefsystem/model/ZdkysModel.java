package com.appler.riverchiefsystem.model;


import android.util.Log;

import com.appler.riverchiefsystem.base.BaseRetrofit;
import com.appler.riverchiefsystem.model.im.IZdkysModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ZdkysModel {

    private String TAG = this.getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private IZdkysModel iZdkysProModel;

    public ZdkysModel(IZdkysModel iZdkysProModel) {
        this.iZdkysProModel = iZdkysProModel;
    }


    public void getZdkysProList(CompositeSubscription compositeSubscription, String flag, String userId,
                                String problem_task, String problem_descibe) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getZDKYSProList(flag, userId,
                        problem_task, problem_descibe)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                iZdkysProModel.onComplete();
                            }


                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getZdkysProList: " + e.getMessage());
                                iZdkysProModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getZdkysProList: " + string);
                                    iZdkysProModel.getZdkysProList(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iZdkysProModel.onError();
                                }

                            }
                        })
        );
    }


    //支队科研所 向总路长添加任务
    public void zdkysProToTask(CompositeSubscription compositeSubscription,
                               String flag,
                               String sys_problem_id,
                               String launch_staff_id,
                               String task_createtime,
                               String task_expecttime,
                               String task_detail) {
        compositeSubscription.add(
                baseRetrofit.getApiService().zdkysUpdateProTask(
                        flag,
                        sys_problem_id,
                        launch_staff_id,
                        task_createtime,
                        task_expecttime,
                        task_detail)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                iZdkysProModel.onComplete();
                            }


                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError zdkysProToTask: " + e.getMessage());
                                iZdkysProModel.zdkysProToTask(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext zdkysProToTask: " + string);
                                    iZdkysProModel.zdkysProToTask(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iZdkysProModel.onError();
                                }

                            }
                        })
        );
    }


}

