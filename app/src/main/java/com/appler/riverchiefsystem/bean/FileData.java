package com.appler.riverchiefsystem.bean;


public class FileData {

    private String filename;
    private String filePath;
    private String chooseTime;
    private String fileType;

    public FileData() {
    }

    public FileData(String filename, String filePath, String chooseTime, String fileType) {
        this.filename = filename;
        this.filePath = filePath;
        this.chooseTime = chooseTime;
        this.fileType = fileType;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getChooseTime() {
        return chooseTime;
    }

    public void setChooseTime(String chooseTime) {
        this.chooseTime = chooseTime;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return "FileData{" +
                "filename='" + filename + '\'' +
                ", filePath='" + filePath + '\'' +
                ", chooseTime='" + chooseTime + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
