package com.appler.riverchiefsystem.model.im;


import com.appler.riverchiefsystem.base.IModel;

public interface ITaskListModel extends IModel {

    void getTaskList(String string);

    void getChargePerson(String string);


    void updateTask(String string);
}
