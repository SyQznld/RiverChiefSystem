package com.appler.riverchiefsystem.model;


import android.util.Log;

import com.appler.riverchiefsystem.base.BaseRetrofit;
import com.appler.riverchiefsystem.model.im.IPersonelInfoModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class PersonelInfoModel {

    private String TAG = this.getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private IPersonelInfoModel iPersonelInfoModel;

    public PersonelInfoModel(IPersonelInfoModel iPersonelInfoModel) {
        this.iPersonelInfoModel = iPersonelInfoModel;
    }


    //修改个人信息
    public void editPersonelInfo(CompositeSubscription compositeSubscription, String flag, int userId, String tel) {
        compositeSubscription.add(
                baseRetrofit.getApiService().editPersonelInfo(flag, userId, tel)
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
                                iPersonelInfoModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError editPersonelInfo: " + e.getMessage());
                                iPersonelInfoModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext editPersonelInfo: " + string);
                                    iPersonelInfoModel.editPersonelInfo(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iPersonelInfoModel.onError();
                                }

                            }
                        })
        );
    }

    //修改密码
    public void editPassword(CompositeSubscription compositeSubscription, String flag, int userId, String oldPwd, String newPwd) {
        compositeSubscription.add(
                baseRetrofit.getApiService().editPassword(flag, userId, oldPwd, newPwd)
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
                                iPersonelInfoModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError editPassword: " + e.getMessage());
                                iPersonelInfoModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext editPassword: " + string);
                                    iPersonelInfoModel.editPassword(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iPersonelInfoModel.onError();
                                }

                            }
                        })
        );
    }


    //版本更新
    public void versionUpdate(CompositeSubscription compositeSubscription, String flag) {
        compositeSubscription.add(
                baseRetrofit.getApiService().versionUpdate(flag)
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
                                iPersonelInfoModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError versionUpdate: " + e.getMessage());
                                iPersonelInfoModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext versionUpdate: " + string);
                                    iPersonelInfoModel.versionUpdate(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iPersonelInfoModel.onError();
                                }

                            }
                        })
        );
    }


    //退出登录时调用   保证一个账号仅能登陆一次
    public void exitApp(CompositeSubscription compositeSubscription, String user, String pass) {
        compositeSubscription.add(
                baseRetrofit.getApiService().appLoginOut(user, pass)
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
                                iPersonelInfoModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError exitApp: " + e.getMessage());
                                iPersonelInfoModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext exitApp: " + string);
                                    iPersonelInfoModel.exitApp(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iPersonelInfoModel.onError();
                                }

                            }
                        })
        );
    }


}

