package com.appler.riverchiefsystem.model;

import android.util.Log;

import com.appler.riverchiefsystem.base.BaseRetrofit;
import com.appler.riverchiefsystem.view.AddPatrolView;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class AddPatrolModel {

    private String TAG = this.getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private AddPatrolView addPatrolView;

    public AddPatrolModel(AddPatrolView addPatrolView) {
        this.addPatrolView = addPatrolView;
    }

    public void updatePatrolrecord(CompositeSubscription compositeSubscription,
                                   int sysStaffId, String patrolRecordBegintime, String patrolRecordEndtime,
                                   String patrolRecordGps, String patrolRecordDetail, int sysRoadId,
                                   int sysPatrolRecordId, String patrolRecordPicture, String patrolRecordVideo) {
        compositeSubscription.add(
                baseRetrofit.getApiService().updatePatrolrecord(sysStaffId, patrolRecordBegintime, patrolRecordEndtime,
                        patrolRecordGps, patrolRecordDetail, sysRoadId, sysPatrolRecordId, patrolRecordPicture, patrolRecordVideo)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError updatePatrolrecord: " + e.getMessage());
                                addPatrolView.AddPatrolFailure(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext: updatePatrolrecord" + string);
                                    addPatrolView.AddPatrolSuccess(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })
        );
    }
}
