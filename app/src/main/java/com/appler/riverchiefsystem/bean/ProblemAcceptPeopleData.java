package com.appler.riverchiefsystem.bean;

import java.util.List;

public class ProblemAcceptPeopleData {


    /**
     * code : 1
     * msg : success
     * data : [{"staff_name":"八大队","sys_role_id":9,"staff_post":"八大队","stafff_pid":0,"staff_hierarchy":"八大队","staff_tel":"badadui","sys_staff_id":138,"staff_online":"2KH6R19318000291","sys_section_id":34,"staff_password":"E10ADC3949BA59ABBE56E057F20F883E","staff_sex":"男","staff_photo":"img/4.jpg"}]
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

    @Override
    public String toString() {
        return "ProblemAcceptPeopleData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * staff_name : 八大队
         * sys_role_id : 9
         * staff_post : 八大队
         * stafff_pid : 0
         * staff_hierarchy : 八大队
         * staff_tel : badadui
         * sys_staff_id : 138
         * staff_online : 2KH6R19318000291
         * sys_section_id : 34
         * staff_password : E10ADC3949BA59ABBE56E057F20F883E
         * staff_sex : 男
         * staff_photo : img/4.jpg
         */

        private String staff_name;
        private int sys_role_id;
        private String staff_post;
        private int stafff_pid;
        private String staff_hierarchy;
        private String staff_tel;
        private int sys_staff_id;
        private String staff_online;
        private int sys_section_id;
        private String staff_password;
        private String staff_sex;
        private String staff_photo;

        public String getStaff_name() {
            return staff_name;
        }

        public void setStaff_name(String staff_name) {
            this.staff_name = staff_name;
        }

        public int getSys_role_id() {
            return sys_role_id;
        }

        public void setSys_role_id(int sys_role_id) {
            this.sys_role_id = sys_role_id;
        }

        public String getStaff_post() {
            return staff_post;
        }

        public void setStaff_post(String staff_post) {
            this.staff_post = staff_post;
        }

        public int getStafff_pid() {
            return stafff_pid;
        }

        public void setStafff_pid(int stafff_pid) {
            this.stafff_pid = stafff_pid;
        }

        public String getStaff_hierarchy() {
            return staff_hierarchy;
        }

        public void setStaff_hierarchy(String staff_hierarchy) {
            this.staff_hierarchy = staff_hierarchy;
        }

        public String getStaff_tel() {
            return staff_tel;
        }

        public void setStaff_tel(String staff_tel) {
            this.staff_tel = staff_tel;
        }

        public int getSys_staff_id() {
            return sys_staff_id;
        }

        public void setSys_staff_id(int sys_staff_id) {
            this.sys_staff_id = sys_staff_id;
        }

        public String getStaff_online() {
            return staff_online;
        }

        public void setStaff_online(String staff_online) {
            this.staff_online = staff_online;
        }

        public int getSys_section_id() {
            return sys_section_id;
        }

        public void setSys_section_id(int sys_section_id) {
            this.sys_section_id = sys_section_id;
        }

        public String getStaff_password() {
            return staff_password;
        }

        public void setStaff_password(String staff_password) {
            this.staff_password = staff_password;
        }

        public String getStaff_sex() {
            return staff_sex;
        }

        public void setStaff_sex(String staff_sex) {
            this.staff_sex = staff_sex;
        }

        public String getStaff_photo() {
            return staff_photo;
        }

        public void setStaff_photo(String staff_photo) {
            this.staff_photo = staff_photo;
        }
    }
}
