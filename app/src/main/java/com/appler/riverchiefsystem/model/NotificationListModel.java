package com.appler.riverchiefsystem.model;


import android.util.Log;

import com.appler.riverchiefsystem.base.BaseRetrofit;
import com.appler.riverchiefsystem.model.im.INotificationListModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class NotificationListModel {

    private String TAG = this.getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private INotificationListModel iNotificationListModel;

    public NotificationListModel(INotificationListModel iNotificationListModel) {
        this.iNotificationListModel = iNotificationListModel;
    }

    //通知公告列表
    public void getPCNoticeList(CompositeSubscription compositeSubscription, String flag, String keyword) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getPCNoticeList(flag, keyword)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                iNotificationListModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                iNotificationListModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getPCNoticeList: " + e.getMessage());
                                iNotificationListModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getPCNoticeList: " + string);
                                    iNotificationListModel.getPCNotificationList(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iNotificationListModel.onError();
                                }
                            }
                        })
        );
    }


    //消息列表
    public void getMessageList(CompositeSubscription compositeSubscription, String flag, int userId) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getMessageList(flag, userId)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                iNotificationListModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                iNotificationListModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getMessageList: " + e.getMessage());
                                iNotificationListModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getMessageList: " + string);
                                    iNotificationListModel.getMessageList(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iNotificationListModel.onError();
                                }

                            }
                        })
        );
    }


    //删除单条消息
    public void deleteSingleMessage(CompositeSubscription compositeSubscription, String flag, String msgId) {
        compositeSubscription.add(
                baseRetrofit.getApiService().deleteSingleMessage(flag, msgId)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                iNotificationListModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                iNotificationListModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError deleteSingleMessage: " + e.getMessage());
                                iNotificationListModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext deleteSingleMessage: " + string);
                                    iNotificationListModel.deleteMessageItem(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iNotificationListModel.onError();
                                }

                            }
                        })
        );
    }


    //更改消息已读未读状态
    public void updateMessageState(CompositeSubscription compositeSubscription, String flag, String msgId) {
        compositeSubscription.add(
                baseRetrofit.getApiService().updateMessageState(flag, msgId)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                iNotificationListModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                iNotificationListModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError updateMessageState: " + e.getMessage());
                                iNotificationListModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext updateMessageState: " + string);
                                    iNotificationListModel.updateMessageState(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iNotificationListModel.onError();
                                }

                            }
                        })
        );
    }


}

