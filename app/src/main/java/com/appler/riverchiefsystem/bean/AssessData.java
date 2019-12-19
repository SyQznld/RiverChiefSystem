package com.appler.riverchiefsystem.bean;

public class AssessData {
    private String userName;
    private String month;
    private String score;

    public AssessData() {
    }

    public AssessData(String userName, String month, String score) {
        this.userName = userName;
        this.month = month;
        this.score = score;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }


    @Override
    public String toString() {
        return "AssessData{" +
                "userName='" + userName + '\'' +
                ", month='" + month + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
