package com.appler.riverchiefsystem.model;


import android.util.Log;

import com.appler.riverchiefsystem.base.BaseRetrofit;
import com.appler.riverchiefsystem.model.im.ISuperviseDetailModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class SuperviseDetailModel {

    private String TAG = this.getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private ISuperviseDetailModel iSuperviseDetailModel;

    public SuperviseDetailModel(ISuperviseDetailModel iSuperviseDetailModel) {
        this.iSuperviseDetailModel = iSuperviseDetailModel;
    }


    public void getSuperviseDetail(CompositeSubscription compositeSubscription, String flag, String changeId) {
        Log.i(TAG, "getSuperviseDetail changeId: " + changeId);
        compositeSubscription.add(
                baseRetrofit.getApiService().getSuperviseDetail(flag, changeId)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
//                                iSuperviseDetailModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                iSuperviseDetailModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getSuperviseDetail: " + e.getMessage());
                                iSuperviseDetailModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getSuperviseDetail: " + string);
                                    iSuperviseDetailModel.getSuperviseDetail(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iSuperviseDetailModel.onError();
                                }

                            }
                        })
        );
    }


}

