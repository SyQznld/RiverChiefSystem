package com.appler.riverchiefsystem.model;


import android.util.Log;

import com.appler.riverchiefsystem.base.BaseRetrofit;
import com.appler.riverchiefsystem.model.im.IChooseUsersModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ChooseUsersModel {

    private String TAG = this.getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private IChooseUsersModel iChooseUsersModel;

    public ChooseUsersModel(IChooseUsersModel iChooseUsersModel) {
        this.iChooseUsersModel = iChooseUsersModel;
    }

//    //所有用户
//    public void getAllUsers(CompositeSubscription compositeSubscription, String flag) {
//        compositeSubscription.add(
//                baseRetrofit.getApiService().getAllUsers(flag)
//                        .observeOn(Schedulers.newThread())
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Subscriber<ResponseBody>() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.i(TAG, "onError getAllUsers: " + e.getMessage());
//                                iChooseUsersModel.onError();
//                            }
//
//                            @Override
//                            public void onNext(ResponseBody responseBody) {
//                                try {
//                                    iChooseUsersModel.onComplete();
//                                    String string = responseBody.string();
//                                    Log.i(TAG, "onNext getAllUsers: " + string);
//                                    iChooseUsersModel.getAllUsers(string);
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                    iChooseUsersModel.onError();
//                                }
//
//                            }
//                        })
//        );
//    }

    //返回领导
    public void getSuperiorPerson(CompositeSubscription compositeSubscription, String flag, String sys_staff_id) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getSuperiorPerson(flag, sys_staff_id)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getSuperiorPerson: " + e.getMessage());
                                iChooseUsersModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    iChooseUsersModel.onComplete();
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getSuperiorPerson: " + string);
                                    iChooseUsersModel.getSuperiorPerson(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iChooseUsersModel.onError();
                                }

                            }
                        })
        );
    }


    //预期显示登录角色的下级人员列表
    public void getChargeUsers(CompositeSubscription compositeSubscription, String flag, int userId) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getChargeHezhangList(flag, userId)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getChargeUsers: " + e.getMessage());
                                iChooseUsersModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    iChooseUsersModel.onComplete();
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getChargeUsers: " + string);
                                    iChooseUsersModel.getChargeUsers(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iChooseUsersModel.onError();
                                }

                            }
                        })
        );
    }


    //所有总路长
    public void getAllZongluzhang(CompositeSubscription compositeSubscription, String flag) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getAllZongluzhang(flag)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getAllZongluzhang: " + e.getMessage());
                                iChooseUsersModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    iChooseUsersModel.onComplete();
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getAllZongluzhang: " + string);
                                    iChooseUsersModel.getAllZongluzhang(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iChooseUsersModel.onError();
                                }

                            }
                        })
        );
    }

    //总路长权限 得到一级路长以及二级路长
    public void getYiErJiLuzhang(CompositeSubscription compositeSubscription, String flag, String section_name) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getYiErJiLuzhang(flag, section_name)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getYiErJiLuzhang: " + e.getMessage());
                                iChooseUsersModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    iChooseUsersModel.onComplete();
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getYiErJiLuzhang: " + string);
                                    iChooseUsersModel.getYiErJiLuzhang(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iChooseUsersModel.onError();
                                }

                            }
                        })
        );
    }
}

