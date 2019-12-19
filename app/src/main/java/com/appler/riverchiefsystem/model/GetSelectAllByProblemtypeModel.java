package com.appler.riverchiefsystem.model;

import android.util.Log;

import com.appler.riverchiefsystem.base.BaseRetrofit;
import com.appler.riverchiefsystem.model.im.IGetSelectAllByProblemtype;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class GetSelectAllByProblemtypeModel {

//获取问题类型  ,对应问题类型指向人


    private String TAG = this.getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private IGetSelectAllByProblemtype iGetSelectAllByProblemtype;

    public GetSelectAllByProblemtypeModel(IGetSelectAllByProblemtype iGetSelectAllByProblemtype) {
        this.iGetSelectAllByProblemtype = iGetSelectAllByProblemtype;
    }


    public void getSelectAllByProblemtype(CompositeSubscription compositeSubscription) {
        compositeSubscription.add(
                baseRetrofit.getApiService().selectAllByProblemtype()
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getSelectAllByProblemtype: " + e.getMessage());
                                iGetSelectAllByProblemtype.getSelectAllByProblemtypeFailure(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getSelectAllByProblemtype: " + string);
                                    iGetSelectAllByProblemtype.getSelectAllByProblemtypeSuccess(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
        );
    }


    public void selectdefault(CompositeSubscription compositeSubscription, String probleDetail) {
        compositeSubscription.add(
                baseRetrofit.getApiService().selectdefault(probleDetail)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getSelectAllByProblemtype: " + e.getMessage());
                                iGetSelectAllByProblemtype.getSelectdefaultFailure(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getSelectAllByProblemtype: " + string);
                                    iGetSelectAllByProblemtype.getSelectdefaultSuccess(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
        );
    }
}
