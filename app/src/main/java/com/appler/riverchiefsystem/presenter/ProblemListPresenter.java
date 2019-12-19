package com.appler.riverchiefsystem.presenter;


import com.appler.riverchiefsystem.base.BaseMvpPresenter;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.model.ProblemListModel;
import com.appler.riverchiefsystem.model.im.IProblemListModel;
import com.appler.riverchiefsystem.view.ProblemListView;


public class ProblemListPresenter extends BaseMvpPresenter {
    private String TAG = this.getClass().getSimpleName();
    private ProblemListModel problemListModel;
    private ProblemListView problemListView;


    public ProblemListPresenter(final ProblemListView problemListView) {
        this.problemListView = problemListView;

        problemListModel = new ProblemListModel(new IProblemListModel() {
            @Override
            public void getProblemList(String string) {
                problemListView.getProblemList(string);
            }

            @Override
            public void getPatrolProblemList(String string) {
                problemListView.getPatrolProblemList(string);
            }

            @Override
            public void onStart() {
                problemListView.setState(Global.LOADING_DOING);
            }

            @Override
            public void onComplete() {
                problemListView.setState(Global.LOADING_SUCCESS);
            }

            @Override
            public void onError() {
                problemListView.setState(Global.LOADING_FAIL);
            }
        });
    }


    //问题列表
    public void getProblemList(String flag, int sysStaffId, String state, String wt_type,String fzren,String keyword) {
        problemListModel.getProblemList(compositeSubscription, flag,sysStaffId, state,wt_type,fzren,keyword);
    }


    //巡查关联问题列表
    public void getPatrolProblemList(String flag, String patrol_id, String problem_grade, String problem_resove, String problem_descibe) {
        problemListModel.getPatrolProblemList(compositeSubscription, flag, patrol_id, problem_grade, problem_resove, problem_descibe);
    }
}
