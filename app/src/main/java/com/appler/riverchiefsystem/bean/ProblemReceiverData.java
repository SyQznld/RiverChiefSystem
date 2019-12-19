package com.appler.riverchiefsystem.bean;

import java.util.List;

public class ProblemReceiverData {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 12
         * types : 拥堵
         * department : 124
         * party : 重度
         * sys_staff_id : 124
         * staff_name : 渠化组
         * staff_post : 渠化组
         * staff_tel :
         * sys_gps_id :
         * sys_section_id : 36
         * sys_role_id :
         * staff_sex :
         * staff_photo :
         * staff_password : E10ADC3949BA59ABBE56E057F20F883E
         * staff_hierarchy : 支队科研所
         * stafff_pid :
         * staff_num :
         * staff_idcard :
         * staff_online :
         */

        private String id;
        private String types;
        private String department;
        private String party;
        private String sys_staff_id;
        private String staff_name;
        private String staff_post;
        private String staff_tel;
        private String sys_gps_id;
        private String sys_section_id;
        private String sys_role_id;
        private String staff_sex;
        private String staff_photo;
        private String staff_password;
        private String staff_hierarchy;
        private String stafff_pid;
        private String staff_num;
        private String staff_idcard;
        private String staff_online;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getParty() {
            return party;
        }

        public void setParty(String party) {
            this.party = party;
        }

        public String getSys_staff_id() {
            return sys_staff_id;
        }

        public void setSys_staff_id(String sys_staff_id) {
            this.sys_staff_id = sys_staff_id;
        }

        public String getStaff_name() {
            return staff_name;
        }

        public void setStaff_name(String staff_name) {
            this.staff_name = staff_name;
        }

        public String getStaff_post() {
            return staff_post;
        }

        public void setStaff_post(String staff_post) {
            this.staff_post = staff_post;
        }

        public String getStaff_tel() {
            return staff_tel;
        }

        public void setStaff_tel(String staff_tel) {
            this.staff_tel = staff_tel;
        }

        public String getSys_gps_id() {
            return sys_gps_id;
        }

        public void setSys_gps_id(String sys_gps_id) {
            this.sys_gps_id = sys_gps_id;
        }

        public String getSys_section_id() {
            return sys_section_id;
        }

        public void setSys_section_id(String sys_section_id) {
            this.sys_section_id = sys_section_id;
        }

        public String getSys_role_id() {
            return sys_role_id;
        }

        public void setSys_role_id(String sys_role_id) {
            this.sys_role_id = sys_role_id;
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

        public String getStaff_password() {
            return staff_password;
        }

        public void setStaff_password(String staff_password) {
            this.staff_password = staff_password;
        }

        public String getStaff_hierarchy() {
            return staff_hierarchy;
        }

        public void setStaff_hierarchy(String staff_hierarchy) {
            this.staff_hierarchy = staff_hierarchy;
        }

        public String getStafff_pid() {
            return stafff_pid;
        }

        public void setStafff_pid(String stafff_pid) {
            this.stafff_pid = stafff_pid;
        }

        public String getStaff_num() {
            return staff_num;
        }

        public void setStaff_num(String staff_num) {
            this.staff_num = staff_num;
        }

        public String getStaff_idcard() {
            return staff_idcard;
        }

        public void setStaff_idcard(String staff_idcard) {
            this.staff_idcard = staff_idcard;
        }

        public String getStaff_online() {
            return staff_online;
        }

        public void setStaff_online(String staff_online) {
            this.staff_online = staff_online;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", types='" + types + '\'' +
                    ", department='" + department + '\'' +
                    ", party='" + party + '\'' +
                    ", sys_staff_id='" + sys_staff_id + '\'' +
                    ", staff_name='" + staff_name + '\'' +
                    ", staff_post='" + staff_post + '\'' +
                    ", staff_tel='" + staff_tel + '\'' +
                    ", sys_gps_id='" + sys_gps_id + '\'' +
                    ", sys_section_id='" + sys_section_id + '\'' +
                    ", sys_role_id='" + sys_role_id + '\'' +
                    ", staff_sex='" + staff_sex + '\'' +
                    ", staff_photo='" + staff_photo + '\'' +
                    ", staff_password='" + staff_password + '\'' +
                    ", staff_hierarchy='" + staff_hierarchy + '\'' +
                    ", stafff_pid='" + stafff_pid + '\'' +
                    ", staff_num='" + staff_num + '\'' +
                    ", staff_idcard='" + staff_idcard + '\'' +
                    ", staff_online='" + staff_online + '\'' +
                    '}';
        }
    }
}
