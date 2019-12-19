package com.appler.riverchiefsystem.bean;


import java.util.List;

/**
 * 文件上传 成功后
 * */
public class UploadFileData {


    /**
     * code : 1
     * msg : 上传成功
     * data : ["http://114.116.52.4:8080/photo/156878998614820190918145921.jpg","http://114.116.52.4:8080/photo/156878998615120190918145933.jpg"]
     */

    private int code;
    private String msg;
    private List<String> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
