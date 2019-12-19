package com.appler.riverchiefsystem.presenter;


import com.appler.riverchiefsystem.base.BaseMvpPresenter;
import com.appler.riverchiefsystem.model.ZdkysModel;
import com.appler.riverchiefsystem.model.im.IZdkysModel;
import com.appler.riverchiefsystem.view.ZdkysView;


public class ZdkysPresenter extends BaseMvpPresenter {
    private String TAG = this.getClass().getSimpleName();
    private ZdkysModel zdkysProModel;
    private ZdkysView zdkysView;


    public ZdkysPresenter(final ZdkysView zdkysView) {
        this.zdkysView = zdkysView;

        zdkysProModel = new ZdkysModel(new IZdkysModel() {
            @Override
            public void getZdkysProList(String string) {
                zdkysView.getZdkysProList(string);
            }

            @Override
            public void zdkysProToTask(String string) {
                zdkysView.zdkysProToTask(string);
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


    public void getZdkysProList(String flag, String userId,
                                String problem_task, String problem_descibe) {
        zdkysProModel.getZdkysProList(compositeSubscription, flag, userId,
                problem_task, problem_descibe);
    }


    //支队科研所添加任务
    public void zdkysProToTask(String flag,
                               String sys_problem_id,
                               String launch_staff_id,
                               String task_createtime,
                               String task_expecttime,
                               String task_detail) {
        zdkysProModel.zdkysProToTask(
                compositeSubscription,
                flag,
                sys_problem_id,
                launch_staff_id,
                task_createtime,
                task_expecttime,
                task_detail);
    }


}
