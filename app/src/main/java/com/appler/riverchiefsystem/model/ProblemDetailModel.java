package com.appler.riverchiefsystem.model;


import android.util.Log;

import com.appler.riverchiefsystem.base.BaseRetrofit;
import com.appler.riverchiefsystem.model.im.IProblemDetailModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ProblemDetailModel {

    private String TAG = this.getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private IProblemDetailModel iProblemDetailModel;

    public ProblemDetailModel(IProblemDetailModel iProblemDetailModel) {
        this.iProblemDetailModel = iProblemDetailModel;
    }


    public void getProblemDetail(CompositeSubscription compositeSubscription, String flag, String msgId, String messageType) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getItemDetail(flag, msgId, messageType)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                iProblemDetailModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                iProblemDetailModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getProblemDetail: " + e.getMessage());
                                iProblemDetailModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getProblemDetail: " + string);
                                    iProblemDetailModel.getProblemDetail(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iProblemDetailModel.onError();
                                }

                            }
                        })
        );
    }

}

