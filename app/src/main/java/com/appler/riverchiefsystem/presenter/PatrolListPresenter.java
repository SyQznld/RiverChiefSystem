package com.appler.riverchiefsystem.presenter;


import com.appler.riverchiefsystem.base.BaseMvpPresenter;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.model.PatrolListModel;
import com.appler.riverchiefsystem.model.im.IPatrolListModel;
import com.appler.riverchiefsystem.view.PatrolListView;


public class PatrolListPresenter extends BaseMvpPresenter {
    private String TAG = this.getClass().getSimpleName();
    private PatrolListModel patrolListModel;
    private PatrolListView patrolListView;


    public PatrolListPresenter(final PatrolListView patrolListView) {
        this.patrolListView = patrolListView;

        patrolListModel = new PatrolListModel(new IPatrolListModel() {
            @Override
            public void getPatrolList(String string) {
                patrolListView.getPatrolList(string);
            }

            @Override
            public void getChargePerson(String string) {
                patrolListView.getChargePerson(string);
            }

            @Override
            public void onStart() {
                patrolListView.setState(Global.LOADING_DOING);
            }

            @Override
            public void onComplete() {
                patrolListView.setState(Global.LOADING_SUCCESS);
            }

            @Override
            public void onError() {
                patrolListView.setState(Global.LOADING_FAIL);
            }
        });
    }


    public void getPatrolList(String flag, int SysStaffId, String keyword) {
        patrolListModel.getPatrolList(compositeSubscription, flag, SysStaffId, keyword);
    }


    public void getChargePerson(String flag, int userId) {
        patrolListModel.getChargePerson(compositeSubscription, flag, userId);
    }
}
