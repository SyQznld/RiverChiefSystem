package com.appler.riverchiefsystem.presenter;


import com.appler.riverchiefsystem.base.BaseMvpPresenter;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.model.ProblemDetailModel;
import com.appler.riverchiefsystem.model.im.IProblemDetailModel;
import com.appler.riverchiefsystem.view.ProblemDetailView;


public class ProblemDetailPresenter extends BaseMvpPresenter {
    private String TAG = this.getClass().getSimpleName();
    private ProblemDetailModel problemDetailModel;
    private ProblemDetailView problemDetailView;


    public ProblemDetailPresenter(final ProblemDetailView problemDetailView) {
        this.problemDetailView = problemDetailView;

        problemDetailModel = new ProblemDetailModel(new IProblemDetailModel() {

            @Override
            public void getProblemDetail(String string) {
                problemDetailView.getProblemDetail(string);
            }


            @Override
            public void onStart() {
problemDetailView.setState(Global.LOADING_DOING);
            }

            @Override
            public void onComplete() {
                problemDetailView.setState(Global.LOADING_SUCCESS);
            }

            @Override
            public void onError() {
                problemDetailView.setState(Global.LOADING_FAIL);
            }
        });
    }


    public void getProblemDetail( String flag,String msgId, String messageType) {
        problemDetailModel.getProblemDetail(compositeSubscription, flag, msgId, messageType);
    }

}
