package com.appler.riverchiefsystem.bean;

/**
 * 版本更新 版本
 */

public class VersionData {


    /**
     * version : 1.05
     * updateinfo : 修改巡查区域，优化页面布局，加载图层显示，添加定位导航
     * loadurl : http://62.234.178.242:8083//apk/鹿邑1.05.apk
     */

    private String version;
    private String updateinfo;
    private String loadurl;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdateinfo() {
        return updateinfo;
    }

    public void setUpdateinfo(String updateinfo) {
        this.updateinfo = updateinfo;
    }

    public String getLoadurl() {
        return loadurl;
    }

    public void setLoadurl(String loadurl) {
        this.loadurl = loadurl;
    }
}
