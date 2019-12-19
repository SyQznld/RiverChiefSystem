package com.appler.riverchiefsystem.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PatrolData implements Parcelable {


    /**
     * code : 0
     * msg : success
     * data : [{"sys_patrol_record_id":"139","sys_staff_id":"83","patrol_record_begintime":"2019/09/27 14:46:46","patrol_record_endtime":"2019/09/27 14:49:02","patrol_record_photo":"uploadfiles/Picture/8320190927144912 20190927144659.jpg","patrol_record_video":"","sys_river_id":"16","patrol_record_gps":"113.640622 34.869031,113.640622 34.869031","patrol_state":"","address":"周庄村","rivername":"沙河周庄村"},{"sys_patrol_record_id":"138","sys_staff_id":"83","patrol_record_begintime":"2019/09/27 14:45:45","patrol_record_endtime":"","patrol_record_photo":"","patrol_record_video":"","sys_river_id":"0","patrol_record_gps":"","patrol_state":"","address":"","rivername":"沙河周庄村"},{"sys_patrol_record_id":"137","sys_staff_id":"83","patrol_record_begintime":"2019/09/27 14:37:03","patrol_record_endtime":"2019/09/27 14:37:41","patrol_record_photo":"","patrol_record_video":"","sys_river_id":"0","patrol_record_gps":"","patrol_state":"","address":"","rivername":"沙河周庄村"},{"sys_patrol_record_id":"136","sys_staff_id":"83","patrol_record_begintime":"2019/09/27 14:22:23","patrol_record_endtime":"2019/09/27 14:22:38","patrol_record_photo":"","patrol_record_video":"","sys_river_id":"0","patrol_record_gps":"113.64062 34.869028","patrol_state":"","address":"","rivername":"沙河周庄村"},{"sys_patrol_record_id":"135","sys_staff_id":"83","patrol_record_begintime":"2019/09/27 14:W01:12","patrol_record_endtime":"","patrol_record_photo":"","patrol_record_video":"","sys_river_id":"0","patrol_record_gps":"","patrol_state":"","address":"","rivername":""},{"sys_patrol_record_id":"134","sys_staff_id":"83","patrol_record_begintime":"2019/09/27 13:47:23","patrol_record_endtime":"2019/09/27 13:47:57","patrol_record_photo":"uploadfiles/Picture/8320190927134758 20190927134733.jpg,uploadfiles/Picture/8320190927134758 20190927134741.jpg","patrol_record_video":"","sys_river_id":"0","patrol_record_gps":"113.640591 34.86902,113.640591 34.86902,113.640591 34.86902","patrol_state":"","address":"","rivername":""},{"sys_patrol_record_id":"133","sys_staff_id":"83","patrol_record_begintime":"2019/09/27 13:44:42","patrol_record_endtime":"","patrol_record_photo":"","patrol_record_video":"","sys_river_id":"0","patrol_record_gps":"","patrol_state":"","address":"","rivername":""},{"sys_patrol_record_id":"132","sys_staff_id":"83","patrol_record_begintime":"2019/09/27 13:43:06","patrol_record_endtime":"","patrol_record_photo":"","patrol_record_video":"","sys_river_id":"0","patrol_record_gps":"","patrol_state":"","address":"","rivername":""},{"sys_patrol_record_id":"131","sys_staff_id":"83","patrol_record_begintime":"2019/09/27 13:38:07","patrol_record_endtime":"","patrol_record_photo":"","patrol_record_video":"","sys_river_id":"0","patrol_record_gps":"","patrol_state":"","address":"","rivername":""}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    protected PatrolData(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        data = in.createTypedArrayList(DataBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PatrolData> CREATOR = new Creator<PatrolData>() {
        @Override
        public PatrolData createFromParcel(Parcel in) {
            return new PatrolData(in);
        }

        @Override
        public PatrolData[] newArray(int size) {
            return new PatrolData[size];
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

    public static class DataBean implements Parcelable{
        /**
         * sys_patrol_record_id : 139
         * sys_staff_id : 83
         * patrol_record_begintime : 2019/09/27 14:46:46
         * patrol_record_endtime : 2019/09/27 14:49:02
         * patrol_record_photo : uploadfiles/Picture/8320190927144912 20190927144659.jpg
         * patrol_record_video :
         * sys_river_id : 16
         * patrol_record_gps : 113.640622 34.869031,113.640622 34.869031
         * patrol_state :
         * address : 周庄村
         * rivername : 沙河周庄村
         */

        private String sys_patrol_record_id;
        private String sys_staff_id;
        private String patrol_record_begintime;
        private String patrol_record_endtime;
        private String patrol_record_photo;
        private String patrol_record_video;
        private String sys_river_id;
        private String patrol_record_gps;
        private String patrol_state;
        private String address;
        private String rivername;

        protected DataBean(Parcel in) {
            sys_patrol_record_id = in.readString();
            sys_staff_id = in.readString();
            patrol_record_begintime = in.readString();
            patrol_record_endtime = in.readString();
            patrol_record_photo = in.readString();
            patrol_record_video = in.readString();
            sys_river_id = in.readString();
            patrol_record_gps = in.readString();
            patrol_state = in.readString();
            address = in.readString();
            rivername = in.readString();
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

        public String getSys_patrol_record_id() {
            return sys_patrol_record_id;
        }

        public void setSys_patrol_record_id(String sys_patrol_record_id) {
            this.sys_patrol_record_id = sys_patrol_record_id;
        }

        public String getSys_staff_id() {
            return sys_staff_id;
        }

        public void setSys_staff_id(String sys_staff_id) {
            this.sys_staff_id = sys_staff_id;
        }

        public String getPatrol_record_begintime() {
            return patrol_record_begintime;
        }

        public void setPatrol_record_begintime(String patrol_record_begintime) {
            this.patrol_record_begintime = patrol_record_begintime;
        }

        public String getPatrol_record_endtime() {
            return patrol_record_endtime;
        }

        public void setPatrol_record_endtime(String patrol_record_endtime) {
            this.patrol_record_endtime = patrol_record_endtime;
        }

        public String getPatrol_record_photo() {
            return patrol_record_photo;
        }

        public void setPatrol_record_photo(String patrol_record_photo) {
            this.patrol_record_photo = patrol_record_photo;
        }

        public String getPatrol_record_video() {
            return patrol_record_video;
        }

        public void setPatrol_record_video(String patrol_record_video) {
            this.patrol_record_video = patrol_record_video;
        }

        public String getSys_river_id() {
            return sys_river_id;
        }

        public void setSys_river_id(String sys_river_id) {
            this.sys_river_id = sys_river_id;
        }

        public String getPatrol_record_gps() {
            return patrol_record_gps;
        }

        public void setPatrol_record_gps(String patrol_record_gps) {
            this.patrol_record_gps = patrol_record_gps;
        }

        public String getPatrol_state() {
            return patrol_state;
        }

        public void setPatrol_state(String patrol_state) {
            this.patrol_state = patrol_state;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRivername() {
            return rivername;
        }

        public void setRivername(String rivername) {
            this.rivername = rivername;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(sys_patrol_record_id);
            dest.writeString(sys_staff_id);
            dest.writeString(patrol_record_begintime);
            dest.writeString(patrol_record_endtime);
            dest.writeString(patrol_record_photo);
            dest.writeString(patrol_record_video);
            dest.writeString(sys_river_id);
            dest.writeString(patrol_record_gps);
            dest.writeString(patrol_state);
            dest.writeString(address);
            dest.writeString(rivername);
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "sys_patrol_record_id='" + sys_patrol_record_id + '\'' +
                    ", sys_staff_id='" + sys_staff_id + '\'' +
                    ", patrol_record_begintime='" + patrol_record_begintime + '\'' +
                    ", patrol_record_endtime='" + patrol_record_endtime + '\'' +
                    ", patrol_record_photo='" + patrol_record_photo + '\'' +
                    ", patrol_record_video='" + patrol_record_video + '\'' +
                    ", sys_river_id='" + sys_river_id + '\'' +
                    ", patrol_record_gps='" + patrol_record_gps + '\'' +
                    ", patrol_state='" + patrol_state + '\'' +
                    ", address='" + address + '\'' +
                    ", rivername='" + rivername + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PatrolData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
