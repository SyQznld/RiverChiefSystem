package com.appler.riverchiefsystem.presenter;


import com.appler.riverchiefsystem.base.BaseMvpPresenter;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.model.TodoTaskListModel;
import com.appler.riverchiefsystem.model.im.ITodoTaskListModel;
import com.appler.riverchiefsystem.view.TodoTaskListView;


public class TodoTaskListPresenter extends BaseMvpPresenter {
    private String TAG = this.getClass().getSimpleName();
    private TodoTaskListModel todoTaskListModel;
    private TodoTaskListView todoTaskListView;


    public TodoTaskListPresenter(final TodoTaskListView todoTaskListView) {
        this.todoTaskListView = todoTaskListView;

        todoTaskListModel = new TodoTaskListModel(new ITodoTaskListModel() {
            @Override
            public void getHomeTodoTaskList(String string) {
                todoTaskListView.getHomeTodoTaskList(string);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete() {
            }

            @Override
            public void onError() {
                todoTaskListView.setState(Global.LOADING_FAIL);
            }
        });
    }


    public void getHomeTodoTaskList(String flag,int acceptStaffId) {
        todoTaskListModel.getHomeTodoTaskList(compositeSubscription, flag,acceptStaffId);
    }


}
