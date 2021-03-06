package com.appler.riverchiefsystem.model;


import android.util.Log;

import com.appler.riverchiefsystem.base.BaseRetrofit;
import com.appler.riverchiefsystem.model.im.ITaskDetailModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class TaskDetailModel {

    private String TAG = this.getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private ITaskDetailModel iTaskDetailModel;

    public TaskDetailModel(ITaskDetailModel iTaskDetailModel) {
        this.iTaskDetailModel = iTaskDetailModel;
    }


    public void getTaskDetail(CompositeSubscription compositeSubscription, String flag, String msgId, String messageType) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getItemDetail(flag, msgId, messageType)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                iTaskDetailModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                iTaskDetailModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getTaskDetail: " + e.getMessage());
                                iTaskDetailModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getTaskDetail: " + string);
                                    iTaskDetailModel.getTaskDetail(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iTaskDetailModel.onError();
                                }

                            }
                        })
        );
    }


}

