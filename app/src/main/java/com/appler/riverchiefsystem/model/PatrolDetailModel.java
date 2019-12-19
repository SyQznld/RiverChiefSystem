package com.appler.riverchiefsystem.model;


import android.util.Log;

import com.appler.riverchiefsystem.base.BaseRetrofit;
import com.appler.riverchiefsystem.model.im.IPatrolDetailModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class PatrolDetailModel {

    private String TAG = this.getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private IPatrolDetailModel iPatrolDetailModel;

    public PatrolDetailModel(IPatrolDetailModel iPatrolDetailModel) {
        this.iPatrolDetailModel = iPatrolDetailModel;
    }


    //巡查记录关联问题
    public void getPatrolProblemList(CompositeSubscription compositeSubscription, String flag, String patrol_id,
                                     String zt_type, String wt_type, String keyword) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getPatrolProblemList(flag, patrol_id,zt_type,wt_type,keyword)
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
                                iPatrolDetailModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getPatrolProblemList: " + e.getMessage());
                                iPatrolDetailModel.getPrtaolProList(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getPatrolProblemList: " + string);
                                    iPatrolDetailModel.getPrtaolProList(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iPatrolDetailModel.onError();
                                }

                            }
                        })
        );
    }

}

