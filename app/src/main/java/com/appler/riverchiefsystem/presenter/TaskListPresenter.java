package com.appler.riverchiefsystem.presenter;


import com.appler.riverchiefsystem.base.BaseMvpPresenter;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.model.TaskListModel;
import com.appler.riverchiefsystem.model.im.ITaskListModel;
import com.appler.riverchiefsystem.view.TaskListView;


public class TaskListPresenter extends BaseMvpPresenter {
    private String TAG = this.getClass().getSimpleName();
    private TaskListModel myTaskListModel;
    private TaskListView myTaskListView;


    public TaskListPresenter(final TaskListView myTaskListView) {
        this.myTaskListView = myTaskListView;

        myTaskListModel = new TaskListModel(new ITaskListModel() {
            @Override
            public void getTaskList(String string) {
                myTaskListView.getTaskList(string);
            }

            @Override
            public void getChargePerson(String string) {
                myTaskListView.getChargePerson(string);
            }

            @Override
            public void updateTask(String string) {
                myTaskListView.updateTask(string);
            }

            @Override
            public void onStart() {
                myTaskListView.setState(Global.LOADING_DOING);
            }

            @Override
            public void onComplete() {
                myTaskListView.setState(Global.LOADING_SUCCESS);
            }

            @Override
            public void onError() {
                myTaskListView.setState(Global.LOADING_FAIL);
            }
        });
    }


    public void getTaskList( String flag,int userId, String type,String fzren,String keyword) {
        myTaskListModel.getTaskList(compositeSubscription, flag,userId,type,fzren,keyword);
    }

    public void getChargePerson(String flag, int userid) {
        myTaskListModel.getChargePerson(compositeSubscription, flag, userid);
    }


}
