package com.appler.riverchiefsystem.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ChargeRoadsData implements Parcelable {

    /**
     * code : 1
     * msg : success
     * data : [{"road_name":"万三路","road_gps_end":"[113.917601,34.718402]","patrol_record_begintime":"2019-06-19 11:49:42","road_gps":"[113.932492,34.827928,113.923266,34.80034,113.923394,34.799353,113.922665,34.780462,113.921849,34.759522,113.921849,34.759522,113.920476,34.725033,113.920519,34.724751,113.917601,34.718402]","sys_urban_id":3,"patrol_record_endtime":"2019-06-19 12:18:03","road_gps_begin":"[113.932492,34.827928]","patrol_record_detail":"未打分","road_type":"施工路段","road_begin":"连霍高速","road_over":"陇海铁路","sys_staff_id":102,"sys_patrol_record_id":1,"patrol_record_video":"","sys_road_id":131},{"road_name":"万三路","road_gps_end":"[113.917601,34.718402]","patrol_record_begintime":"2019-06-19 12:19:07","road_gps":"[113.932492,34.827928,113.923266,34.80034,113.923394,34.799353,113.922665,34.780462,113.921849,34.759522,113.921849,34.759522,113.920476,34.725033,113.920519,34.724751,113.917601,34.718402]","sys_urban_id":3,"patrol_record_endtime":"2019-06-19 12:22:15","road_gps_begin":"[113.932492,34.827928]","patrol_record_detail":"未打分","road_type":"施工路段","road_begin":"连霍高速","road_over":"陇海铁路","sys_staff_id":102,"sys_patrol_record_id":2,"patrol_record_video":"","sys_road_id":131},{"road_name":"万三路","road_gps_end":"[113.917601,34.718402]","patrol_record_begintime":"2019-06-19 12:23:11","road_gps":"[113.932492,34.827928,113.923266,34.80034,113.923394,34.799353,113.922665,34.780462,113.921849,34.759522,113.921849,34.759522,113.920476,34.725033,113.920519,34.724751,113.917601,34.718402]","sys_urban_id":3,"patrol_record_endtime":"2019-06-19 12:24:20","road_gps_begin":"[113.932492,34.827928]","patrol_record_detail":"未打分","road_type":"施工路段","road_begin":"连霍高速","road_over":"陇海铁路","sys_staff_id":102,"sys_patrol_record_id":3,"patrol_record_video":"","sys_road_id":131},{"road_name":"万三路","road_gps_end":"[113.917601,34.718402]","patrol_record_begintime":"2019-06-19 13:W00:55","road_gps":"[113.932492,34.827928,113.923266,34.80034,113.923394,34.799353,113.922665,34.780462,113.921849,34.759522,113.921849,34.759522,113.920476,34.725033,113.920519,34.724751,113.917601,34.718402]","sys_urban_id":3,"patrol_record_endtime":"2019-06-19 16:W01:34","road_gps_begin":"[113.932492,34.827928]","patrol_record_detail":"未打分","road_type":"施工路段","road_begin":"连霍高速","road_over":"陇海铁路","sys_staff_id":102,"sys_patrol_record_id":5,"patrol_record_video":"","sys_road_id":131},{"road_name":"万三路","road_gps_end":"[113.917601,34.718402]","patrol_record_begintime":"2019-06-19 16:02:18","road_gps":"[113.932492,34.827928,113.923266,34.80034,113.923394,34.799353,113.922665,34.780462,113.921849,34.759522,113.921849,34.759522,113.920476,34.725033,113.920519,34.724751,113.917601,34.718402]","sys_urban_id":3,"patrol_record_endtime":"2019-06-19 16:02:44","road_gps_begin":"[113.932492,34.827928]","patrol_record_detail":"未打分","road_type":"施工路段","road_begin":"连霍高速","road_over":"陇海铁路","sys_staff_id":102,"sys_patrol_record_id":13,"patrol_record_video":"","sys_road_id":131}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    protected ChargeRoadsData(Parcel in) {
        code = in.readInt();
        msg = in.readString();
    }

    public static final Creator<ChargeRoadsData> CREATOR = new Creator<ChargeRoadsData>() {
        @Override
        public ChargeRoadsData createFromParcel(Parcel in) {
            return new ChargeRoadsData(in);
        }

        @Override
        public ChargeRoadsData[] newArray(int size) {
            return new ChargeRoadsData[size];
        }
    };

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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
    }

    public static class DataBean implements Parcelable{
        /**
         * road_name : 万三路
         * road_gps_end : [113.917601,34.718402]
         * patrol_record_begintime : 2019-06-19 11:49:42
         * road_gps : [113.932492,34.827928,113.923266,34.80034,113.923394,34.799353,113.922665,34.780462,113.921849,34.759522,113.921849,34.759522,113.920476,34.725033,113.920519,34.724751,113.917601,34.718402]
         * sys_urban_id : 3
         * patrol_record_endtime : 2019-06-19 12:18:03
         * road_gps_begin : [113.932492,34.827928]
         * patrol_record_detail : 未打分
         * road_type : 施工路段
         * road_begin : 连霍高速
         * road_over : 陇海铁路
         * sys_staff_id : 102
         * sys_patrol_record_id : 1
         * patrol_record_video :
         * sys_road_id : 131
         */

        private String road_name;
        private String road_gps_end;
        private String patrol_record_begintime;
        private String road_gps;
        private int sys_urban_id;
        private String patrol_record_endtime;
        private String road_gps_begin;
        private String patrol_record_detail;
        private String road_type;
        private String road_begin;
        private String road_over;
        private int sys_staff_id;
        private int sys_patrol_record_id;
        private String patrol_record_video;
        private int sys_road_id;

        protected DataBean(Parcel in) {
            road_name = in.readString();
            road_gps_end = in.readString();
            patrol_record_begintime = in.readString();
            road_gps = in.readString();
            sys_urban_id = in.readInt();
            patrol_record_endtime = in.readString();
            road_gps_begin = in.readString();
            patrol_record_detail = in.readString();
            road_type = in.readString();
            road_begin = in.readString();
            road_over = in.readString();
            sys_staff_id = in.readInt();
            sys_patrol_record_id = in.readInt();
            patrol_record_video = in.readString();
            sys_road_id = in.readInt();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public String getRoad_name() {
            return road_name;
        }

        public void setRoad_name(String road_name) {
            this.road_name = road_name;
        }

        public String getRoad_gps_end() {
            return road_gps_end;
        }

        public void setRoad_gps_end(String road_gps_end) {
            this.road_gps_end = road_gps_end;
        }

        public String getPatrol_record_begintime() {
            return patrol_record_begintime;
        }

        public void setPatrol_record_begintime(String patrol_record_begintime) {
            this.patrol_record_begintime = patrol_record_begintime;
        }

        public String getRoad_gps() {
            return road_gps;
        }

        public void setRoad_gps(String road_gps) {
            this.road_gps = road_gps;
        }

        public int getSys_urban_id() {
            return sys_urban_id;
        }

        public void setSys_urban_id(int sys_urban_id) {
            this.sys_urban_id = sys_urban_id;
        }

        public String getPatrol_record_endtime() {
            return patrol_record_endtime;
        }

        public void setPatrol_record_endtime(String patrol_record_endtime) {
            this.patrol_record_endtime = patrol_record_endtime;
        }

        public String getRoad_gps_begin() {
            return road_gps_begin;
        }

        public void setRoad_gps_begin(String road_gps_begin) {
            this.road_gps_begin = road_gps_begin;
        }

        public String getPatrol_record_detail() {
            return patrol_record_detail;
        }

        public void setPatrol_record_detail(String patrol_record_detail) {
            this.patrol_record_detail = patrol_record_detail;
        }

        public String getRoad_type() {
            return road_type;
        }

        public void setRoad_type(String road_type) {
            this.road_type = road_type;
        }

        public String getRoad_begin() {
            return road_begin;
        }

        public void setRoad_begin(String road_begin) {
            this.road_begin = road_begin;
        }

        public String getRoad_over() {
            return road_over;
        }

        public void setRoad_over(String road_over) {
            this.road_over = road_over;
        }

        public int getSys_staff_id() {
            return sys_staff_id;
        }

        public void setSys_staff_id(int sys_staff_id) {
            this.sys_staff_id = sys_staff_id;
        }

        public int getSys_patrol_record_id() {
            return sys_patrol_record_id;
        }

        public void setSys_patrol_record_id(int sys_patrol_record_id) {
            this.sys_patrol_record_id = sys_patrol_record_id;
        }

        public String getPatrol_record_video() {
            return patrol_record_video;
        }

        public void setPatrol_record_video(String patrol_record_video) {
            this.patrol_record_video = patrol_record_video;
        }

        public int getSys_road_id() {
            return sys_road_id;
        }

        public void setSys_road_id(int sys_road_id) {
            this.sys_road_id = sys_road_id;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "road_name='" + road_name + '\'' +
                    ", road_gps_end='" + road_gps_end + '\'' +
                    ", patrol_record_begintime='" + patrol_record_begintime + '\'' +
                    ", road_gps='" + road_gps + '\'' +
                    ", sys_urban_id=" + sys_urban_id +
                    ", patrol_record_endtime='" + patrol_record_endtime + '\'' +
                    ", road_gps_begin='" + road_gps_begin + '\'' +
                    ", patrol_record_detail='" + patrol_record_detail + '\'' +
                    ", road_type='" + road_type + '\'' +
                    ", road_begin='" + road_begin + '\'' +
                    ", road_over='" + road_over + '\'' +
                    ", sys_staff_id=" + sys_staff_id +
                    ", sys_patrol_record_id=" + sys_patrol_record_id +
                    ", patrol_record_video='" + patrol_record_video + '\'' +
                    ", sys_road_id=" + sys_road_id +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(road_name);
            dest.writeString(road_gps_end);
            dest.writeString(patrol_record_begintime);
            dest.writeString(road_gps);
            dest.writeInt(sys_urban_id);
            dest.writeString(patrol_record_endtime);
            dest.writeString(road_gps_begin);
            dest.writeString(patrol_record_detail);
            dest.writeString(road_type);
            dest.writeString(road_begin);
            dest.writeString(road_over);
            dest.writeInt(sys_staff_id);
            dest.writeInt(sys_patrol_record_id);
            dest.writeString(patrol_record_video);
            dest.writeInt(sys_road_id);
        }
    }

    @Override
    public String toString() {
        return "ChargeRoadsData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
