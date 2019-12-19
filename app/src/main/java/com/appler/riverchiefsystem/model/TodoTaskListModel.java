package com.appler.riverchiefsystem.model;


import android.util.Log;

import com.appler.riverchiefsystem.base.BaseRetrofit;
import com.appler.riverchiefsystem.model.im.ITodoTaskListModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class TodoTaskListModel {

    private String TAG = this.getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private ITodoTaskListModel iTodoTaskListModel;

    public TodoTaskListModel(ITodoTaskListModel iTodoTaskListModel) {
        this.iTodoTaskListModel = iTodoTaskListModel;
    }


    //首页 待办任务 列表
    public void getHomeTodoTaskList(CompositeSubscription compositeSubscription, String flag,int acceptStaffId) {
        Log.i(TAG, "getHomeTodoTaskList userId: " + acceptStaffId);
        compositeSubscription.add(
                baseRetrofit.getApiService().getHomeTodoList(flag,acceptStaffId)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getHomeTodoTaskList: " + e.getMessage());
                                iTodoTaskListModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getHomeTodoTaskList: " + string);
                                    iTodoTaskListModel.getHomeTodoTaskList(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iTodoTaskListModel.onError();
                                }

                            }
                        })
        );
    }


}

