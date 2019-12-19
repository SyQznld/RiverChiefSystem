package com.appler.riverchiefsystem.presenter;


import com.appler.riverchiefsystem.base.BaseMvpPresenter;
import com.appler.riverchiefsystem.model.PatrolDetailModel;
import com.appler.riverchiefsystem.model.im.IPatrolDetailModel;
import com.appler.riverchiefsystem.view.PatrolDetailView;


public class PatrolDetailPresenter extends BaseMvpPresenter {
    private String TAG = this.getClass().getSimpleName();
    private PatrolDetailModel patrolDetailModel;
    private PatrolDetailView patrolDetailView;


    public PatrolDetailPresenter(final PatrolDetailView patrolDetailView) {
        this.patrolDetailView = patrolDetailView;

        patrolDetailModel = new PatrolDetailModel(new IPatrolDetailModel() {

            @Override
            public void getPrtaolProList(String string) {
                patrolDetailView.getPrtaolProList(string);
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


    //巡查关联问题列表
    public void getPatrolProblemList(String flag, String patrol_id,
                                     String zt_type, String wt_type, String keyword) {
        patrolDetailModel.getPatrolProblemList(compositeSubscription, flag, patrol_id, zt_type, wt_type, keyword);
    }
}
