package com.appler.riverchiefsystem.presenter;


import com.appler.riverchiefsystem.base.BaseMvpPresenter;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.model.TaskDetailModel;
import com.appler.riverchiefsystem.model.im.ITaskDetailModel;
import com.appler.riverchiefsystem.view.TaskDetailView;


public class TaskDetailPresenter extends BaseMvpPresenter {
    private String TAG = this.getClass().getSimpleName();
    private TaskDetailModel taskDetailModel;
    private TaskDetailView taskDetailView;


    public TaskDetailPresenter(final TaskDetailView taskDetailView) {
        this.taskDetailView = taskDetailView;

        taskDetailModel = new TaskDetailModel(new ITaskDetailModel() {
            @Override
            public void editTaskDetail(String string) {
                taskDetailView.editTaskDetail(string);
            }

            @Override
            public void getTaskDetail(String string) {
                taskDetailView.getTaskDetail(string);
            }

            @Override
            public void onStart() {
                taskDetailView.setState(Global.LOADING_DOING);

            }

            @Override
            public void onComplete() {
                taskDetailView.setState(Global.LOADING_SUCCESS);
            }

            @Override
            public void onError() {
                taskDetailView.setState(Global.LOADING_FAIL);
            }
        });
    }


    public void getTaskDetail( String flag,String msgId, String messageType) {
        taskDetailModel.getTaskDetail(compositeSubscription, flag,msgId, messageType);
    }


}
