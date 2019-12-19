package com.appler.riverchiefsystem.model;


import android.util.Log;

import com.appler.riverchiefsystem.base.BaseRetrofit;
import com.appler.riverchiefsystem.model.im.IProblemListModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ProblemListModel {

    private String TAG = this.getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private IProblemListModel iProblemListModel;

    public ProblemListModel(IProblemListModel iProblemListModel) {
        this.iProblemListModel = iProblemListModel;
    }


    //当前用户相关问题
    public void getProblemList(CompositeSubscription compositeSubscription, String flag,int sysStaffId, String state, String wt_type,String fzren,String keyword) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getProblemList(flag,sysStaffId, state,wt_type,fzren,keyword)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                iProblemListModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                iProblemListModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getProblemList: " + e.getMessage());
                                iProblemListModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getProblemList: " + string);
                                    iProblemListModel.getProblemList(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iProblemListModel.onError();
                                }

                            }
                        })
        );
    }


    //巡查记录关联问题
    public void getPatrolProblemList(CompositeSubscription compositeSubscription, String flag, String patrol_id,
                                     String problem_grade, String problem_resove, String problem_descibe) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getPatrolProblemList(flag, patrol_id,
                        problem_grade, problem_resove, problem_descibe)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                iProblemListModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                iProblemListModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getPatrolProblemList: " + e.getMessage());
                                iProblemListModel.getPatrolProblemList(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getPatrolProblemList: " + string);
                                    iProblemListModel.getPatrolProblemList(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iProblemListModel.onError();
                                }

                            }
                        })
        );
    }






}

