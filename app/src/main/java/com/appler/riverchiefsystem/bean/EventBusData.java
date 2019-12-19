package com.appler.riverchiefsystem.bean;

/**
 * 传递数据
 */

public class EventBusData {

    private String message;

    private String gpsTrack;
    private String heduanName;
    private String riverName;
    private String type;

    private String problemId;

    public EventBusData() {
    }

    public EventBusData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGpsTrack() {
        return gpsTrack;
    }

    public void setGpsTrack(String gpsTrack) {
        this.gpsTrack = gpsTrack;
    }

    public String getHeduanName() {
        return heduanName;
    }

    public void setHeduanName(String heduanName) {
        this.heduanName = heduanName;
    }

    public String getRiverName() {
        return riverName;
    }

    public void setRiverName(String riverName) {
        this.riverName = riverName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }
}
