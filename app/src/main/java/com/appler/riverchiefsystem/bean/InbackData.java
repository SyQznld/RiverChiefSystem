package com.appler.riverchiefsystem.bean;

import java.util.List;

/**
 * 签到签退
 */

public class InbackData {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "InbackData{" +
                "data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * sys_inback_id : 7
         * sys_staff_id : 7
         * inback_intime : 2018-09-19 08:W00:W00
         * inback_backtime : 2018-09-22 19:W00:W00
         * in_gps_x : 113.7144910000
         * in_gps_y : 34.7481880000
         * in_gps_z :
         * back_gps_x : 113.7144910000
         * back_gps_y : 34.7441880000
         * back_gps_z :
         * in_place : 农业路
         * back_place : 黄河路
         * sys_road_id : 1
         * staff_name : 张福志
         * road_name : 金水路地面道路
         * staff_hierarchy : 二级路长
         */

        private String sys_inback_id;
        private String sys_staff_id;
        private String inback_intime;
        private String inback_backtime;
        private String in_gps_x;
        private String in_gps_y;
        private String in_gps_z;
        private String back_gps_x;
        private String back_gps_y;
        private String back_gps_z;
        private String in_place;
        private String back_place;
        private String sys_road_id;
        private String staff_name;
        private String road_name;
        private String staff_hierarchy;

        public String getSys_inback_id() {
            return sys_inback_id;
        }

        public void setSys_inback_id(String sys_inback_id) {
            this.sys_inback_id = sys_inback_id;
        }

        public String getSys_staff_id() {
            return sys_staff_id;
        }

        public void setSys_staff_id(String sys_staff_id) {
            this.sys_staff_id = sys_staff_id;
        }

        public String getInback_intime() {
            return inback_intime;
        }

        public void setInback_intime(String inback_intime) {
            this.inback_intime = inback_intime;
        }

        public String getInback_backtime() {
            return inback_backtime;
        }

        public void setInback_backtime(String inback_backtime) {
            this.inback_backtime = inback_backtime;
        }

        public String getIn_gps_x() {
            return in_gps_x;
        }

        public void setIn_gps_x(String in_gps_x) {
            this.in_gps_x = in_gps_x;
        }

        public String getIn_gps_y() {
            return in_gps_y;
        }

        public void setIn_gps_y(String in_gps_y) {
            this.in_gps_y = in_gps_y;
        }

        public String getIn_gps_z() {
            return in_gps_z;
        }

        public void setIn_gps_z(String in_gps_z) {
            this.in_gps_z = in_gps_z;
        }

        public String getBack_gps_x() {
            return back_gps_x;
        }

        public void setBack_gps_x(String back_gps_x) {
            this.back_gps_x = back_gps_x;
        }

        public String getBack_gps_y() {
            return back_gps_y;
        }

        public void setBack_gps_y(String back_gps_y) {
            this.back_gps_y = back_gps_y;
        }

        public String getBack_gps_z() {
            return back_gps_z;
        }

        public void setBack_gps_z(String back_gps_z) {
            this.back_gps_z = back_gps_z;
        }

        public String getIn_place() {
            return in_place;
        }

        public void setIn_place(String in_place) {
            this.in_place = in_place;
        }

        public String getBack_place() {
            return back_place;
        }

        public void setBack_place(String back_place) {
            this.back_place = back_place;
        }

        public String getSys_road_id() {
            return sys_road_id;
        }

        public void setSys_road_id(String sys_road_id) {
            this.sys_road_id = sys_road_id;
        }

        public String getStaff_name() {
            return staff_name;
        }

        public void setStaff_name(String staff_name) {
            this.staff_name = staff_name;
        }

        public String getRoad_name() {
            return road_name;
        }

        public void setRoad_name(String road_name) {
            this.road_name = road_name;
        }

        public String getStaff_hierarchy() {
            return staff_hierarchy;
        }

        public void setStaff_hierarchy(String staff_hierarchy) {
            this.staff_hierarchy = staff_hierarchy;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "sys_inback_id='" + sys_inback_id + '\'' +
                    ", sys_staff_id='" + sys_staff_id + '\'' +
                    ", inback_intime='" + inback_intime + '\'' +
                    ", inback_backtime='" + inback_backtime + '\'' +
                    ", in_gps_x='" + in_gps_x + '\'' +
                    ", in_gps_y='" + in_gps_y + '\'' +
                    ", in_gps_z='" + in_gps_z + '\'' +
                    ", back_gps_x='" + back_gps_x + '\'' +
                    ", back_gps_y='" + back_gps_y + '\'' +
                    ", back_gps_z='" + back_gps_z + '\'' +
                    ", in_place='" + in_place + '\'' +
                    ", back_place='" + back_place + '\'' +
                    ", sys_road_id='" + sys_road_id + '\'' +
                    ", staff_name='" + staff_name + '\'' +
                    ", road_name='" + road_name + '\'' +
                    ", staff_hierarchy='" + staff_hierarchy + '\'' +
                    '}';
        }
    }
}
