package com.appler.riverchiefsystem.model;


import com.appler.riverchiefsystem.base.BaseRetrofit;
import com.appler.riverchiefsystem.model.im.ISYQInfoModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class SYQInfoModel {

    private String TAG = this.getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private ISYQInfoModel ISYQInfoModel;

    public SYQInfoModel(ISYQInfoModel ISYQInfoModel) {
        this.ISYQInfoModel = ISYQInfoModel;
    }


    //水库水位
    public void getYSQ_SKSWInfo(CompositeSubscription compositeSubscription, String flag,String keyword) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getYSQ_SKSW(flag,keyword)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                ISYQInfoModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    ISYQInfoModel.getSKSWInfo(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    ISYQInfoModel.onError();
                                }

                            }
                        })
        );
    }


    //水情
    public void getYSQ_RiversInfo(CompositeSubscription compositeSubscription, String flag,String keyword) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getYSQ_River(flag,keyword)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                ISYQInfoModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    ISYQInfoModel.getRiversInfo(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    ISYQInfoModel.onError();
                                }

                            }
                        })
        );
    }


    //雨情
    public void getYSQ_RainInfo(CompositeSubscription compositeSubscription, String flag,String keyword) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getYSQ_Rain(flag,keyword)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                ISYQInfoModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    ISYQInfoModel.getRainInfo(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    ISYQInfoModel.onError();
                                }

                            }
                        })
        );
    }




}

