package com.appler.riverchiefsystem.bean;

import java.util.List;

/**
 * 根据权限返回下属人员
 */

public class ChargePersonData {


    /**
     * code : 0
     * msg : success
     * data : [{"id":"131","pid":"127","hezhang":"马楼乡","phone":"13213787850","fuzeren":"卢向升","fuzezhize":"乡负责人","updatetime":"","备注":"","user_id":"76"},{"id":"132","pid":"127","hezhang":"下汤镇","phone":"11111","fuzeren":"张汤","fuzezhize":"镇负责人","updatetime":"","备注":"","user_id":"77"},{"id":"133","pid":"127","hezhang":"尧山镇","phone":"1111","fuzeren":"张瑶","fuzezhize":"镇负责人","updatetime":"","备注":"","user_id":"78"},{"id":"144","pid":"127","hezhang":"张良镇","phone":"13122212231","fuzeren":"冯言富","fuzezhize":"镇负责人","updatetime":"","备注":"","user_id":"89"},{"id":"150","pid":"127","hezhang":"滚子营乡","phone":"13140040001","fuzeren":"冯大道","fuzezhize":"乡负责人","updatetime":"","备注":"","user_id":"95"}]
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
         * id : 131
         * pid : 127
         * hezhang : 马楼乡
         * phone : 13213787850
         * fuzeren : 卢向升
         * fuzezhize : 乡负责人
         * updatetime :
         * 备注 :
         * user_id : 76
         */

        private String id;
        private String pid;
        private String hezhang;
        private String phone;
        private String fuzeren;
        private String fuzezhize;
        private String updatetime;
        private String 备注;
        private String user_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getHezhang() {
            return hezhang;
        }

        public void setHezhang(String hezhang) {
            this.hezhang = hezhang;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getFuzeren() {
            return fuzeren;
        }

        public void setFuzeren(String fuzeren) {
            this.fuzeren = fuzeren;
        }

        public String getFuzezhize() {
            return fuzezhize;
        }

        public void setFuzezhize(String fuzezhize) {
            this.fuzezhize = fuzezhize;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String get备注() {
            return 备注;
        }

        public void set备注(String 备注) {
            this.备注 = 备注;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", pid='" + pid + '\'' +
                    ", hezhang='" + hezhang + '\'' +
                    ", phone='" + phone + '\'' +
                    ", fuzeren='" + fuzeren + '\'' +
                    ", fuzezhize='" + fuzezhize + '\'' +
                    ", updatetime='" + updatetime + '\'' +
                    ", 备注='" + 备注 + '\'' +
                    ", user_id='" + user_id + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ChargePersonData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
