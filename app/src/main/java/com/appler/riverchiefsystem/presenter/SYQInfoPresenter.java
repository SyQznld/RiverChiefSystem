package com.appler.riverchiefsystem.presenter;


import com.appler.riverchiefsystem.base.BaseMvpPresenter;
import com.appler.riverchiefsystem.model.SYQInfoModel;
import com.appler.riverchiefsystem.model.im.ISYQInfoModel;
import com.appler.riverchiefsystem.view.SYQInfoView;


public class SYQInfoPresenter extends BaseMvpPresenter {
    private String TAG = this.getClass().getSimpleName();
    private SYQInfoModel SYQInfoModel;
    private SYQInfoView SYQInfoView;


    public SYQInfoPresenter(final SYQInfoView SYQInfoView) {
        this.SYQInfoView = SYQInfoView;
        SYQInfoModel = new SYQInfoModel(new ISYQInfoModel() {
            @Override
            public void getSKSWInfo(String string) {
                SYQInfoView.getSKSWInfo(string);
            }

            @Override
            public void getRainInfo(String string) {
                SYQInfoView.getRainInfo(string);
            }

            @Override
            public void getRiversInfo(String string) {
                SYQInfoView.getRiversInfo(string);
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
        }) ;

    }


    //水库水位
    public void getSKSWInfo(String flag,String keyword) {
        SYQInfoModel.getYSQ_SKSWInfo(compositeSubscription, flag,keyword);
    }


    //雨情
    public void getRainInfo(String flag,String keyword) {
        SYQInfoModel.getYSQ_RainInfo(compositeSubscription, flag,keyword);
    }


    //水情
    public void getRiversInfo(String flag,String keyword) {
        SYQInfoModel.getYSQ_RiversInfo(compositeSubscription, flag,keyword);
    }


}
