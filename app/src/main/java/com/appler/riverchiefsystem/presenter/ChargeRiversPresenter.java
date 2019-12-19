package com.appler.riverchiefsystem.presenter;


import com.appler.riverchiefsystem.base.BaseMvpPresenter;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.model.ChargeRiversModel;
import com.appler.riverchiefsystem.model.im.IChargeRiversModel;
import com.appler.riverchiefsystem.view.ChargeRiversView;


public class ChargeRiversPresenter extends BaseMvpPresenter {
    private String TAG = this.getClass().getSimpleName();
    private ChargeRiversModel chargeRiversModel;
    private ChargeRiversView chargeRiversView;


    public ChargeRiversPresenter(final ChargeRiversView chargeRiversView) {
        this.chargeRiversView = chargeRiversView;

        chargeRiversModel = new ChargeRiversModel(new IChargeRiversModel() {
            @Override
            public void getChargeRivers(String string) {
                chargeRiversView.getChargeRivers(string);
            }

            @Override
            public void getAllUsers(String string) {
                chargeRiversView.getAllUsers(string);
            }

            @Override
            public void onStart() {
                chargeRiversView.setState(Global.LOADING_DOING);
            }

            @Override
            public void onComplete() {
                chargeRiversView.setState(Global.LOADING_SUCCESS);
            }

            @Override
            public void onError() {
                chargeRiversView.setState(Global.LOADING_FAIL);
            }
        });
    }


    //负责河流
    public void getChargeRivers(String flag,int staffId) {
        chargeRiversModel.getChargRivers(compositeSubscription, flag,staffId);
    }



    //所有用户
    public void getAllUsers(String flag,String keyword) {
        chargeRiversModel.getAllUsers(compositeSubscription, flag,keyword);
    }



}
