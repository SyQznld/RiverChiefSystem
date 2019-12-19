package com.appler.riverchiefsystem.view;


import com.appler.riverchiefsystem.base.BaseView;

public interface TaskListView extends BaseView {

    void getTaskList(String string);

    void getChargePerson(String string);
    void updateTask(String string);

}
