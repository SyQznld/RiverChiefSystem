package com.appler.riverchiefsystem.model.im;


import com.appler.riverchiefsystem.base.IModel;

public interface IChooseUsersModel extends IModel {

    void getAllUsers(String string);

    void getChargeUsers(String string);

    void getSuperiorPerson(String string);

    void getAllZongluzhang(String string);

    void getYiErJiLuzhang(String string);

}
