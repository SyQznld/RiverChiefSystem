package com.appler.riverchiefsystem.model.im;


import com.appler.riverchiefsystem.base.IModel;

public interface IProblemListModel extends IModel {

    void getProblemList(String string);

    void getPatrolProblemList(String string);
}
