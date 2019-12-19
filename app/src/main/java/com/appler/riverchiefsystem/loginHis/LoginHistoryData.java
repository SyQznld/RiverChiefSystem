package com.appler.riverchiefsystem.loginHis;

/**
 * 保存登录历史 用户名密码
 */

public class LoginHistoryData {

    private String userName;
    private String pass;

    public LoginHistoryData() {
    }

    public LoginHistoryData(String userName, String pass) {
        this.userName = userName;
        this.pass = pass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "LoginHistoryData{" +
                "userName='" + userName + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
