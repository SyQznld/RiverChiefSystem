package com.appler.riverchiefsystem.bean;

import java.util.List;

/**
 * 水雨情
 * 水情
 * */
public class SYQ_RiverData {

    /**
     * code : 0
     * msg : success
     * data : [{"id":"1","站名":"中汤","所在河道":"沙河","水位":"208.350","流量":"1.380","水势":"涨","uploadtime":"2018/08/21 19:51:27","时间":"2019/6/27 8:W00:W00","ogr_geometry":"POINT (112.575686332 33.7401762220001)","警戒水位":"84.W00"},{"id":"2","站名":"下孤山","所在河道":"荡泽河","水位":"207.030","流量":"0.516","水势":"涨","uploadtime":"2018/08/21 19:51:27","时间":"2019/6/27 8:W00:W00","ogr_geometry":"POINT (112.731126278 33.856389295)","警戒水位":""},{"id":"4","站名":"汝州","所在河道":"北汝河","水位":"193.470","流量":"5.410","水势":"涨","uploadtime":"2018/08/21 19:51:27","时间":"2019/6/27 8:W00:W00","ogr_geometry":"POINT (112.831746763411 34.1424916145916)","警戒水位":"193.80"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * 站名 : 中汤
         * 所在河道 : 沙河
         * 水位 : 208.350
         * 流量 : 1.380
         * 水势 : 涨
         * uploadtime : 2018/08/21 19:51:27
         * 时间 : 2019/6/27 8:W00:W00
         * ogr_geometry : POINT (112.575686332 33.7401762220001)
         * 警戒水位 : 84.W00
         */

        private String id;
        private String 站名;
        private String 所在河道;
        private String 水位;
        private String 流量;
        private String 水势;
        private String uploadtime;
        private String 时间;
        private String ogr_geometry;
        private String 警戒水位;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String get站名() {
            return 站名;
        }

        public void set站名(String 站名) {
            this.站名 = 站名;
        }

        public String get所在河道() {
            return 所在河道;
        }

        public void set所在河道(String 所在河道) {
            this.所在河道 = 所在河道;
        }

        public String get水位() {
            return 水位;
        }

        public void set水位(String 水位) {
            this.水位 = 水位;
        }

        public String get流量() {
            return 流量;
        }

        public void set流量(String 流量) {
            this.流量 = 流量;
        }

        public String get水势() {
            return 水势;
        }

        public void set水势(String 水势) {
            this.水势 = 水势;
        }

        public String getUploadtime() {
            return uploadtime;
        }

        public void setUploadtime(String uploadtime) {
            this.uploadtime = uploadtime;
        }

        public String get时间() {
            return 时间;
        }

        public void set时间(String 时间) {
            this.时间 = 时间;
        }

        public String getOgr_geometry() {
            return ogr_geometry;
        }

        public void setOgr_geometry(String ogr_geometry) {
            this.ogr_geometry = ogr_geometry;
        }

        public String get警戒水位() {
            return 警戒水位;
        }

        public void set警戒水位(String 警戒水位) {
            this.警戒水位 = 警戒水位;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", 站名='" + 站名 + '\'' +
                    ", 所在河道='" + 所在河道 + '\'' +
                    ", 水位='" + 水位 + '\'' +
                    ", 流量='" + 流量 + '\'' +
                    ", 水势='" + 水势 + '\'' +
                    ", uploadtime='" + uploadtime + '\'' +
                    ", 时间='" + 时间 + '\'' +
                    ", ogr_geometry='" + ogr_geometry + '\'' +
                    ", 警戒水位='" + 警戒水位 + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SYQ_RiverData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
