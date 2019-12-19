package com.appler.riverchiefsystem.presenter;


import com.appler.riverchiefsystem.base.BaseMvpPresenter;
import com.appler.riverchiefsystem.model.SuperviseDetailModel;
import com.appler.riverchiefsystem.model.im.ISuperviseDetailModel;
import com.appler.riverchiefsystem.view.SuperviseDetailView;


public class SuperviseDetailPresenter extends BaseMvpPresenter {
    private String TAG = this.getClass().getSimpleName();
    private SuperviseDetailModel superviseDetailModel;
    private SuperviseDetailView superviseDetailView;


    public SuperviseDetailPresenter(final SuperviseDetailView superviseDetailView) {
        this.superviseDetailView = superviseDetailView;

        superviseDetailModel = new SuperviseDetailModel(new ISuperviseDetailModel() {
            @Override
            public void getSuperviseDetail(String string) {
                superviseDetailView.getSuperviseDetail(string);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError() {

            }
        });
    }


    public void getSuperviseDetail(String flag, String changeId) {
        superviseDetailModel.getSuperviseDetail(compositeSubscription, flag, changeId);
    }


}
